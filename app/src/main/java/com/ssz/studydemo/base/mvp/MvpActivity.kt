package com.ssz.studydemo.base.mvp

import android.os.Bundle
import com.ssz.studydemo.base.BaseActivity
import com.ssz.studydemo.utils.ObjectHelper

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author : zsp
 * time : 2019 09 2019/9/18 10:39
 */
abstract class MvpActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        bindPresenter()
        afterOnCreate(savedInstanceState)
    }

    protected open fun beforeOnCreate(savedInstanceState: Bundle?) {

    }
    protected abstract fun bindPresenter()
    protected abstract fun afterOnCreate(savedInstanceState: Bundle?)
    protected abstract fun getLayoutId(): Int

    override fun onDestroy() {
        super.onDestroy()
    }
}