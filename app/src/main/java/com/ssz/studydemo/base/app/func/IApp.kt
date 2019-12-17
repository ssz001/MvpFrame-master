package com.ssz.studydemo.base.app.func

import com.ssz.studydemo.base.ui.dagger.di.component.AppComponent

/**
 * @author : zsp
 * time : 2019 12 2019/12/17 8:34
 */
interface IApp {
    var mAppComponent : AppComponent
    fun setupAppComponent()
}