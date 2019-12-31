package com.ssz.baselibrary.model.remote.net.schedulers.lamada;

import io.reactivex.FlowableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : zsp
 * time : 2019 12 2019/12/31 16:29
 */
public final class RxNewThread {
    public static <R> SingleTransformer<R,R> applySinale() {
        return upstream -> upstream
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <R> ObservableTransformer<R,R> applyObservable() {
        return upstream -> upstream
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <R> FlowableTransformer<R,R> applyFlowable() {
        return upstream -> upstream
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <R> MaybeTransformer<R,R> applyMaybe() {
        return upstream -> upstream
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
