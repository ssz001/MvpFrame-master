package com.ssz.studydemo.module.dagger

import com.ssz.studydemo.base.func.BasePresenter

interface IDaggerMvpContract {
    interface IView
    interface IPresenter : BasePresenter
}