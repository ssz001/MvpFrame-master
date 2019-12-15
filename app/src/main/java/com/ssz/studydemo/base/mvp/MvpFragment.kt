package com.ssz.studydemo.base.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssz.studydemo.base.BaseFragment
import com.ssz.studydemo.base.mvp.func.IFragment

/**
 * @author : zsp
 * time : 2019 10 2019/10/15 13:40
 */
abstract class MvpFragment : BaseFragment(), IFragment {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindPresenter()
        beforeOnCreateView()
        return inflater.inflate(getLayoutId(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEvent()
        afterOnCreateView(savedInstanceState)
    }

    protected open fun beforeOnCreateView(){

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}