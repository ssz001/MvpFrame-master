package com.ssz.baselibrary.model.remote.net.handler;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.ssz.baselibrary.model.remote.net.execption.ApiException;
import com.ssz.baselibrary.model.remote.net.response.ResponseCode;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * @author : zsp
 * time : 2019 11 2019/11/4 9:44
 * 请求过程中出现的错误处理
 */
public final class ExceptionHandler200 {

    /**
     * 请求过程中产生的异常统一处理
     *
     * @param e 请求过程中产生的异常
     * @return ApiException 封装后的异常
     */
    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(ResponseCode.PARSE_ERROR, "数据解析出错!");
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(ResponseCode.NETWORK_ERROR, "连接出错!");
            return ex;
        } else if (e instanceof UnknownHostException
                || e instanceof SocketTimeoutException) {
            ex = new ApiException(ResponseCode.NETWORK_TIMEOUT, "连接超时!");
            return ex;
        } else if (e instanceof ApiException) {
            // 用于非Rx流调用
            return (ApiException) e;
        } else {
            ex = new ApiException(ResponseCode.UNKNOWN, "未知错误!");
            return ex;
        }
    }
}
