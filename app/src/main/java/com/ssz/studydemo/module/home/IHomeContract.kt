package com.ssz.studydemo.module.home

import com.ssz.studydemo.base.mvp.func.BasePresenter
import com.ssz.studydemo.base.mvp.func.BaseView


/**
 * @author : zsp
 * time : 2019 09 2019/9/17 15:05
 */
interface IHomeContract {

    interface IHomeView : BaseView<IHomePresenter>

    interface IHomePresenter : BasePresenter
}