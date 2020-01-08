package com.ssz.framejava.model.remote.net.handler.observable.net200;

import android.util.Log;

import com.ssz.framejava.model.remote.net.execption.ApiException;
import com.ssz.framejava.model.remote.net.execption.TokenExpiredException;
import com.ssz.framejava.model.remote.net.handler.ExceptionHandler200;
import com.ssz.framejava.model.remote.net.response.ResponseCode;
import com.ssz.framejava.model.remote.net.response.Result;
import com.ssz.framejava.utils.log.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * @author : zsp
 * time : 2020 01 2020/1/8 8:42
 */
public final class RetryTransformer200 {

    private static final String TAG = "RetryTransformer200";

    public static <T> ObservableTransformer<Result<T>, T> handleException() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>())
                .retryWhen(new RetryFunction());
    }

    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends Result<T>>> {
        @Override
        public ObservableSource<? extends Result<T>> apply(Throwable throwable) throws Exception {
            Log.d(TAG, "ErrorResumeFunction：错误收集");
            return Observable.error(ExceptionHandler200.handleException(throwable));
        }
    }

    private static class ResponseFunction<T> implements Function<Result<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(Result<T> tResult) throws Exception {
            return dealResponseCode(tResult);
        }
    }

    private static class RetryFunction implements Function<Observable<Throwable>, ObservableSource<?>> {

        /**
         * 重试次数
         */
        private int retryTime;

        @Override
        public ObservableSource<?> apply(Observable<Throwable> thr) throws Exception {
            try {
                return thr.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        if (throwable instanceof TokenExpiredException) {
                            if (retryTime < 3) {
                                retryTime++;
                                return getToken();
                            }
                            return Observable.error(new ApiException(throwable));
                        }
                        return Observable.error(throwable);
                    }
                });
            } catch (Exception e) {
                // 统一归类为ApiException
                return Observable.error(new ApiException(e));
            }
        }
    }

    /**
     * 默认实现: 不启用刷新Token
     */
    private static ObservableSource<?> getToken() {
        // todo 实现 更新Token 逻辑
        final int code = ResponseCode.CERTIFICATE_INVALID;
        return Observable.error(new ApiException(code, "error:" + code));
//        return PublishProcessor.just("sss").compose(new RxUiScheduler<>())
//                .doOnNext(s -> Log.d("token_", "getToken()"));
    }

    /**
     * 对返回的数据进行分类处理
     * 服务器错误整理
     */
    private static <T> ObservableSource<T> dealResponseCode(Result<T> tResult) throws Exception {
        int code = tResult.getCode();
        String message = tResult.getMessage();
        Observable<T> observable;
        LogUtil.e(TAG, "code:" + code + " message:" + message + " data:" + tResult.getData());
        switch (code) {
            case ResponseCode.SUCCESS:
                observable = Observable.just(tResult.getData());
                break;
            case ResponseCode.CERTIFICATE_INVALID:
                observable = Observable.error(new TokenExpiredException());
                break;
            default:
                observable = Observable.error(new ApiException(code, message));
                break;
        }
        return observable;
    }
}
