package com.ssz.studydemo.view.dispatch

import android.support.v4.view.ViewPager
import com.ssz.frame.base.CustomActivity
import com.ssz.frame.utils.ScreenUtil
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
      StatusBarUtils.setColor(this,ScreenUtil.getColor(R.color.colorAccent))
    }

   override fun beforeOnCreate() {
//      supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
//      window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//              WindowManager.LayoutParams.FLAG_FULLSCREEN)
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