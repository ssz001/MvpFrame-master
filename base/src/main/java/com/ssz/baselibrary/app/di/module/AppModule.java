package com.ssz.baselibrary.app.di.module;

import com.ssz.baselibrary.app.AppContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author : zsp
 * time : 2019 11 2019/11/19 12:45
 */
@Module
public class AppModule {

    AppContext context;

    public AppModule(AppContext context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public AppContext provideAppContext() {
        return context;
    }
}
