package com.ssz.studydemo.test

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.ssz.studydemo.base.dagger.di.component.AppComponent
import com.ssz.studydemo.base.dagger.di.component.DaggerAppComponent
import com.ssz.studydemo.base.dagger.di.module.AppModule
import com.ssz.studydemo.base.dagger.di.module.NetModule

class AppDelegate : IAppLifeCycleListener, IApp {

    lateinit var mAppComponent : AppComponent

    private fun initAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
                 .appModule(AppModule(this))
                 .netModule(NetModule())
                 .build()
        mAppComponent.inject(this)
    }

    override fun attachBaseContext(base: Context?) {

    }

    override fun onCreate(application: Application) {
        initAppComponent()
    }

    override fun onTrimMemory(application: Application) {

    }

    override fun onTrimMemory(level: Int) {

    }

    override fun onConfigurationChanged(newConfig: Configuration?) {

    }

}