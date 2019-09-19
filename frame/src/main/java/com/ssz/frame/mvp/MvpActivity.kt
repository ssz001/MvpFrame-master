package com.ssz.frame.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

/**
 * @author : zsp
 * time : 2019 09 2019/9/18 10:39
 */
abstract class MvpActivity : AppCompatActivity(){

    protected var mDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
//        ButterKnife.bind(this)
        attachPresenter().attach()
        afterOnCreate(savedInstanceState)
    }

    protected open fun beforeOnCreate(savedInstanceState: Bundle?) {

    }

    protected abstract fun afterOnCreate(savedInstanceState: Bundle?)
    protected abstract fun getLayoutId(): Int
    protected abstract fun attachPresenter():BasePresenter

    override fun onDestroy() {
        mDisposable?.dispose()
        super.onDestroy()
    }
}