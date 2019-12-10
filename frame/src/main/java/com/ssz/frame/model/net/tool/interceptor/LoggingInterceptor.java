package com.ssz.frame.model.net.tool.interceptor;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author zsp
 * create at 2019/1/18 9:52
 * okhttp Log 拦截器
 */
public class LoggingInterceptor implements Interceptor {

    /**
     * 请求发起的时间 long t1 = System.nanoTime();
     * 收到响应的时间 long t2 = System.nanoTime();
     * <p>
     * 不能直接使用response.body（）.string()的方式输出日志
     * 因为response.body().string()之后，response中的流会被关闭，程序会报错，
     * 我们需要创建出一个新的response给应用层处理
     *
     * @param chain Chain 里包含了request和response
     */
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.d("intercept",String.format("发送请求 %s on %s%n%s",request.url(),chain.connection(),request.headers()));
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Log.d("intercept",String.format("接收响应：[%s] %n返回json:%s  %.1fms%n%s",
                response.request().url(),
                responseBody.string(),
                (t2-t1) /1e6d,
                response.headers()
        ));
        return response;
    }
}
