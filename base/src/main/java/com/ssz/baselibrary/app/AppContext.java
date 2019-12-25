package com.ssz.baselibrary.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.simple.spiderman.SpiderMan;
import com.ssz.baselibrary.BuildConfig;
import com.ssz.baselibrary.R;
import com.ssz.baselibrary.app.di.component.AppComponent;
import com.ssz.baselibrary.app.di.component.DaggerAppComponent;
import com.ssz.baselibrary.app.di.module.AppModule;
import com.ssz.baselibrary.app.di.module.NetModule;
import com.ssz.baselibrary.app.func.IApp;
import com.ssz.baselibrary.app.helper.AppHelper;
import com.ssz.baselibrary.utils.log.TimberUtil;


/**
 * @author : zsp
 * time : 2019 11 2019/11/1 16:31
 */
public final class AppContext extends Application implements IApp {

    @Override
    public void onCreate() {
        super.onCreate();
        // 崩溃日志
        SpiderMan.init(this)
                .setTheme(R.style.SpiderManTheme_Dark);
        TimberUtil.init();
        AppHelper.init(this).toLog();
        // Dagger2 全局配置
        setupAppComponent();
        initARouter();
    }

    @Override
    public void initARouter() {
        if (BuildConfig.DEBUG){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
//        ARouter.getInstance().destroy();
    }

    /**
     * Dagger2 support
     */
    private AppComponent appComponent;
    public AppComponent getAppComponent(){
        return appComponent;
    }
    @Override
    public void setupAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();
        appComponent.inject(this);
    }
}
