package com.ssz.frame.model.net.net200;

import android.util.Log;

import com.ssz.frame.model.net.execption.ApiException;
import com.ssz.frame.model.net.response.ResponseCode;
import com.ssz.frame.model.net.response.Result;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;


/**
 * @author zsp
 * create at 2019/1/23 13:51
 * RxJava；加工处理返回的数据
 */
public final class ResponseTransformer200 {

    private static final String TAG = "ResponseTransformer200";

    public static <T> SingleTransformer<Result<T>, T> handleException() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }

    /**
     * RxJava 错误收集处理，发送一个新的Observable对象（Throwable）
     * 非服务器错误
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, SingleSource<? extends Result<T>>> {
        @Override
        public SingleSource<? extends Result<T>> apply(Throwable throwable) throws Exception {
            Log.i(TAG, "ErrorResumeFunction：错误收集");
            return Single.error(ExceptionHandler200.handleException(throwable));
        }
    }

    /**
     * 数据返回正常时 对 code 进行分类，分为错误的code 和 正常的code
     * 正常的code发送Observable.just()发送    --  走正常通道
     * 错误的code发送Observable.error()发送   --  走异常通道
     */
    private static class ResponseFunction<T> implements Function<Result<T>, SingleSource<T>> {
        @Override
        public SingleSource<T> apply(Result<T> tResult) throws Exception {
            Log.i(TAG, "ResponseFunction：code分类处理");
            return dealResponseCode(tResult);
        }
    }

    /**
     * 对返回的数据进行分类处理
     * 服务器错误整理
     * public
     */
    public static <T> SingleSource<T> dealResponseCode(Result<T> tResult) throws Exception {
        int code = tResult.getCode();
        String message = tResult.getMessage();
        Single<T> single;
        Log.e(TAG, "code:" + code + " message:" + message);
        switch (code) {
            case ResponseCode.SUCCESS:
                single = Single.just(tResult.getData());
                break;
            case ResponseCode.CERTIFICATE_INVALID:
                single = Single.error(new ApiException(ResponseCode.CERTIFICATE_INVALID, "刷新凭证失败"));
                break;
            default:
                single = Single.error(new ApiException(code, message));
                break;
        }
        return single;
    }
}
