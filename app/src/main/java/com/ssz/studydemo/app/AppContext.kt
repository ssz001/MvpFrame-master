package com.ssz.studydemo.app

import android.content.Context
import com.ssz.studydemo.base.dagger.di.component.AppComponent
import com.ssz.studydemo.base.dagger.di.component.DaggerAppComponent
import com.ssz.studydemo.base.dagger.di.module.AppModule
import com.ssz.studydemo.base.dagger.di.module.NetModule
import com.ssz.studydemo.test.AppDelegate
import com.ssz.studydemo.test.IApp
import com.ssz.studydemo.utils.log.LogUtils
import com.ssz.studydemo.utils.network.NetworkManager
import retrofit2.Retrofit
import javax.inject.Inject


/**
 * @author : zsp
 * time : 2019 09 2019/9/18 15:44
 */
class AppContext : BaseApp(), IApp {

    var mAppDelegate : AppDelegate? = null

    companion object {
        private lateinit var  appContext: AppContext
        fun getInstance(): AppContext {
            return appContext
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        appContext = this
        if (null == mAppDelegate) mAppDelegate = AppDelegate()
        mAppDelegate?.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        mAppDelegate?.onCreate(this)

        // 注册释放监听
        Framework.init(this).registerReleaseListener {

            System.gc()
        }
        // 网络状态监听框架注册
        NetworkManager.getDefault().init(this)
        inject()
        LogUtils.d("mmmRe","mRe = $mRetrofit" )
    }

    lateinit var appComponent : AppComponent
    @Inject lateinit var mRetrofit : Retrofit
    fun inject(){
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule())
                .build()
        appComponent.inject(this)
    }

}