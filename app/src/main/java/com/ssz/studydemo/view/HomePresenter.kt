package com.ssz.studydemo.view

import com.ssz.studydemo.view.home.IHomeContract

/**
 * @author : zsp
 * time : 2019 09 2019/9/17 15:11
 */
class HomePresenter(val view : IHomeContract.IHomeView) : IHomeContract.IHomePresenter {

    init {
        view.mPresenter = this
    }

    override fun attach() {

    }

    override fun detach() {

    }
}