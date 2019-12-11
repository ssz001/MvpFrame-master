package com.ssz.framejava.model.remote.net.nethttp;

import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.ssz.framejava.model.remote.net.execption.ApiException;
import com.ssz.framejava.model.remote.net.execption.TokenExpiredException;
import com.ssz.framejava.model.remote.net.response.ResponseCode;
import com.ssz.framejava.utils.ObjectHelper;
import com.ssz.framejava.utils.gsonutils.ReflectUtil;
import com.ssz.framejava.utils.log.LogUtil;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * @author zsp
 * create at 2019/1/23 13:44
 * 统一处理网络请求中出现的异常
 */
public final class ExceptionHandlerHttp {

    private static final String TAG = "eh_http";

    /**
     * 得到ApiException,用于外部调用
     */
    public static ApiException safeHandleException(Throwable e) {
        ApiException ex;
        try {
            ex = handleException(e);
        } catch (Exception e1) {
            ex = new ApiException(e1);
        }
        return ex;
    }

    /**
     * 请求过程中产生的异常统一处理
     *
     * @param e 请求过程中产生的异常
     * @return ApiException 封装后的异常
     */
    static ApiException handleException(Throwable e) throws Exception {
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
            ex = new ApiException(ResponseCode.NETWORK_ERROR, "连接超时!");
            return ex;
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            return dealHttpException(httpException);
        } else if (e instanceof ApiException) {
            // 用于非Rx流调用
            return (ApiException) e;
        } else {
            ex = new ApiException(ResponseCode.UNKNOWN, "未知错误!");
            return ex;
        }
    }

    /**
     * 注意 错误后台json里面不要返回status字段
     *
     * @param httpException http异常
     * @return ApiException
     */
    private static ApiException dealHttpException(HttpException httpException) throws Exception {
        int code;
        String message;
        // 后台抛出的HttpException 带有信息，在这里进行解析，然后封装成ApiException；
        ResponseBody resBody = httpException.response().errorBody();
        if (ObjectHelper.nonNull(resBody)) {
            String errorMsg;
            try {
                errorMsg = resBody.string();
            } catch (IOException e) {
                return new ApiException(e);
            }
            if (!TextUtils.isEmpty(errorMsg)) {
                ErrorResponse errorResponse = ReflectUtil.toObj(errorMsg, ErrorResponse.class);
                if (ObjectHelper.nonNull(errorResponse)) {
                    code = errorResponse.code;
                    message = errorResponse.message;
                    return selfApiException(code, message);
                }
            }
        }
        // 默认操作
        code = httpException.code();
        message = httpException.message();
        return new ApiException(code,message);
//        return selfApiException(code, message);
    }

    /**
     * 对http异常进行分类
     *
     * @param httpCode httpException 的code
     * @param message  httpException 的message
     * @return ApiException
     */
    private static ApiException selfApiException(int httpCode, String message) throws Exception {
        ApiException ex;
        LogUtil.e(TAG, "selfApiException:" + httpCode + message);
        switch (httpCode) {
            case ResponseCode.CODE_500:
                ex = new ApiException(httpCode, message);
                break;
            case ResponseCode.CERTIFICATE_INVALID:
                // 未测试
                throw new TokenExpiredException();
            default:
                ex = new ApiException(ResponseCode.HTTP_ERROR, message);
                break;
        }
        return ex;
    }

    /**
     * @author zsp
     * create at 2019/1/23 13:44
     * 错误Json 数据的类映射
     * 在一些情况下 状态码为code 一些为 status字段
     * code ：一般是后台返回的数据中带有code字段
     * stauts：一般是在后台数据已经不能返回的情况下，自动生成的状态码
     *
     *  2019.11.20 我忘了上面注释是什么意思？？？,如果两个status 和 code 不同时出现，修改没问题
     *  如果同时出现，确定优先级后再修改  ErrorResponse
     */
    private static class ErrorResponse {
        @SerializedName(value = "code", alternate = {"status"})
        private int code;
        @SerializedName(value = "message", alternate = {"msg"})
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
