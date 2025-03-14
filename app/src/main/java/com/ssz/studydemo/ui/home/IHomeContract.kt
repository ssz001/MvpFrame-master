package com.ssz.studydemo.ui.home

import com.ssz.studydemo.base.ui.mvp.func.BasePresenter
import com.ssz.studydemo.base.ui.mvp.func.BaseView


/**
 * @author : zsp
 * time : 2019 09 2019/9/17 15:05
 */
interface IHomeContract {

    interface IHomeView : BaseView<IHomePresenter>

    interface IHomePresenter : BasePresenter
}