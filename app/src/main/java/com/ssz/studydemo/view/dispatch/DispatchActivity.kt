package com.ssz.studydemo.view.dispatch

import android.support.v4.view.ViewPager
import android.widget.FrameLayout
import com.ssz.dragger2.Entry
import com.ssz.frame.base.CustomActivity
import com.ssz.frame.utils.StatusBarUtils
import com.ssz.studydemo.R
import kotlinx.android.synthetic.main.activity_dispatch.*

/**
 * @author : zsp
 * time : 2019 10 2019/10/24 16:27
 */
 class DispatchActivity :CustomActivity(),ViewPager.OnPageChangeListener {

    override fun getLayoutId() = R.layout.activity_dispatch

    override fun initView() {
        Entry().start()
        // 透明状态栏
        StatusBarUtils.setTransparent(this)
        val params = FrameLayout.LayoutParams(root_cl.layoutParams).apply {
            topMargin = (-StatusBarUtils.getStatusBarHeight(this@DispatchActivity))
        }
        root_cl.layoutParams = params
    }

   override fun beforeOnCreate() {
      super.beforeOnCreate()
   }

   override fun initData() {
       v_dispatch.apply{
          adapter = DispatchAdapter(supportFragmentManager)
          addOnPageChangeListener(this@DispatchActivity)
       }
    }

    override fun onPageScrollStateChanged(position: Int) {

    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

    }

    override fun onPageSelected(p0: Int) {

    }
}