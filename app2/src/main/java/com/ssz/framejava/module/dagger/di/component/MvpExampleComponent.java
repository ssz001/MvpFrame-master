package com.ssz.framejava.module.dagger.di.component;


import com.ssz.framejava.base.dagger.di.component.AppComponent;
import com.ssz.framejava.base.dagger.di.scope.ActivityScope;
import com.ssz.framejava.module.dagger.DaggerMvpExampleActivity;
import com.ssz.framejava.module.dagger.di.module.DaggerMvpModule;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author : zsp
 * time : 2019 11 2019/11/20 10:57
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = {DaggerMvpModule.class})
public interface MvpExampleComponent {
    void inject(DaggerMvpExampleActivity activity);

//    String getView();

    @Component.Builder
     interface Builder{
        @BindsInstance
        Builder view(DaggerMvpExampleActivity view);
        Builder addAppComponent(AppComponent appComponent);
        Builder daggerMvpModule(DaggerMvpModule module);
        MvpExampleComponent build();
    }
}