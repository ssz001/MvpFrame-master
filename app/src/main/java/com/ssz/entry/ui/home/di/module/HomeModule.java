package com.ssz.entry.ui.home.di.module;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.ssz.baselibrary.app.di.scope.ActivityScope;
import com.ssz.baselibrary.utils.ExitProxy;
import com.ssz.entry.ui.home.IHomeContract;

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

    @ActivityScope
    @Provides
    public ExitProxy provideExitProxy(IHomeContract.IView view){
        return new ExitProxy((Activity)view,false);
    }
}
