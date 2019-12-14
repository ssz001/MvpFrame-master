package com.ssz.studydemo.app

import android.content.Context
import android.content.res.Configuration
import com.ssz.studydemo.test.AppDelegate
import com.ssz.studydemo.test.IApp
import com.ssz.studydemo.utils.network.NetworkManager


/**
 * @author : zsp
 * time : 2019 09 2019/9/18 15:44
 */
class AppContext : BaseApp(), IApp {

    private val mAppDelegate: AppDelegate by lazy { AppDelegate() }

    companion object {
        private lateinit var  appContext: AppContext
        fun getInstance(): AppContext {
            return appContext
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        appContext = this
        mAppDelegate.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        mAppDelegate.onCreate(this)

        // 注册释放监听
        Framework.init(this).registerReleaseListener {

            System.gc()
        }
        // 网络状态监听框架注册
        NetworkManager.getDefault().init(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mAppDelegate.onTrimMemory(level)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        mAppDelegate.onConfigurationChanged(newConfig)
        super.onConfigurationChanged(newConfig)
    }
}