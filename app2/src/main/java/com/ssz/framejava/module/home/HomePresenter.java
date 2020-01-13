package com.ssz.framejava.module.home;


import com.ssz.framejava.base.func.ISuccessListener;

import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 10 2019/10/10 13:18
 */
public class HomePresenter implements IHomeContract.IPresenter {

    private IHomeContract.IView mView;

    @Override
    public void attach(IHomeContract.IView IView) {
        this.mView = IView;
    }

    @Override
    public void detach() {
        this.mView = null;
    }

    @Override
    public Disposable login() {
        Disposable d = null;

        return d;
    }

    @Override
    public Disposable login2(ISuccessListener<Boolean> listener) {
        listener.result(false);
        return null;
    }
}
