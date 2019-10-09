package com.ssz.rx.func;

import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author : zsp
 * time : 2019 10 2019/10/9 10:58
 */
public class Entry {

    /**
     * 延迟一段事件发送事件。
     */
    void delay(){
        Observable.just(1, 2, 3)
                .delay(2, TimeUnit.SECONDS)
                .subscribe(new Observer< Integer >() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "=======================onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
//                        Log.d(TAG, "=======================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //43 : =======================onSubscribe
        //45 : =======================onNext 1
        //=======================onNext 2
        //=======================onNext 3
    }

    /**
     * Observable 每发送一件事件(onNext onError,onComplete)之前都会先回调这个方法。
     */
    void doOnEach(){
        Observable.create(new ObservableOnSubscribe< Integer >() {
            @Override
            public void subscribe(ObservableEmitter< Integer > e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                //      e.onError(new NumberFormatException());
                e.onComplete();
            }
        })
                .doOnEach(new Consumer<Notification< Integer >>() {
                    @Override
                    public void accept(Notification < Integer > integerNotification) throws Exception {
//                        Log.d(TAG, "==================doOnEach " + integerNotification.getValue());
                    }
                })
                .subscribe(new Observer < Integer > () {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "==================onSubscribe ");
                    }

                    @Override
                    public void onNext(Integer integer) {
//                        Log.d(TAG, "==================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "==================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "==================onComplete ");
                    }
                });
        //05-23 09:07:05.547 19867-19867/? D/chan: ==================onSubscribe
        //==================doOnEach 1
        //==================onNext 1
        //==================doOnEach 2
        //==================onNext 2
        //==================doOnEach 3
        //==================onNext 3
        //==================doOnEach null
        //==================onComplete
    }

    /**
     * Observable 每发送 onNext() 之前都会先回调这个方法。
     */
    void doOnNext(){
        Observable.create(new ObservableOnSubscribe < Integer > () {
            @Override
            public void subscribe(ObservableEmitter < Integer > e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        })
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "==================doOnNext " + integer);
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "==================onSubscribe ");
                    }

                    @Override
                    public void onNext(Integer integer) {
//                        Log.d(TAG, "==================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "==================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "==================onComplete ");
                    }
                });
        //==================onSubscribe
        //==================doOnNext 1
        //==================onNext 1
        //==================doOnNext 2
        //==================onNext 2
        //==================doOnNext 3
        //==================onNext 3
        //==================onComplete
    }

    /**
     * doAfterNext()
     * 顾名思义
     * Observable 每发送 onNext() 之后都会回调这个方法。
     */
    void doAfterNext(){

    }

    /**
     * Observable 每发送 onComplete() 之前都会回调这个方法。
     */
    void doOnComplete() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
//                        Log.d(TAG, "==================doOnComplete ");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "==================onSubscribe ");
                    }

                    @Override
                    public void onNext(Integer integer) {
//                        Log.d(TAG, "==================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "==================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "==================onComplete ");
                    }
                });
        //==================onSubscribe
        //==================onNext 1
        //==================onNext 2
        //==================onNext 3
        //==================doOnComplete
        //==================onComplete
    }

    /**
     * Observable 每发送 onError() 之前都会回调这个方法。
     */
    void doOnError(){}

    /**
     * Observable 每发送 onSubscribe() 之前都会回调这个方法。
     */
    void doOnSubscribe(){

    }

    /**
     * doOnDispose
     * 当调用 Disposable 的 dispose() 之后回调该方法。
     */
    void doOnDispose(){
        Observable.create(new ObservableOnSubscribe < Integer > () {
            @Override
            public void subscribe(ObservableEmitter < Integer > e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        })
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
//                        Log.d(TAG, "==================doOnDispose ");
                    }
                })
                .subscribe(new Observer < Integer > () {
                    private Disposable d;

                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "==================onSubscribe ");
                        this.d = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
//                        Log.d(TAG, "==================onNext " + integer);
                        d.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "==================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "==================onComplete ");
                    }
                });
    }

    /**
     * 在回调 onSubscribe 之前回调该方法的第一个参数的回调方法，可以使用该回调方法决定是否取消订阅。
     */
    void doOnLifecycle(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        })
                .doOnLifecycle(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
//                        Log.d(TAG, "==================doOnLifecycle accept");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
//                        Log.d(TAG, "==================doOnLifecycle Action");
                    }
                })
                .doOnDispose(new Action() {
                            @Override
                            public void run() throws Exception {
//                                Log.d(TAG, "==================doOnDispose Action");
                            }
                        })
                .subscribe(new Observer<Integer>() {
                    private Disposable d;
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "==================onSubscribe ");
                        this.d = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
//                        Log.d(TAG, "==================onNext " + integer);
                        d.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "==================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "==================onComplete ");
                    }

                });
        // 05-23 10:20:36.345 23922-23922/? D/chan: ==================doOnLifecycle accept
        //==================onSubscribe
        //==================onNext 1
        //==================doOnDispose Action
        //==================doOnLifecycle Action

        //可以看到当在 onNext() 方法进行取消订阅操作后，doOnDispose() 和 doOnLifecycle() 都会被回调。
        //如果使用 doOnLifecycle 进行取消订阅，来看看打印结果：
        //05-23 10:32:20.014 24652-24652/com.example.rxjavademo D/chan: ==================doOnLifecycle accept
        //==================onSubscribe

    }

    /**
     * doOnTerminate() & doAfterTerminate()
     * doOnTerminate 是在 onError 或者 onComplete 发送之前回调，
     * 而 doAfterTerminate 则是 onError 或者 onComplete 发送之后回调。
     */
    void doOnTerminate(){

    }

    /**
     *    在所有事件发送完毕之后回调该方法。
     */
    void doFinally(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
//                        Log.d(TAG, "==================doFinally ");
                    }
                })
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
//                        Log.d(TAG, "==================doOnDispose ");
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
//                        Log.d(TAG, "==================doAfterTerminate ");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    private Disposable d;
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "==================onSubscribe ");
                        this.d = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
