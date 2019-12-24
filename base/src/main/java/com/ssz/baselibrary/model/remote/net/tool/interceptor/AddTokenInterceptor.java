package com.ssz.baselibrary.model.remote.net.tool.interceptor;

import android.accounts.AccountManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ssz.baselibrary.app.helper.AppHelper;
import com.ssz.baselibrary.cons.Constant;
import com.ssz.baselibrary.utils.SharedPreUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zsp
 * create at 2019/1/18 9:52
 * 在每个请求里加上Token（登录除外）
 */
public class AddTokenInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request;
            if (!TextUtils.isEmpty(SharedPreUtils.get().getString(Constant.CON_TOKEN))) {
            request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "bearer " + SharedPreUtils.get().getString(Constant.CON_TOKEN))
                    .build();
        } else {
            request = chain.request()
                    .newBuilder()
                    .build();
        }
        return chain.proceed(request);
    }
}
