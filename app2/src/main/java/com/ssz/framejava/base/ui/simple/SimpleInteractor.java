package com.ssz.framejava.base.ui.simple;


import com.ssz.framejava.T.SayBean;
import com.ssz.framejava.model.remote.net.Net;
import com.ssz.framejava.model.remote.net.execption.ApiException;
import com.ssz.framejava.model.remote.net.handler.single.net200.RetryTransformer200;
import com.ssz.framejava.model.remote.net.schedulers.lamada.RxIo;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 11 2019/11/22 12:55
 */
public class SimpleInteractor {

    interface IGetJokeFinishListener {
        void getJokeFinish(List<SayBean> sayBeans);

        void getJokeFailure(ApiException ex);
    }

    /**
     * Disposable 如果放在这里，取消订阅要穿透两个类，所以这里直接返回
     */
    public Disposable getJoke(int page, int count, String type, IGetJokeFinishListener listener) {
        Disposable d = Net.request().getJoke(page, count, type)
                .compose(RxIo.applySingle())
                .compose(RetryTransformer200.handleException())
                .subscribe(listener::getJokeFinish, throwable ->
                        listener.getJokeFailure(ApiException.cast(throwable)));
        return d;
    }

}
