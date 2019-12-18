package com.ssz.framejava.module.dagger.di.component;


import com.ssz.framejava.base.ui.dagger.di.component.AppComponent;
import com.ssz.framejava.base.ui.dagger.di.scope.ActivityScope;
import com.ssz.framejava.module.dagger.DaggerMvpExampleActivity;
import com.ssz.framejava.module.dagger.IDaggerMvpContract;
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

    @Component.Builder
     interface Builder{
        @BindsInstance
        // 这里可以用多态，为了有些地方需要传入view,提前打桩
        Builder view(IDaggerMvpContract.IView view);
        Builder addAppComponent(AppComponent appComponent);
        Builder daggerMvpModule(DaggerMvpModule module);
        MvpExampleComponent build();
    }
}
