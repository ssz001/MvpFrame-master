package com.ssz.baselibrary.app.di.component;

import com.ssz.baselibrary.app.AppContext;
import com.ssz.baselibrary.app.di.module.AppModule;
import com.ssz.baselibrary.app.di.module.NetModule;
import com.ssz.baselibrary.model.remote.net.Api;
import com.ssz.baselibrary.model.remote.net.Net;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author : zsp
 * time : 2019 11 2019/11/19 10:58
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {

    AppContext getAppContext();

    Net getNet();

    Api getApi();

    void inject(AppContext appContext);
}
