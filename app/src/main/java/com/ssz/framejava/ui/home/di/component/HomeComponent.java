package com.ssz.framejava.ui.home.di.component;

import com.ssz.baselibrary.app.di.component.AppComponent;
import com.ssz.baselibrary.app.di.scope.ActivityScope;
import com.ssz.framejava.ui.home.HomeActivity;
import com.ssz.framejava.ui.home.IHomeContract;
import com.ssz.framejava.ui.home.di.module.HomeModule;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author : zsp
 * time : 2019 12 2019/12/23 15:24
 */
@ActivityScope
@Component(modules = {HomeModule.class},dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeActivity activity);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder view(IHomeContract.IView view);
        Builder appComponent(AppComponent appComponent);
        Builder homeModule(HomeModule homeModule);
        HomeComponent build();
    }
}
