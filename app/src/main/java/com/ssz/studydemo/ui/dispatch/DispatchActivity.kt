package com.ssz.studydemo.ui.dispatch

import android.support.v4.view.ViewPager
import android.widget.FrameLayout
import com.ssz.studydemo.base.CustomActivity
import com.ssz.studydemo.R
import com.ssz.studydemo.ui.dagger.DaggerMvpExampleActivity
import com.ssz.studydemo.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_dispatch.*

/**
 * @author : zsp
 * time : 2019 10 2019/10/24 16:27
 */
 class DispatchActivity : CustomActivity(),ViewPager.OnPageChangeListener {

    override fun getLayoutId() = R.layout.activity_dispatch
    private var page : Int = 0

    override fun initView() {
        // 透明状态栏
        StatusBarUtils.setTransparent(this)
        val params = FrameLayout.LayoutParams(root_cl.layoutParams)
                .apply {
            topMargin = (-StatusBarUtils.getStatusBarHeight(this@DispatchActivity))
        }
        root_cl.layoutParams = params
    }

   override fun initData() {
       v_dispatch.apply{
          adapter = DispatchAdapter(supportFragmentManager)
          addOnPageChangeListener(this@DispatchActivity)
       }
    }

    override fun setEvent() {
        tv_enter .setOnClickListener {
            when(page){
                0->{}
                1->{}
                2->{ startActivity(DaggerMvpExampleActivity::class.java) }
            }
        }
    }

    override fun onPageScrollStateChanged(position: Int) {

    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

    }

    override fun onPageSelected(p0: Int) {
        this.page= p0
    }
}