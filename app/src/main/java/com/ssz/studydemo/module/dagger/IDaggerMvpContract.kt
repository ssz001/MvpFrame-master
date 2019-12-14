package com.ssz.studydemo.module.dagger

import com.ssz.studydemo.base.dagger.func.DaggerPresenter
import com.ssz.studydemo.base.dagger.func.DaggerView

interface IDaggerMvpContract {
    interface IView : DaggerView
    interface IPresenter : DaggerPresenter{
        fun getJoke(page:Int,count :Int,type: String)
    }
}