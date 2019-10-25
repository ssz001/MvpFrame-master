package com.ssz.frame.mvp

import android.os.Bundle
import com.ssz.frame.base.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author : zsp
 * time : 2019 09 2019/9/18 10:39
 */
abstract class MvpActivity : BaseActivity(){

    private lateinit var mcDisposable: CompositeDisposable

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
    protected fun addDisposable(d: Disposable) {
        if(!this::mcDisposable.isInitialized){
            mcDisposable = CompositeDisposable()
        }
        mcDisposable.add(d)
    }

    /**
     * 对于网络请求需要取消的情况,每次请求前调这个方法就行
     */
    protected fun removeDisposable(d: Disposable?): Boolean {
        if (null == d) return true
        if (!this::mcDisposable.isInitialized) {
            d.dispose()
            return true
        }
        return mcDisposable.remove(d)
    }

    override fun onDestroy() {
        if (this::mcDisposable.isInitialized)mcDisposable.dispose()
        super.onDestroy()
    }
}