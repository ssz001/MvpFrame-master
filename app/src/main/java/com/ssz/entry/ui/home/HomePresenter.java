package com.ssz.entry.ui.home;

import com.ssz.baselibrary.app.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 * @author : zsp
 * time : 2019 12 2019/12/23 15:20
 */
@ActivityScope
public class HomePresenter implements IHomeContract.IPresenter {

    private IHomeContract.IView mView;

    @Inject HomePresenter(IHomeContract.IView view){
        this.mView = view;
    }

    @Override
    public void detach() {
        this.mView = null;
    }
}
