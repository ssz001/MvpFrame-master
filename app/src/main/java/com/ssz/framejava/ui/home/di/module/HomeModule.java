package com.ssz.framejava.ui.home.di.module;

import android.os.Handler;
import android.os.Looper;

import com.ssz.baselibrary.app.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author : zsp
 * time : 2019 12 2019/12/23 15:27
 */
@Module
public class HomeModule {

    @ActivityScope
    @Provides
    public String provideModuleName(){
        return HomeModule.class.getSimpleName();
    }

    @ActivityScope
    @Provides
    public Handler provideMainHandler(){
        return new Handler(Looper.myLooper());
    }
}
