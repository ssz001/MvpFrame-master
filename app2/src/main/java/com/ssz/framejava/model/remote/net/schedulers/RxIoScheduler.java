package com.ssz.framejava.model.remote.net.schedulers;


import com.ssz.framejava.model.remote.net.schedulers.base.RxScheduler;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;

/**
 * @author zsp
 * create at 2019/1/18 14:47
 * RxJava订阅线程调度
 */
public class RxIoScheduler<T> extends RxScheduler implements
        SingleTransformer<T,T> ,ObservableTransformer<T,T>,MaybeTransformer<T,T>,FlowableTransformer<T,T> {

    @Override
    public SingleSource<T> apply(Single<T> upstream) {
        return upstream.subscribeOn(io())
                .observeOn(ui());
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.subscribeOn(io())
                .observeOn(ui());
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.subscribeOn(io())
                .observeOn(ui());
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream) {
        return upstream.subscribeOn(io())
                .observeOn(ui());
    }
}
