package com.ssz.studydemo.base.ui.dagger.di.component

import com.ssz.studydemo.app.AppContext
import com.ssz.studydemo.base.ui.dagger.di.module.AppModule
import com.ssz.studydemo.base.ui.dagger.di.module.NetModule
import com.ssz.studydemo.model.remote.net.Api
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
    fun getApi():Api
    fun inject(appContext: AppContext)
}