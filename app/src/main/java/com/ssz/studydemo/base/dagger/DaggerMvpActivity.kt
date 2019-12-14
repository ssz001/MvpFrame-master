package com.ssz.studydemo.base.dagger

import android.os.Bundle
import com.ssz.studydemo.base.BaseActivity
import com.ssz.studydemo.utils.ObjectHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class DaggerMvpActivity<T : BasePresenter> : BaseActivity() {

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


    protected open fun beforeOnCreate(savedInstanceState: Bundle?) {

    }
    protected abstract fun getLayoutId(): Int
    protected abstract fun initInject()
    protected abstract fun setEvent()
    protected abstract fun afterOnCreate(savedInstanceState: Bundle?)


    override fun onDestroy() {
        mPresenter?.detach()
        super.onDestroy()
    }
}