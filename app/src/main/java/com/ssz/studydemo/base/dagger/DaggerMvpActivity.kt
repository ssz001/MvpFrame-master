package com.ssz.studydemo.base.dagger

import android.os.Bundle
import com.ssz.studydemo.base.BaseActivity
import com.ssz.studydemo.base.func.BasePresenter
import com.ssz.studydemo.utils.ObjectHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class DaggerMvpActivity<T : BasePresenter> : BaseActivity() {

    @set: Inject
    var mPresenter : T? = null
    private var mcDisposable: CompositeDisposable? = null

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
    protected abstract fun initInject()
    protected abstract fun afterOnCreate(savedInstanceState: Bundle?)
    protected abstract fun getLayoutId(): Int
    protected abstract fun setEvent()


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