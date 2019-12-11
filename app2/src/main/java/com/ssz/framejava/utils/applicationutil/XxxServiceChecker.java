package com.ssz.framejava.utils.applicationutil;

import android.support.annotation.NonNull;

import java.util.concurrent.CountDownLatch;

/**
 * @author : zsp
 * time : 2019 10 2019/10/14 13:45
 */
public class XxxServiceChecker extends BaseServiceChecker {

    public XxxServiceChecker(@NonNull CountDownLatch latch) {
        super(latch, "XxxServiceChecker");
    }

    /**
     * 启动服务逻辑
     */
    @Override
    protected void verifyService() {
        // todo  初始化的逻辑代码
    }
}
