package com.ssz.baselibrary.model.remote.net.handler.single.net200;

import android.util.Log;

import com.ssz.baselibrary.model.remote.net.execption.ApiException;
import com.ssz.baselibrary.model.remote.net.execption.TokenExpiredException;
import com.ssz.baselibrary.model.remote.net.handler.ExceptionHandler200;
import com.ssz.baselibrary.model.remote.net.response.ResponseCode;
import com.ssz.baselibrary.model.remote.net.response.Result;
import com.ssz.baselibrary.utils.log.LogUtil;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;

/**
 * @author : zsp
 * time : 2019 11 2019/11/12 9:43
 */
public final class RetryTransformer200 {

    private static final String TAG = "RetryTransformer200";

    public static <T> SingleTransformer<Result<T>, T> handleException() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>())
                .retryWhen(new RetryFunction());
    }

    private static class ErrorResumeFunction<T> implements Function<Throwable, SingleSource<? extends Result<T>>> {
        @Override
        public SingleSource<? extends Result<T>> apply(Throwable throwable) throws Exception {
            Log.d(TAG, "ErrorResumeFunction：错误收集");
            return Single.error(ExceptionHandler200.handleException(throwable));
        }
    }

    private static class ResponseFunction<T> implements Function<Result<T>, SingleSource<T>> {

        @Override
        public SingleSource<T> apply(Result<T> tResult) throws Exception {
            return dealResponseCode(tResult);
        }
    }

    private static class RetryFunction implements Function<Flowable<Throwable>, Publisher<?>> {

        /**
         * 重试次数
         */
        private int retryTime;

        @Override
        public Publisher<?> apply(Flowable<Throwable> thr) throws Exception {
            try {
                return thr.flatMap(new Function<Throwable, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Throwable throwable) throws Exception {
                        if (throwable instanceof TokenExpiredException) {
                            if (retryTime < 3) {
                                retryTime++;
                                return getToken();
                            }
                            return PublishProcessor.error(new ApiException(throwable));
                        }
                        return PublishProcessor.error(throwable);
                    }
                });
            } catch (Exception e) {
                // 统一归类为ApiException
                return PublishProcessor.error(new ApiException(e));
            }
        }
    }

    /**
     * 默认实现: 不启用刷新Token
     */
    private static Publisher<?> getToken() {
        // todo 实现 更新Token 逻辑
        final int code = ResponseCode.CERTIFICATE_INVALID;
        return PublishProcessor.error(new ApiException(code, "error:" + code));
//        return PublishProcessor.just("sss").compose(new RxUiScheduler<>())
//                .doOnNext(s -> Log.d("token_", "getToken()"));
    }

    /**
     * 对返回的数据进行分类处理
     * 服务器错误整理
     */
    private static <T> SingleSource<T> dealResponseCode(Result<T> tResult) throws Exception {
        int code = tResult.getCode();
        String message = tResult.getMessage();
        Single<T> single;
        LogUtil.e(TAG, "code:" + code + " message:" + message + " data:" + tResult.getData());
        switch (code) {
            case ResponseCode.SUCCESS:
                single = Single.just(tResult.getData());
                break;
            case ResponseCode.CERTIFICATE_INVALID:
                single = Single.error(new TokenExpiredException());
                break;
            default:
                single = Single.error(new ApiException(code, message));
                break;
        }
        return single;
    }
}
