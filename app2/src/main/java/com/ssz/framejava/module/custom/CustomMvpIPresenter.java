package com.ssz.framejava.module.custom;


import com.ssz.framejava.T.SayBean;
import com.ssz.framejava.base.func.ISuccessListener;
import com.ssz.framejava.base.ui.view.IActivity;
import com.ssz.framejava.model.remote.net.Net;
import com.ssz.framejava.model.remote.net.execption.ApiException;
import com.ssz.framejava.model.remote.net.handler.single.net200.RetryTransformer200;
import com.ssz.framejava.model.remote.net.schedulers.lamada.RxIo;
import com.ssz.framejava.utils.RxLifecycleUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 11 2019/11/20 9:55
 */
public class CustomMvpIPresenter implements CustomMvpContract.IPresenter {

    private CustomMvpContract.IView mView;

    @Override
    public void attach(CustomMvpContract.IView view) {
        this.mView = view;
    }

    @Override
    public void detach() {
        this.mView = null;
    }

    @Override
    public Disposable getJoke() {
        mView.showProgress();
        Disposable d = Net.request().getJoke(1, 2, "video")
                .compose(RxIo.applySinale())
                .compose(RetryTransformer200.handleException())
                .compose(RxLifecycleUtil.bindUntilEvent((IActivity) mView,ActivityEvent.DESTROY))
                .subscribe(sayBeans -> {
                            mView.hideProgress();
                            mView.success(sayBeans);
                        },
                        throwable -> {
                            mView.hideProgress();
                            mView.error(ApiException.cast(throwable));
                        });
        return d;
    }

    @Override
    public Disposable getJoke2(ISuccessListener<List<SayBean>> listener) {
        mView.showProgress();
        Disposable d = Net.request().getJoke(1, 2, "video")
                .compose(RxIo.applySinale())
                .compose(RetryTransformer200.handleException())
                .subscribe(result -> {
                            mView.hideProgress();
                            mView.success(result);
                        },
                        throwable -> {
                            mView.hideProgress();
                            mView.error(ApiException.cast(throwable));
                        });
        return d;
    }

}
