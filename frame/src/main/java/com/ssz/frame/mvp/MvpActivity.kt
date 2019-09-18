package com.ssz.frame.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
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
        ButterKnife.bind(this)
        attachPresenter().attach()
        afterOnCreate(savedInstanceState)
    }

    open fun beforeOnCreate(savedInstanceState: Bundle?) {

    }

    abstract fun afterOnCreate(savedInstanceState: Bundle?)
    abstract fun getLayoutId(): Int
    abstract fun attachPresenter():BasePresenter
    abstract fun detachPresenter():BasePresenter

    override fun onDestroy() {
        mDisposable?.dispose()
        detachPresenter().detach()
        super.onDestroy()
    }
}