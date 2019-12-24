package com.ssz.login.login.di.component;

import com.ssz.baselibrary.app.di.component.AppComponent;
import com.ssz.baselibrary.app.di.scope.ActivityScope;
import com.ssz.login.login.LoginActivity;
import com.ssz.login.login.ILoginContract;
import com.ssz.login.login.di.module.LoginModule;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author : zsp
 * time : 2019 12 2019/12/24 9:43
 */
@ActivityScope
@Component(modules = LoginModule.class,dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder view(ILoginContract.IView view);
        Builder appComponent(AppComponent appComponent);
        Builder loginModule(LoginModule loginModule);
        LoginComponent build();
    }
}
