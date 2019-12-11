package com.ssz.studydemo.view.dispatch

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ssz.studydemo.R
import com.ssz.utils.ScreenUtil

/**
 * @author : zsp
 * time : 2019 10 2019/10/25 14:46
 */
class DispatchAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){
    private val mPaperList = listOf<Fragment>(
            DispatchFragment("夏虫",ScreenUtil.getDrawable(R.drawable.dispatch_type_1))
            ,DispatchFragment("麦浪", ScreenUtil.getDrawable(R.drawable.dispatch_type_2))
            ,DispatchFragment("雷声",ScreenUtil.getDrawable(R.drawable.dispatch_type_3))
    )
    override fun getItem(position: Int) = mPaperList[position]
    override fun getCount() = mPaperList.size
}