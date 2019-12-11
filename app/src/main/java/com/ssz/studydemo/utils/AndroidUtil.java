package com.ssz.studydemo.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * @author : zsp
 * time : 2019 10 2019/10/12 10:49
 */
public final class AndroidUtil {

    private AndroidUtil(){}

    /**
     * 是不是主线程
     */
    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 建立一个新的主线程的Handler
     */
    public static Handler createMainHandler(){
        return new Handler(Looper.getMainLooper());
    }

    /**
     * 建立一个新的Handler
     */
    public static Handler createHandler(){
        return new Handler();
    }

}
