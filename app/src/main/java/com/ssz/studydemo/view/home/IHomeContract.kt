package com.ssz.studydemo.view.home

import com.ssz.base.func.BasePresenter
import com.ssz.base.func.BaseView


/**
 * @author : zsp
 * time : 2019 09 2019/9/17 15:05
 */
interface IHomeContract {

    interface IHomeView : BaseView<IHomePresenter>

    interface IHomePresenter : BasePresenter
}