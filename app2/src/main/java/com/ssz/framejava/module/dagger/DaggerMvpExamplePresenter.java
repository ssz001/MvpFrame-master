package com.ssz.framejava.module.dagger;


import com.ssz.framejava.T.SayBean;
import com.ssz.framejava.base.dagger.di.scope.ActivityScope;
import com.ssz.framejava.func.ISuccessListener;
import com.ssz.framejava.model.remote.net.Api;
import com.ssz.framejava.model.remote.net.Net;
import com.ssz.framejava.model.remote.net.execption.ApiException;
import com.ssz.framejava.model.remote.net.net200.RetryTransformer200;
import com.ssz.framejava.model.remote.net.schedulers.RxIoScheduler;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * @author : zsp
 * time : 2019 11 2019/11/20 10:42
 */
@ActivityScope
public class DaggerMvpExamplePresenter implements IDaggerMvpContract.IPresenter {

    private DaggerMvpExampleActivity mView;

    @Inject
    Api mApi;

    @Inject
    public DaggerMvpExamplePresenter(DaggerMvpExampleActivity view) {
        this.mView = view;
        //false
        Timber.d("DaggerMvpExampleActivity == null? : %s", (view == null));
    }

    @Override
    public void detach() {
        this.mView = null;
    }

    @Override
    public Disposable getJoke() {
        mView.showProgress();
        Disposable d = Net.request().getJoke(1, 2, "video")
                .compose(new RxIoScheduler<>())
                .compose(RetryTransformer200.handleException())
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
        // NetModule
        Disposable d = mApi.getJoke(1, 2, "video")
                .compose(new RxIoScheduler<>())
                .compose(RetryTransformer200.handleException())
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
}
