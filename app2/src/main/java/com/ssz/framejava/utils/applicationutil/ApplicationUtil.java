package com.ssz.framejava.utils.applicationutil;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : zsp
 * time : 2019 10 2019/10/14 13:38
 * 阻塞当前线程，服务启动后恢复,
 * 注意android 主线程阻塞的后果
 */
public class ApplicationUtil {

    public static boolean checkServerUp() throws Exception {
        // 数量同步更改
        final CountDownLatch latch = new CountDownLatch(1);
        final List<Runnable> services = new ArrayList<>();
        services.add(new XxxServiceChecker(latch));

        int size = services.size();
        ExecutorService executorService = new ThreadPoolExecutor(size, size, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), r -> {
                    Thread thread = new Thread(r);
                    thread.setName("ApplicationUtil service thread");
                    return thread;
                });

        for (final Runnable v : services) {
            executorService.execute(v);
        }

        latch.await();

        for (final Runnable v : services) {
            BaseServiceChecker baseServiceChecker = (BaseServiceChecker) v;
            if (!baseServiceChecker.isServiceUp()) {
                return false;
            }
        }
        return true;
    }
}
