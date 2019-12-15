package com.ssz.studydemo.base.dagger.di.component

import com.ssz.studydemo.base.dagger.di.module.AppModule
import com.ssz.studydemo.base.dagger.di.module.NetModule
import com.ssz.studydemo.model.remote.net.Api
import com.ssz.studydemo.test.app.AppDelegate
import dagger.Component
import javax.inject.Singleton

/**
 * @author : zsp
 * time : 2019 12 2019/12/11 14:32
 */
@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface AppComponent {
    fun getAppDelegate() : AppDelegate
    fun getApi():Api
    fun inject(appDelegate: AppDelegate)
}