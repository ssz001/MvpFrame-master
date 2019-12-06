package com.ssz.studydemo.view.book.read

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.ContentObserver
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat.LAYER_TYPE_SOFTWARE
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import com.ssz.frame.mvp.MvpActivity
import com.ssz.frame.utils.BrightnessUtils
import com.ssz.frame.utils.StatusBarUtils
import com.ssz.frame.utils.StringUtils
import com.ssz.frame.utils.SystemBarUtils
import com.ssz.studydemo.R
import com.ssz.studydemo.data.local.ReadSettingManager
import com.ssz.studydemo.model.bean.CollBookBean
import com.ssz.studydemo.widget.book.page.PageLoader
import com.ssz.studydemo.widget.book.page.PageView
import com.ssz.studydemo.widget.book.page.TxtChapter
import kotlinx.android.synthetic.main.activity_read.*

/**
 * @author : zsp
 * time : 2019 09 2019/9/20 14:21
 */
class ReadActivity : MvpActivity(), IReadContract.IReadView {

    companion object {
        private const val TAG = "ReadActivity"
        const val REQUEST_MORE_SETTING = 1
        private const val EXTRA_COLL_BOOK = "extra_coll_book"
        private const val EXTRA_IS_COLLECTED = "extra_is_collected"
        private const val WHAT_CATEGORY = 1
        private const val WHAT_CHAPTER = 2
        fun startActivity(context: Context, collBook: CollBookBean, isCollected: Boolean) {
            context.startActivity(Intent(context, ReadActivity::class.java)
                    .putExtra(EXTRA_IS_COLLECTED, isCollected)
                    .putExtra(EXTRA_COLL_BOOK, collBook))
        }
    }

