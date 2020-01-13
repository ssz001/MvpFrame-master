package com.ssz.framejava.module.dagger.di.module;


import android.app.Activity;
import android.os.Handler;

import com.ssz.framejava.base.ui.dagger.di.scope.ActivityScope;
import com.ssz.framejava.module.dagger.DaggerMvpExampleActivity;
import com.ssz.framejava.module.dagger.IDaggerMvpContract;
import com.ssz.framejava.widget.window.dialog.loading.LoadingDialog;

import dagger.Module;
import dagger.Provides;

/**
 * @author : zsp
 * time : 2019 11 2019/11/20 10:53
 */
@Module
public class DaggerMvpModule {

    @ActivityScope
    @Provides
    public Handler provideHandler(){
        return new Handler();
    }

    @ActivityScope
    @Provides
    public LoadingDialog provideLoadingDialog(IDaggerMvpContract.IView view){
        return new LoadingDialog((Activity)view);
    }
}