//                        Log.d(TAG, "==================onNext " + integer);
                        d.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "==================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "==================onComplete ");
                    }
                });
    }

    /**
     * 当接受到一个 onError() 事件之后回调，返回的值会回调 onNext() 方法，并正常结束该事件序列。
     */
    void onErrorReturn(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new NullPointerException());
            }
        })
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Exception {
//                        Log.d(TAG, "==================onErrorReturn " + throwable);
                        return 404;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "==================onSubscribe ");
                    }

                    @Override
                    public void onNext(Integer integer) {
//                        Log.d(TAG, "==================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "==================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "==================onComplete ");
                    }
                });
        //==================onSubscribe
        //==================onNext 1
        //==================onNext 2
        //==================onNext 3
        //==================onErrorReturn java.lang.NullPointerException
        //==================onNext 404   ---  发生异常后发送给onNext()
        //==================onComplete
    }

    /**
     * 当接收到 onError() 事件时，返回一个新的 Observable，并正常结束事件序列。
     */
    void onErrorResumeNext(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new NullPointerException());
            }
        })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
//                        Log.d(TAG, "==================onErrorResumeNext " + throwable);
                        return Observable.just(4, 5, 6);
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "==================onSubscribe ");
                    }

                    @Override
                    public void onNext(Integer integer) {
//                        Log.d(TAG, "==================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "==================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "==================onComplete ");
                    }
                });
        //==================onSubscribe
        //==================onNext 1
        //==================onNext 2
        //==================onNext 3
        //==================onErrorResumeNext java.lang.NullPointerException
        //==================onNext 4
        //==================onNext 5
        //==================onNext 6
        //==================onComplete
    }

    /**
     *  onExceptionResumeNext
     * 与 onErrorResumeNext() 作用基本一致，但是这个方法只能捕捉 Exception。
     * Throwable -> error
     *           -> Exception
     */
    void onExceptionResumeNext(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new Error("404"));
            }
        })
                .onExceptionResumeNext(new Observable<Integer>() {
                    @Override
                    protected void subscribeActual(Observer<? super Integer> observer) {
                        observer.onNext(333);
                        observer.onComplete();
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "==================onSubscribe ");
                    }

                    @Override
                    public void onNext(Integer integer) {
//                        Log.d(TAG, "==================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "==================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "==================onComplete ");
                    }
                });
        //==================onSubscribe
        //==================onNext 1
        //==================onNext 2
        //==================onNext 3
              // 没有捕获Error
        //==================onError
    }

    /**
     * retry
     * 如果出现错误事件，则会重新发送-所有-事件序列。times 是代表重新发的次数。
     */
    void retry(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new Exception("404"));
            }
        })
                .retry(2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "==================onSubscribe ");
                    }

                    @Override
                    public void onNext(Integer integer) {
//                        Log.d(TAG, "==================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "==================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "==================onComplete ");
                    }
                });
        //==================onSubscribe
        //==================onNext 1
        //==================onNext 2
        //==================onNext 3
               // 第一次出错
        //==================onNext 1
        //==================onNext 2
        //==================onNext 3
               // 第二次出错
        //==================onNext 1
        //==================onNext 2
        //==================onNext 3
        //==================onError
    }

    /**
     * 出现错误事件之后，可以通过此方法判断是否继续发送事件。
     */
    int i = 0;
    void retryUntil() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new Exception("404"));
            }
        })
                .retryUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() throws Exception {
                        if (i == 6) {
                            return true;
                        }
                        return false;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "==================onSubscribe ");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        i += integer;
