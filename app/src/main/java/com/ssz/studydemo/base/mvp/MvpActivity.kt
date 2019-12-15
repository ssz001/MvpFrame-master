package com.ssz.studydemo.base.mvp

import android.os.Bundle
import com.ssz.studydemo.base.BaseActivity
import com.ssz.studydemo.base.mvp.func.IActivity

/**
 * @author : zsp
 * time : 2019 09 2019/9/18 10:39
 */
abstract class MvpActivity : BaseActivity(), IActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        bindPresenter()
        setEvent()
        afterOnCreate(savedInstanceState)
    }

    protected open fun beforeOnCreate(savedInstanceState: Bundle?) {

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}