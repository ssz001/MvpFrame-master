package com.ssz.framejava.ui.home;

import javax.inject.Inject;

/**
 * @author : zsp
 * time : 2019 12 2019/12/23 15:20
 */
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