//                        Log.d(TAG, "==================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "==================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "==================onComplete ");
                    }
                });
        //==================onSubscribe
        //==================onNext 1
        //==================onNext 2
        //==================onNext 3
        //==================onError
    }

    /**
     * 出现错误事件之后，可以通过此方法判断是否继续发送事件。
     */
    void retryWhen() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("chan");
                e.onNext("ze");
                e.onNext("de");
                e.onError(new Exception("404"));
                e.onNext("haha");
            }
        })
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                if (!throwable.toString().equals("java.lang.Exception: 404")) {
                                    return Observable.just("可以忽略的异常");
                                } else {
                                    return Observable.error(new Throwable("终止啦"));
                                }
                            }
                        });
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "==================onSubscribe ");
                    }

                    @Override
                    public void onNext(String s) {
//                        Log.d(TAG, "==================onNext " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "==================onError " + e.toString());
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "==================onComplete ");
                    }
                });
        //==================onSubscribe
        //==================onNext chan
        //==================onNext ze
        //==================onNext de
        //==================onError java.lang.Throwable: 终止啦

        // 将 onError(new Exception("404")) 改为 onError(new Exception("303")) 看看打印结果：

        //==================onNext chan
        //05-24 09:54:08.653 29694-29694/? D/chan: ==================onNext ze
        //==================onNext de
        //==================onNext chan
        //==================onNext ze
        //==================onNext de
        //==================onNext chan
        //==================onNext ze
        //==================onNext de
        //==================onNext chan
        //==================onNext ze
        //==================onNext de
        //==================onNext chan
        //==================onNext ze
        //==================onNext de
        //==================onNext chan
        //  ...   不断重复发送
    }

    /**
     * 重复发送被观察者的事件，times 为发送次数。
     */
    void repeat() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        })
                .repeat(2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "===================onSubscribe ");
                    }

                    @Override
                    public void onNext(Integer integer) {
//                        Log.d(TAG, "===================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "===================onComplete ");
                    }
                });
        //===================onSubscribe
        //===================onNext 1
        //===================onNext 2
        //===================onNext 3
        //===================onNext 1
        //===================onNext 2
        //===================onNext 3
        //===================onComplete
    }

    /**
     * 这个方法可以会返回一个新的被观察者设定一定逻辑来决定是否重复发送事件。
     *
     * 这里分三种情况，如果新的被观察者返回 onComplete 或者 onError 事件，则旧的被观察者不会继续发送事件。
     * 如果被观察者返回其他事件，则会重复发送事件。
     */
    void repeatWhen(){
        Observable.create(new ObservableOnSubscribe < Integer > () {
            @Override
            public void subscribe(ObservableEmitter < Integer > e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        })
                .repeatWhen(new Function < Observable < Object > , ObservableSource <? >> () {
                    @Override
                    public ObservableSource <? > apply(Observable < Object > objectObservable) throws Exception {
                        return Observable.empty();
                        //  return Observable.error(new Exception("404"));
                        //  return Observable.just(4); null;
                    }
                })
                .subscribe(new Observer < Integer > () {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "===================onSubscribe ");
                    }

                    @Override
                    public void onNext(Integer integer) {
//                        Log.d(TAG, "===================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "===================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "===================onComplete ");
                    }
                });
        //===================onSubscribe
        //===================onComplete

        //下面直接看看发送 onError 事件和其他事件的打印结果。
        //发送 onError 打印结果：

        //===================onSubscribe
        //===================onError

        // 发送其它事件
        //===================onSubscribe
        //===================onNext 1
        //===================onNext 2
        //===================onNext 3
        //===================onComplete
    }

    /**
     * 指定被观察者的线程，要注意的时，如果多次调用此方法，只有第一次有效。
     */
    void subscribeOn(){

    }

    /**
     * 指定观察者的线程，每指定一次就会生效一次。
     */
    void observeOn(){

    }
}
