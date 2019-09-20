package com.ssz.studydemo.view.read

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.Settings
import android.util.Log
import android.view.animation.Animation
import com.ssz.frame.mvp.MvpActivity
import com.ssz.frame.utils.BrightnessUtils
import com.ssz.studydemo.R
import com.ssz.studydemo.model.bean.CollBookBean
import com.ssz.studydemo.widget.book.page.PageLoader
import com.ssz.studydemo.widget.book.page.TxtChapter

/**
 * @author : zsp
 * time : 2019 09 2019/9/20 14:21
 */
class ReadActivity : MvpActivity() ,IReadContract.IReadView {

    companion object{
        private val TAG = "ReadActivity"

        val REQUEST_MORE_SETTING = 1
        val EXTRA_COLL_BOOK = "extra_coll_book"
        val EXTRA_IS_COLLECTED = "extra_is_collected"

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

    private val WHAT_CATEGORY = 1
    private val WHAT_CHAPTER = 2

    private lateinit var mPageLoader : PageLoader
    private lateinit var mSettingDialog: ReadSettingDialog
    private var mTopInAnim: Animation? = null
    private var mTopOutAnim: Animation? = null
    private var mBottomInAnim: Animation? = null
    private var mBottomOutAnim: Animation? = null
    private var mCategoryAdapter: CategoryAdapter? = null

    override lateinit var mPresenter: IReadContract.IReadPresenter
    override fun getLayoutId() = R.layout.activity_read
    override fun attachPresenter() = ReadPresenter()

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                WHAT_CATEGORY -> mLvCategory.setSelection(mPageLoader.getChapterPos())
                WHAT_CHAPTER -> {
                    Log.e(TAG, "WHAT_CHAPTER")
                    mPageLoader.openChapter()
                }
            }
        }
    }

    private val mReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Intent.ACTION_BATTERY_CHANGED) {
                val level = intent.getIntExtra("level", 0)
                mPageLoader.updateBattery(level)
            } else if (intent.action == Intent.ACTION_TIME_TICK) {
                mPageLoader.updateTime()
            }// 监听分钟的变化
        }
    }

    private val mContentObserver = object : ContentObserver(Handler()){

        override fun onChange(selfChange: Boolean) {
            onChange(selfChange,null)
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

    override fun afterOnCreate(savedInstanceState: Bundle?) {
          setEvent()
    }

    fun setEvent(){
        mPageLoader.setOnPageChangeListener(object : PageLoader.OnPageChangeListener{
            /**
             * 作用：章节切换的时候进行回调
             *
             * @param pos:切换章节的序号
             */
            override fun onChapterChange(pos: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            /**
             * 作用：请求加载章节内容
             *
             * @param requestChapters:需要下载的章节列表
             */
            override fun requestChapters(requestChapters: MutableList<TxtChapter>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            /**
             * 作用：章节目录加载完成时候回调
             *
             * @param chapters：返回章节目录
             */
            override fun onCategoryFinish(chapters: MutableList<TxtChapter>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            /**
             * 作用：章节页码数量改变之后的回调。==> 字体大小的调整，或者是否关闭虚拟按钮功能都会改变页面的数量。
             *
             * @param count:页面的数量
             */
            override fun onPageCountChange(count: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            /**
             * 作用：当页面改变的时候回调
             *
             * @param pos:当前的页面的序号
             */
            override fun onPageChange(pos: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}