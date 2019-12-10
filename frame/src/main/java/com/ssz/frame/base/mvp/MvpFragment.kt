package com.ssz.frame.base.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssz.frame.base.BaseFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author : zsp
 * time : 2019 10 2019/10/15 13:40
 */
abstract class MvpFragment : BaseFragment() {

    private lateinit var mcDisposable: CompositeDisposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindPresenter()
        beforeOnCreateView()
        return inflater.inflate(getLayoutId(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterOnCreateView(savedInstanceState)
    }

    protected open fun beforeOnCreateView(){

    }

    protected abstract fun bindPresenter()
    /**
     * view 已经初始化
     */
    protected abstract fun afterOnCreateView(savedInstanceState: Bundle?)
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

    protected fun setOnClickListener(vararg views : View){
        for (v in views){
            v.setOnClickListener(this as View.OnClickListener)
        }
    }

    override fun onDestroy() {
        if (this::mcDisposable.isInitialized)mcDisposable.dispose()
        super.onDestroy()
    }
}