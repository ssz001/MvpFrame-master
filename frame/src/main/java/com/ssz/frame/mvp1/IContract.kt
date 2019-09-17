package com.ssz.frame.mvp1

/**
 * @author : zsp
 * time : 2019 09 2019/9/17 15:34
 */
interface IContract {

    interface IView

    interface IPresenter<T:IView>{
        fun attachView(view:T)
        fun detachView()
    }
}