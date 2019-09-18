package com.ssz.studydemo.view

import com.ssz.studydemo.view.main.IMainContract

/**
 * @author : zsp
 * time : 2019 09 2019/9/17 15:11
 */
class MainPresenter(val view : IMainContract.IMainView) : IMainContract.IMainPresenter {

    init {
        view.mPresenter = this
    }

    override fun attach() {

    }

    override fun detach() {

    }
}