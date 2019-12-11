package com.ssz.studydemo.base.dagger.di.component

import com.ssz.studydemo.app.AppContext
import com.ssz.studydemo.base.dagger.di.module.AppModule
import com.ssz.studydemo.base.dagger.di.module.NetModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author : zsp
 * time : 2019 12 2019/12/11 14:32
 */
@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface AppComponent {
    fun getAppContext() : AppContext
    fun inject(appContext: AppContext)
}