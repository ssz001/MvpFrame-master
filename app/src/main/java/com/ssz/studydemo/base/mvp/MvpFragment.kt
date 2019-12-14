package com.ssz.studydemo.base.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssz.studydemo.base.BaseFragment
import com.ssz.studydemo.utils.ObjectHelper

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author : zsp
 * time : 2019 10 2019/10/15 13:40
 */
abstract class MvpFragment : BaseFragment() {

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


    override fun onDestroy() {
        super.onDestroy()
    }
}