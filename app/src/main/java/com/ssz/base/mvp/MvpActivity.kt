package com.ssz.base.mvp

import android.os.Bundle
import com.ssz.base.BaseActivity
import com.ssz.utils.ObjectHelper

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author : zsp
 * time : 2019 09 2019/9/18 10:39
 */
abstract class MvpActivity : BaseActivity(){

    private var mcDisposable: CompositeDisposable? = null

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

    /**
     * 统一管理订阅
     */
    protected fun addDisposable(d: Disposable){
        if(ObjectHelper.isNull(mcDisposable)){
            mcDisposable = CompositeDisposable()
        }
        mcDisposable!!.add(d)
    }

    override fun onDestroy() {
        mcDisposable?.dispose()
        super.onDestroy()
    }
}