package com.ssz.framejava.app;

import android.app.Application;
import android.content.Context;

import com.ssz.framejava.base.dagger.di.component.AppComponent;
import com.ssz.framejava.base.dagger.di.component.DaggerAppComponent;
import com.ssz.framejava.base.dagger.di.module.AppModule;
import com.ssz.framejava.base.dagger.di.module.NetModule;
import com.ssz.framejava.utils.log.TimberUtil;

import javax.inject.Inject;

import retrofit2.Retrofit;


/**
 * @author : zsp
 * time : 2019 11 2019/11/1 16:31
 */
public final class AppContext extends Application implements IApp {

    private static AppContext instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 初始化到极致操作
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Framework.init(this).registerReleaseListener(new Framework.IReleaseListener() {
            @Override
            public void release() {

                System.gc();
            }
        });
        init();
    }

    /**
     * 进入应用程序需要初始化的东西
     */
    public void init() {
        TimberUtil.init();
        // Dagger2 全局配置
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();
        appComponent.inject(this);
    }

    @Inject
    Retrofit mRetrofit;

    private AppComponent appComponent;

    public  AppComponent getAppComponent() {
        return appComponent;
    }

    public static AppContext get() {
        return instance;
    }

}
