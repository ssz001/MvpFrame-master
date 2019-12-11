package com.ssz.studydemo.base

import android.os.Bundle

/**
 * @author : zsp
 * time : 2019 10 2019/10/24 16:29
 * mvc
 */
abstract class CustomActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate()
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        initData()
    }

    protected open fun beforeOnCreate(){

    }
    abstract fun getLayoutId():Int
    abstract fun initView()
    abstract fun initData()
}