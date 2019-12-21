package com.ssz.studydemo.app

import android.app.Application
import com.ssz.studydemo.base.app.func.IApp
import com.ssz.studydemo.base.app.helper.AppHelper
import com.ssz.studydemo.base.ui.dagger.di.component.AppComponent
import com.ssz.studydemo.base.ui.dagger.di.component.DaggerAppComponent
import com.ssz.studydemo.base.ui.dagger.di.module.AppModule
import com.ssz.studydemo.base.ui.dagger.di.module.NetModule
import com.ssz.studydemo.utils.network.NetworkManager

/**
 * @author : zsp
 * time : 2019 12 2019/12/17 10:04
 */
class AppContext : Application(), IApp {

    var ji = "我在 master 里 加了个成员变量"

    override fun onCreate() {
        super.onCreate()
        AppHelper.init(this).toLog()
        setupAppComponent()
        // 网络状态监听框架注册
        NetworkManager.getDefault().init(this)
    }

    /**
     * supperDagger2
     */
    override lateinit var mAppComponent: AppComponent
    override fun setupAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule())
                .build()
        mAppComponent.inject(this)
    }
}