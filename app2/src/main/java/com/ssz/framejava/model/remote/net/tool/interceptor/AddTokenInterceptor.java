package com.ssz.framejava.model.remote.net.tool.interceptor;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ssz.framejava.account.AccountManager;
import com.ssz.framejava.base.app.helper.AppHelper;

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
        if (!TextUtils.isEmpty(AccountManager.getToken(AppHelper.getApplication()))) {
            request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "bearer " + AccountManager.getToken(AppHelper.getApplication()))
                    .build();
        } else {
            request = chain.request()
                    .newBuilder()
                    .build();
        }
        return chain.proceed(request);
    }
}
