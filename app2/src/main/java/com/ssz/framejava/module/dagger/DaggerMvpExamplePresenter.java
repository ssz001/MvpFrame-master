package com.ssz.framejava.module.dagger;


import com.ssz.framejava.T.SayBean;
import com.ssz.framejava.base.func.ISuccessListener;
import com.ssz.framejava.base.ui.dagger.di.scope.ActivityScope;
import com.ssz.framejava.base.ui.view.IActivity;
import com.ssz.framejava.model.remote.net.Api;
import com.ssz.framejava.model.remote.net.Net;
import com.ssz.framejava.model.remote.net.execption.ApiException;
import com.ssz.framejava.model.remote.net.net200.RetryTransformer200;
import com.ssz.framejava.model.remote.net.schedulers.RxIoScheduler;
import com.ssz.framejava.model.remote.net.schedulers.lamada.RxIo;
import com.ssz.framejava.utils.RxLifecycleUtil;
import com.ssz.framejava.utils.log.LogUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

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

    @Inject
    IDaggerMvpContract.IView mView;
    @Inject
    Api mApi;

//    @Inject
//    public DaggerMvpExamplePresenter(IDaggerMvpContract.IView view) {
//        this.mView = view;
//        //false
//        Timber.d("DaggerMvpExampleActivity == null? : %s", (view == null));
//    }

    @Inject
    public DaggerMvpExamplePresenter() {
        Timber.d("DaggerMvpExampleActivity == null? : %s", (mView == null));
    }

    @Override
    public void detach() {
        this.mView = null;
        this.mApi = null;
    }

    @Override
    public Disposable getJoke() {
        mView.showProgress();
        Disposable d = Net.request().getJoke(1, 2, "video")
//                .doOnSubscribe(disposable -> {
                    // RxCachedThreadScheduler-1
//                    LogUtil.d("getJoke","doOnSubscribe()" + Thread.currentThread().getName());
//                    mView.showProgress();
//                })
                .compose(RxIo.applySinale())
                .compose(RetryTransformer200.handleException())
                .compose(RxLifecycleUtil.bindUntilEvent((IActivity) mView,ActivityEvent.DESTROY))
                .doFinally(() -> {
                    // main
                    LogUtil.d("getJoke","doFinally()" + Thread.currentThread().getName());
                    mView.hideProgress();
                })
                .subscribe(sayBeans -> mView.success(sayBeans),
                        throwable -> mView.error(ApiException.cast(throwable)));
        return d;
    }

    @Override
    public Disposable getJoke2(ISuccessListener<List<SayBean>> listener) {
        mView.showProgress();
        // NetModule
        Disposable d = mApi.getJoke(1, 2, "video")
                .compose(new RxIoScheduler<>())
                .compose(RetryTransformer200.handleException())
                .compose(RxLifecycleUtil.bindUntilEvent((IActivity) mView,ActivityEvent.DESTROY))
                .subscribe(sayBeans -> {
                            mView.hideProgress();
                            listener.result(sayBeans);
                        },
                        throwable -> {
                            mView.hideProgress();
                            mView.error(ApiException.cast(throwable));
                        });
        return d;
    }
}
