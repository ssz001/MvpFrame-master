package com.ssz.frame.utils;

import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author : zsp
 * time : 2019 10 2019/10/10 15:55
 */
public class RxViewUtils {

    private RxViewUtils(){
        // no instance
    }

    public static Observable<View> clickView(@NonNull View view) {
        checkNoNull(view);
        return Observable.create(new ViewClickOnSubscribe(view));
    }

    /**
     * 查空
     */
    private static <T> void checkNoNull(T value) {
        if (value == null) {
            throw new NullPointerException("RxUtils clickView(@NonNull View view) 传入了一个 null 值");
        }
    }

    private static class ViewClickOnSubscribe implements ObservableOnSubscribe<View> {
        private View view;

        ViewClickOnSubscribe(View view) {
            this.view = view;
        }
        @Override
        public void subscribe(ObservableEmitter<View> emitter) throws Exception {
            View.OnClickListener onClickListener = v -> {
                //订阅没取消
                if (!emitter.isDisposed()) {
                    //发送消息
                    emitter.onNext(view);
                }
            };
            view.setOnClickListener(onClickListener);
        }
    }
}
