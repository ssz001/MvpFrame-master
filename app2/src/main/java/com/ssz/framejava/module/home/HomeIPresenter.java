package com.ssz.framejava.module.home;


import com.ssz.framejava.func.ISuccessListener;

import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 10 2019/10/10 13:18
 */
public class HomeIPresenter implements IHomeContract.IPresenter {

    private IHomeContract.IView mIView;

    @Override
    public void attach(IHomeContract.IView IView) {
        this.mIView = IView;
    }

    @Override
    public void detach() {

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
