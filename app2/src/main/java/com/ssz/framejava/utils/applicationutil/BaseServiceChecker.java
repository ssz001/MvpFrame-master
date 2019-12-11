package com.ssz.framejava.utils.applicationutil;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * @author : zsp
 * time : 2019 10 2019/10/14 13:18
 */
public abstract class BaseServiceChecker implements Runnable {

    private static final String TAG = BaseServiceChecker.class.getSimpleName();

    private final CountDownLatch latch;
    private final String serviceName;
    private boolean isServiceUp;

    public BaseServiceChecker(@NonNull CountDownLatch latch, String serviceName) {
        this.latch = latch;
        this.serviceName = serviceName;
        this.isServiceUp = false;
    }

    @Override
    public void run() {
        try {
            verifyService();
            isServiceUp = true;
        } catch (Throwable t) {
            Log.e(TAG,serviceName +"launch error" + t.initCause(t));
            isServiceUp = false;
        } finally {
            if (latch != null) {
                latch.countDown();
            }
        }
    }

    public String getServiceName() {
        return serviceName;
    }

    public boolean isServiceUp() {
        return isServiceUp;
    }

    /**
     * 启动服务逻辑
     */
    protected abstract void verifyService();
}
