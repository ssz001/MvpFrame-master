package com.ssz.frame.mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import io.reactivex.disposables.CompositeDisposable

/**
 * @author : zsp
 * time : 2019 09 2019/9/18 11:08
 */
abstract class MvpFragment : Fragment(){

    protected var mDisposable: CompositeDisposable? = null
    private lateinit var unbinder: Unbinder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(getLayoutId(),null)
        unbinder = ButterKnife.bind(this,root)
        attachPresenter().attach()
        return root
    }

    abstract fun attachPresenter():BasePresenter
    abstract fun detachPresenter():BasePresenter
    abstract fun getLayoutId(): Int

    override fun onDestroyView() {
        mDisposable?.dispose()
        detachPresenter().detach()
        unbinder.unbind()
        super.onDestroyView()
    }
}