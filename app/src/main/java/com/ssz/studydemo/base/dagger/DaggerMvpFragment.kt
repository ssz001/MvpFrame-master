package com.ssz.studydemo.base.dagger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssz.studydemo.base.BaseFragment
import com.ssz.studydemo.base.dagger.func.IFragment
import javax.inject.Inject

abstract class DaggerMvpFragment<T : BasePresenter> : BaseFragment(), IFragment {

    @set: Inject
    var mPresenter : T? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        beforeOnCreateView(savedInstanceState)
        initInject()
        return inflater.inflate(getLayoutId(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEvent()
        afterOnCreateView(savedInstanceState)
    }

    protected open fun beforeOnCreateView(savedInstanceState: Bundle?) {

    }

    override fun onDestroyView() {
        mPresenter?.detach()
        super.onDestroyView()
    }

    override fun onDestroy() {
        this.mPresenter = null
        super.onDestroy()
    }
}