package com.ssz.studydemo.base.ui.dagger

import android.os.Bundle
import com.ssz.studydemo.base.BaseActivity
import com.ssz.studydemo.base.ui.view.IActivity
import javax.inject.Inject

abstract class DaggerMvpActivity<T : BasePresenter> : BaseActivity(), IActivity {

    @set: Inject
    var mPresenter : T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initInject()
        setEvent()
        afterOnCreate(savedInstanceState)
    }

    abstract fun initInject()

    protected open fun beforeOnCreate(savedInstanceState: Bundle?) {

    }

    override fun onDestroy() {
        mPresenter?.detach()
        mPresenter = null
        super.onDestroy()
    }
}