    // 注册 Brightness 的 uri
    private val BRIGHTNESS_MODE_URI = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE)
    private val BRIGHTNESS_URI = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS)
    private val BRIGHTNESS_ADJ_URI = Settings.System.getUriFor("screen_auto_brightness_adj")
    //控制屏幕常亮
    private var mWakeLock: PowerManager.WakeLock? = null
    private var mCollBook: CollBookBean? = null

    override fun getLayoutId() = R.layout.activity_read
    override lateinit var mPresenter: IReadContract.IReadPresenter
    override fun bindPresenter() {
        ReadPresenter(this)
    }

    @SuppressLint("InvalidWakeLockTag")
    override fun afterOnCreate(savedInstanceState: Bundle?) {
        mCollBook = intent.getParcelableExtra(EXTRA_COLL_BOOK)
        isCollected = intent.getBooleanExtra(EXTRA_IS_COLLECTED, false)
        isNightMode = ReadSettingManager.getInstance().isNightMode
        isFullScreen = ReadSettingManager.getInstance().isFullScreen

//        setEvent()
        // 如果 API < 18 取消硬件加速
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2 &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            read_pv_page.setLayerType(LAYER_TYPE_SOFTWARE, null)
        }

        //获取页面加载器
        Log.d(TAG, "+initWidget")
        mPageLoader = read_pv_page.getPageLoader(mCollBook)
        //禁止滑动展示DrawerLayout
        read_dl_slide.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        //侧边打开后，返回键能够起作用
        read_dl_slide.isFocusableInTouchMode = false
        mSettingDialog = ReadSettingDialog(this, mPageLoader)

        setUpAdapter()

        //夜间模式按钮的状态
        toggleNightMode()

        //注册广播
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        intentFilter.addAction(Intent.ACTION_TIME_TICK)
        registerReceiver(mReceiver, intentFilter)

        //设置当前Activity的Brightness
        if (ReadSettingManager.getInstance().isBrightnessAuto) {
            BrightnessUtils.setDefaultBrightness(this)
        } else {
            BrightnessUtils.setBrightness(this, ReadSettingManager.getInstance().getBrightness())
        }

        //初始化屏幕常亮类
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "keep bright")

        //隐藏StatusBar
        read_pv_page.post { hideSystemBar()}
        //初始化TopMenu
        initTopMenu()

        //初始化BottomMenu
        initBottomMenu()
    }

    private lateinit var mPageLoader: PageLoader
    private lateinit var mSettingDialog: ReadSettingDialog
    private var mTopInAnim: Animation? = null
    private var mTopOutAnim: Animation? = null
    private var mBottomInAnim: Animation? = null
    private var mBottomOutAnim: Animation? = null
    private var mCategoryAdapter: CategoryAdapter? = null

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                WHAT_CATEGORY -> read_iv_category.setSelection(mPageLoader.getChapterPos())
                WHAT_CHAPTER -> {
                    Log.e(TAG, "WHAT_CHAPTER")
                    mPageLoader.openChapter()
                }
            }
        }
    }

    private fun hideSystemBar() {
        //隐藏
        SystemBarUtils.hideStableStatusBar(this)
        //        SystemBarUtils.hideStableNavBar(this);
        if (isFullScreen) {
            SystemBarUtils.hideStableNavBar(this)
        }
    }

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Intent.ACTION_BATTERY_CHANGED) {
                val level = intent.getIntExtra("level", 0)
                mPageLoader.updateBattery(level)
            } else if (intent.action == Intent.ACTION_TIME_TICK) {
                mPageLoader.updateTime()
            }// 监听分钟的变化
        }
    }

    private fun toggleNightMode() {
        if (isNightMode) {
            read_tv_night_mode.text = "日间"
            val drawable = ContextCompat.getDrawable(this, R.drawable.ic_read_menu_morning)
            read_tv_night_mode.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
        } else {
            read_tv_night_mode.text = "夜间"
            val drawable = ContextCompat.getDrawable(this, R.drawable.ic_read_menu_night)
            read_tv_night_mode.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
        }
    }

    private fun setUpAdapter() {
        mCategoryAdapter = CategoryAdapter()
        read_iv_category.adapter = mCategoryAdapter
        read_iv_category.isFastScrollEnabled = true
    }

    private val mContentObserver = object : ContentObserver(Handler()) {

        override fun onChange(selfChange: Boolean) {
            onChange(selfChange, null)
        }

        override fun onChange(selfChange: Boolean, uri: Uri?) {
            super.onChange(selfChange, uri)
            // 判断当前是否跟随屏幕亮度，如果不是则返回
            if (selfChange || !mSettingDialog.isBrightFollowSystem()) return
            // 如果系统亮度改变，则修改当前 Activity 亮度
            if (BRIGHTNESS_MODE_URI == uri) {
                Log.d(TAG, "亮度模式改变")
            } else if (BRIGHTNESS_URI == uri && !BrightnessUtils.isAutoBrightness(this@ReadActivity)) {
                Log.d(TAG, "亮度模式为手动模式 值改变")
                BrightnessUtils.setBrightness(this@ReadActivity, BrightnessUtils.getScreenBrightness(this@ReadActivity))
            } else if (BRIGHTNESS_ADJ_URI == uri && BrightnessUtils.isAutoBrightness(this@ReadActivity)) {
                Log.d(TAG, "亮度模式为自动模式 值改变")
                BrightnessUtils.setDefaultBrightness(this@ReadActivity)
            } else {
                Log.d(TAG, "亮度调整 其他")
            }
        }
    }

    // isFromSDCard
    private var isCollected = false
    private var isNightMode = false
    private var isFullScreen = false
    private var isRegistered = false
    private var mBookId: String? = null

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        finish()
        startActivity(intent)
        super.onNewIntent(intent)
    }

    private fun setEvent() {
        mPageLoader.setOnPageChangeListener(object : PageLoader.OnPageChangeListener {

           override fun onChapterChange(pos: Int) {
                mCategoryAdapter.setChapter(pos)
            }

            override fun requestChapters(requestChapters: List<TxtChapter>) {
                Log.d(TAG, "+requestChapters")
                mPresenter.loadChapter(mBookId, requestChapters)
                mHandler.sendEmptyMessage(WHAT_CATEGORY)
                //隐藏提示
                read_tv_page_tip.visibility = GONE
            }

            override  fun onCategoryFinish(chapters: List<TxtChapter>) {
                for (chapter in chapters) {
                    chapter.title = StringUtils.convertCC(chapter.title, read_pv_page.getContext())
                }
                mCategoryAdapter.refreshItems(chapters)
            }

            override fun onPageCountChange(count: Int) {
                read_sb_chapter_progress.max = Math.max(0, count - 1)
                read_sb_chapter_progress.progress = 0
                // 如果处于错误状态，那么就冻结使用
                if (mPageLoader.pageStatus === PageLoader.STATUS_LOADING
                        || mPageLoader.pageStatus === PageLoader.STATUS_ERROR) {
                    read_sb_chapter_progress.setEnabled(false)
                } else {
                    read_sb_chapter_progress.setEnabled(true)
                }
            }

            override fun onPageChange(pos: Int) {
                if (read_sb_chapter_progress == null) {
                    return
                }
                read_sb_chapter_progress.post { read_sb_chapter_progress.setProgress(pos) }
            }
        })

        read_sb_chapter_progress.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                        if (read_ll_bottom_menu.visibility == VISIBLE) {
                            //显示标题
                            read_tv_page_tip.text = (progress + 1).toString() + "/" + (read_sb_chapter_progress.getMax() + 1)
                            read_tv_page_tip.visibility = VISIBLE
                        }
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {}

                    override fun onStopTrackingTouch(seekBar: SeekBar) {
                        //进行切换
                        val pagePos = read_sb_chapter_progress.progress
                        if (pagePos != mPageLoader.pagePos) {
                            mPageLoader.skipToPage(pagePos)
                        }
                        //隐藏提示
                        read_tv_page_tip.visibility = GONE
                    }
                }
        )

        read_pv_page.setTouchListener(object : PageView.TouchListener{
            override fun onTouch(): Boolean {
                return !hideReadMenu()
            }

            override fun center() {
                toggleMenu(true)
            }

            override fun prePage() {}

            override  fun nextPage() {}

            override fun cancel() {}
        })

        read_iv_category.setOnItemClickListener { _, _, position, _ ->
            read_dl_slide.closeDrawer(Gravity.START)
            Log.d("+点击章节","" + position)
            mPageLoader.skipToChapter(position)
        }

        read_tv_category.setOnClickListener {
            //移动到指定位置
            if (mCategoryAdapter.getCount() > 0) {
                read_iv_category.setSelection(mPageLoader.chapterPos)
            }
            //切换菜单
            toggleMenu(true)
            //打开侧滑动栏
            read_dl_slide.openDrawer(Gravity.START)
        }
        read_tv_setting.setOnClickListener {
            toggleMenu(false)
            mSettingDialog.show()
        }

        read_tv_pre_chapter.setOnClickListener {
            if (mPageLoader.skipPreChapter()) {
                mCategoryAdapter.setChapter(mPageLoader.chapterPos)
            }
        }

        read_tv_next_chapter.setOnClickListener {
            if (mPageLoader.skipNextChapter()) {
                mCategoryAdapter.setChapter(mPageLoader.chapterPos)
            }
        }

        read_tv_night_mode.setOnClickListener {
            isNightMode = !isNightMode
            mPageLoader.setNightMode(isNightMode)
            toggleNightMode()
        }

        read_tv_brief.setOnClickListener {
//            BookDetailActivity.startActivity(this, mBookId)
        }

        read_tv_community.setOnClickListener {
//            val intent = Intent(this, CommunityActivity::class.java)
            startActivity(intent)
        }
        mSettingDialog.setOnDismissListener{hideSystemBar()}
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)

        mHandler.removeMessages(WHAT_CATEGORY)
        mHandler.removeMessages(WHAT_CHAPTER)

        mPageLoader.closeBook()
