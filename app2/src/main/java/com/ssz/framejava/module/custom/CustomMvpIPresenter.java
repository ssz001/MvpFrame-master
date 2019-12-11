package com.ssz.framejava.module.custom;


import com.ssz.framejava.T.SayBean;
import com.ssz.framejava.func.ISuccessListener;
import com.ssz.framejava.model.remote.net.Net;
import com.ssz.framejava.model.remote.net.execption.ApiException;
import com.ssz.framejava.model.remote.net.net200.RetryTransformer200;
import com.ssz.framejava.model.remote.net.schedulers.RxIoScheduler;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 11 2019/11/20 9:55
 */
public class CustomMvpIPresenter implements CustomMvpContract.IPresenter {

    private CustomMvpContract.IView mIView;

    @Override
    public void attach(CustomMvpContract.IView IView) {
        this.mIView = IView;
    }

    @Override
    public void detach() {
        this.mIView = null;
    }

    @Override
    public Disposable getJoke() {
        mIView.showProgress();
        Disposable d = Net.request().getJoke(1, 2, "video")
                .compose(new RxIoScheduler<>())
                .compose(RetryTransformer200.handleException())
                .subscribe(sayBeans -> {
                            mIView.hideProgress();
                            mIView.success(sayBeans);
                        },
                        throwable -> {
                            mIView.hideProgress();
                            mIView.error(ApiException.cast(throwable));
                        });
        return d;
    }

    @Override
    public Disposable getJoke2(ISuccessListener<List<SayBean>> listener) {
        mIView.showProgress();
        Disposable d = Net.request().getJoke(1, 2, "video")
                .compose(new RxIoScheduler<>())
                .compose(RetryTransformer200.handleException())
                .subscribe(result -> {
                            mIView.hideProgress();
                            mIView.success(result);
                        },
                        throwable -> {
                            mIView.hideProgress();
                            mIView.error(ApiException.cast(throwable));
                        });
        return d;
    }

}
