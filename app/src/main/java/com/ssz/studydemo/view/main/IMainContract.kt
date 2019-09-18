package com.ssz.studydemo.view.main

import com.ssz.frame.mvp.BasePresenter
import com.ssz.frame.mvp.BaseView


/**
 * @author : zsp
 * time : 2019 09 2019/9/17 15:05
 */
interface IMainContract {

    interface IMainView : BaseView<IMainPresenter>

    interface IMainPresenter : BasePresenter
}