package com.ssz.studydemo.ui.dagger

import com.ssz.studydemo.base.ui.dagger.func.DaggerPresenter
import com.ssz.studydemo.base.ui.dagger.func.DaggerView

interface IDaggerMvpContract {
    interface IView : DaggerView
    interface IPresenter : DaggerPresenter {
        fun getJoke(page:Int,count :Int,type: String)
    }
}