package com.ssz.studydemo.view.book.read

import com.ssz.frame.mvp.BaseRxPresenter

/**
 * @author : zsp
 * time : 2019 09 2019/9/20 9:37
 */
class ReadPresenter(val view : IReadContract.IReadView) : BaseRxPresenter(),IReadContract.IReadPresenter {

    init {
        view.mPresenter = this
    }

}