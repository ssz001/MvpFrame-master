package com.ssz.user.user;

import com.ssz.baselibrary.app.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 * @author : zsp
 * time : 2019 12 2019/12/24 9:26
 */
@ActivityScope
public class UserPresenter implements IUserContract.IPresenter {

    private IUserContract.IView mView;

    @Inject
    UserPresenter(IUserContract.IView view){
        this.mView = view;
    }

    @Override
    public void detach() {
        this.mView = null;
    }
}
