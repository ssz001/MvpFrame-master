package com.ssz.studydemo.ui.dispatch

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ssz.studydemo.R
import com.ssz.studydemo.utils.ScreenUtil

/**
 * @author : zsp
 * time : 2019 10 2019/10/25 14:46
 */
class DispatchAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){
    private val mPaperList = listOf<Fragment>(
            DispatchFragment("simple", ScreenUtil.getDrawable(R.drawable.dispatch_type_1))
            ,DispatchFragment("custom", ScreenUtil.getDrawable(R.drawable.dispatch_type_2))
            ,DispatchFragment("dagger", ScreenUtil.getDrawable(R.drawable.dispatch_type_3))
    )
    override fun getItem(position: Int) = mPaperList[position]
    override fun getCount() = mPaperList.size
}