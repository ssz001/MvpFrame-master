package com.ssz.studydemo.view.main

import com.ssz.frame.mvp1.IContract

/**
 * @author : zsp
 * time : 2019 09 2019/9/17 15:05
 */
interface IMainContract : IContract {

    interface IMainView : IContract.IView

    interface IMainPresenter : IContract.IPresenter<IMainView>
}