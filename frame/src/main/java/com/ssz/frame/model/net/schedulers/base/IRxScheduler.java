package com.ssz.frame.model.net.schedulers.base;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * @author zsp
 * create at 2019/1/18 14:44
 * 调度顶层接口，工厂抽象
 */
public interface IRxScheduler {

    /**
     * CPU计算线程
     */
    @NonNull
    Scheduler computation();

    /**
     * IO线程
     */
    @NonNull
    Scheduler io();

    /**
     * UI线程
     */
    @NonNull
    Scheduler ui();


    @NonNull
    Scheduler newThread();

}
