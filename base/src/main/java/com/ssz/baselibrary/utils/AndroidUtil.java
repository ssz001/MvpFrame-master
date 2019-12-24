package com.ssz.baselibrary.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.ssz.baselibrary.utils.log.LogUtil;


/**
 * @author : zsp
 * time : 2019 10 2019/10/12 10:49
 */
public final class AndroidUtil {

    private static Handler mMainHandler;

    private AndroidUtil() {
    }

//    /**  线程池提取缓存中的数据 */
//    private static ExecutorService executors = new ThreadPoolExecutor(1,
//            1,
//            0L,
//            TimeUnit.SECONDS, new LinkedBlockingQueue<>(1), r -> {
//        Thread thread = new Thread(r);
//        thread.setName("x线程");
//        return thread;
//    }, (r, executor) -> {
//        if (!executor.isShutdown()){
//            executor.getQueue().poll();
//            executor.execute(r);
//        }
//    });

    /**
     * 非密集型调用
     * 创建一个全局的 UI handler，注意潜在的内存泄露问题
     */
    public static Handler getGlobalMainHandler() {
        if (null == mMainHandler) {
            synchronized (AndroidUtil.class) {
                if (null == mMainHandler) {
                    mMainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return mMainHandler;
    }

    /**
     * 是不是主线程
     */
    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 打印当前线程的名字
     */
    public static void logThreadName(@NonNull String tag){
        LogUtil.d(tag,Thread.currentThread().getName());
    }

    /**
     * 建立一个新的主线程的Handler
     */
    public static Handler createMainHandler() {
        return new Handler(Looper.getMainLooper());
    }

    /**
     * 建立一个新的Handler
     */
    public static Handler createHandler() {
        return new Handler();
    }
}
