package com.ssz.framejava.app;

import android.app.Application;

import com.ssz.framejava.base.app.func.IApp;
import com.ssz.framejava.base.app.helper.AppHelper;
import com.ssz.framejava.base.ui.dagger.di.component.AppComponent;
import com.ssz.framejava.base.ui.dagger.di.component.DaggerAppComponent;
import com.ssz.framejava.base.ui.dagger.di.module.AppModule;
import com.ssz.framejava.base.ui.dagger.di.module.NetModule;
import com.ssz.framejava.utils.log.TimberUtil;


/**
 * @author : zsp
 * time : 2019 11 2019/11/1 16:31
 */
public final class AppContext extends Application implements IApp {

    @Override
    public void onCreate() {
        super.onCreate();
        TimberUtil.init();
        AppHelper.init(this).toLog();
        // Dagger2 全局配置
        setupAppComponent();
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
