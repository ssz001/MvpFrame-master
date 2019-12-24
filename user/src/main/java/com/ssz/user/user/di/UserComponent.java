package com.ssz.user.user.di;

import com.ssz.baselibrary.app.di.component.AppComponent;
import com.ssz.baselibrary.app.di.scope.ActivityScope;
import com.ssz.user.user.IUserContract;
import com.ssz.user.user.UserActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author : zsp
 * time : 2019 12 2019/12/24 9:32
 */
@ActivityScope
@Component(modules = UserModule.class,dependencies = AppComponent.class)
public interface UserComponent {
    void inject(UserActivity activity);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder view(IUserContract.IView view);
        Builder appComponent(AppComponent appComponent);
        Builder userModule(UserModule userModule);
        UserComponent build();
    }
}