//        mPageLoader = null
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val isVolumeTurnPage = ReadSettingManager
                .getInstance().isVolumeTurnPage
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> if (isVolumeTurnPage) {
                return mPageLoader.skipToPrePage()
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> if (isVolumeTurnPage) {
                return mPageLoader.skipToNextPage()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun initTopMenu() {
        if (Build.VERSION.SDK_INT >= 19) {
            read_abl_top_menu.setPadding(0, StatusBarUtils.getStatusBarHeight(this), 0, 0)
        }
    }

    private fun initBottomMenu() {
        //判断是否全屏
        if (ReadSettingManager.getInstance().isFullScreen) {
            //还需要设置mBottomMenu的底部高度
            val params = read_ll_bottom_menu.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = SystemBarUtils.getNavigationBarHeight()
            read_ll_bottom_menu.layoutParams = params
        } else {
            //设置mBottomMenu的底部距离
            val params = read_ll_bottom_menu.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = 0
            read_ll_bottom_menu.layoutParams = params
        }
    }

    /**
     * 隐藏阅读界面的菜单显示
     *
     * @return 是否隐藏成功
     */
    private fun hideReadMenu(): Boolean {
        hideSystemBar()
        if (read_abl_top_menu.visibility == VISIBLE) {
            toggleMenu(true)
            return true
        } else if (mSettingDialog.isShowing()) {
            mSettingDialog.dismiss()
            return true
        }
        return false
    }

    private fun showSystemBar() {
        Log.d(TAG, "showSystemBar:")
        //显示
        SystemBarUtils.showUnStableStatusBar(this)
        //        SystemBarUtils.showUnStableNavBar(this);
        if (isFullScreen) {
            SystemBarUtils.showUnStableNavBar(this)
        }
    }

    /**
     * 切换菜单栏的可视状态
     * 默认是隐藏的
     */
    private fun toggleMenu(hideStatusBar: Boolean) {
        initMenuAnim()

        if (read_abl_top_menu.visibility == VISIBLE) {
            //关闭
            read_abl_top_menu.startAnimation(mTopOutAnim)
            read_ll_bottom_menu.startAnimation(mBottomOutAnim)
            read_abl_top_menu.visibility = GONE
            read_ll_bottom_menu.visibility = GONE
            read_tv_page_tip.visibility = GONE

            if (hideStatusBar) {
                hideSystemBar()
            }
        } else {
            read_abl_top_menu.visibility = VISIBLE
            read_ll_bottom_menu.visibility = VISIBLE
            read_abl_top_menu.startAnimation(mTopInAnim)
            read_ll_bottom_menu.startAnimation(mBottomInAnim)
            showSystemBar()
        }
    }

    //初始化菜单动画
    private fun initMenuAnim() {
        if (mTopInAnim != null) return

        mTopInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_in)
        mTopOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_out)
        mBottomInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_in)
        mBottomOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_out)
        //退出的速度要快
        mTopOutAnim?.duration = 200
        mBottomOutAnim?.duration = 200
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        SystemBarUtils.hideStableStatusBar(this)
        if (requestCode == REQUEST_MORE_SETTING) {
            val fullScreen = ReadSettingManager.getInstance().isFullScreen
            if (isFullScreen != fullScreen) {
                isFullScreen = fullScreen
                // 刷新BottomMenu
                initBottomMenu()
            }
            // 设置显示状态
            if (isFullScreen) {
                SystemBarUtils.hideStableNavBar(this)
            } else {
                SystemBarUtils.showStableNavBar(this)
            }
        }
    }
}