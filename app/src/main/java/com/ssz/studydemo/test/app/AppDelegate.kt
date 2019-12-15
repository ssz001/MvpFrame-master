package com.ssz.studydemo.test.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.ssz.studydemo.base.dagger.di.component.AppComponent
import com.ssz.studydemo.base.dagger.di.component.DaggerAppComponent
import com.ssz.studydemo.base.dagger.di.module.AppModule
import com.ssz.studydemo.base.dagger.di.module.NetModule
import com.ssz.studydemo.test.activity.ActivityDelegate
import javax.inject.Inject

class AppDelegate : IAppLifeCycle, IApp {

    lateinit var mApplication :Application
    lateinit var mAppComponent : AppComponent

    @set:Inject
    lateinit var mActivityDelegate : ActivityDelegate

    /********************** lifecycle ***********************/

    override fun attachBaseContext(base: Context) {

    }

    override fun onCreate(application: Application) {
        mApplication = application
        mApplication.registerActivityLifecycleCallbacks(mActivityDelegate)
        initAppComponent()
    }

    override fun onTrimMemory(application: Application) {

    }

    override fun onTrimMemory(level: Int) {

    }

    override fun onConfigurationChanged(newConfig: Configuration?) {

    }

    /**********************************************************/

    private fun initAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule())
                .build()
        mAppComponent.inject(this)
    }

}