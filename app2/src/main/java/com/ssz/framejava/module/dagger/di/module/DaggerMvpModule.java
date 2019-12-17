package com.ssz.framejava.module.dagger.di.module;


import com.ssz.framejava.base.ui.dagger.di.scope.ActivityScope;

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
    public String getStudent() {
        return "ss";
    }

}
