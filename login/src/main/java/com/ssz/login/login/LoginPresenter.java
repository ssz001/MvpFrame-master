package com.ssz.login.login;

import com.ssz.baselibrary.app.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 * @author : zsp
 * time : 2019 12 2019/12/24 9:56
 */
@ActivityScope
public class LoginPresenter implements ILoginContract.IPresenter {

    private ILoginContract.IView mView;

    @Inject
    LoginPresenter(ILoginContract.IView view){
        this.mView = view;
    }

    @Override
    public void detach() {
        this.mView = null;
    }
}
