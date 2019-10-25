package com.ssz.studydemo.view.dispatch

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @author : zsp
 * time : 2019 10 2019/10/25 14:46
 */
class DispatchAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){
    private val mPaperList = listOf<Fragment>(DispatchFragment(),DispatchFragment(),DispatchFragment())
    override fun getItem(position: Int) = mPaperList[position]
    override fun getCount() = mPaperList.size
}