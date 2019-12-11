package com.ssz.testarea.rx.filter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * @author : zsp
 * time : 2019 10 2019/10/9 13:16
 */
public class Entry {

    int i = 0;

    /**
     * 通过一定逻辑来过滤被观察者发送的事件，如果返回 true 则会发送事件，否则不会发送。
     */
    void filter(){
        Observable.just(1, 2, 3)
                .filter(new Predicate< Integer >() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 2;
                    }
                })
                .subscribe(new Observer< Integer >() {
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
        //==================onComplete
    }

    /**
     * 可以过滤不符合该类型事件,留下的是ofType里的参数类型
     */
    void ofType(){
        Observable.just(1, 2, 3, "chan", "zhide")
                .ofType(Integer.class)
                .subscribe(new Observer < Integer > () {
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
        //==================onComplete
    }

    /**
     * 跳过正序某些事件，count 代表跳过事件的数量
     * skipLast() 作用也是跳过某些事件，不过它是用来跳过正序的后面的事件，这里就不再讲解了。
     */
    void skip(){
        Observable.just(1, 2, 3)
                .skip(2)
                .subscribe(new Observer < Integer > () {
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
        //==================onNext 3
        //==================onComplete
    }

    /**
     * 过滤事件序列中的重复事件。
     */
    void distinct(){
        Observable.just(1, 2, 3, 3, 2, 1)
                .distinct()
                .subscribe(new Observer < Integer > () {
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
        //==================onComplete
    }

    /**
     * 过滤掉连续重复的事件
     */
    void distinctUntilChanged(){
        Observable.just(1, 2, 3, 3, 2, 1)
                .distinctUntilChanged()
                .subscribe(new Observer < Integer > () {
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
        //==================onNext 2
        //==================onNext 1
        //==================onComplete
    }

    /**
     * 控制观察者接收的事件的数量。
     * takeLast() 的作用就是控制观察者只能接受事件序列的后面几件事情
     */
    void take(){
        Observable.just(1, 2, 3, 4, 5)
                .take(3)
                .subscribe(new Observer < Integer > () {
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
        //==================onComplete
    }

    /**
     * debounce
     * 如果两件事件发送的时间间隔小于设定的时间间隔则前一件事件就不会发送给观察者
     * throttleWithTimeout() 与此方法的作用一样
     */
    void debounce() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Thread.sleep(900);
                e.onNext(2);
            }
        })
                .debounce(1, TimeUnit.SECONDS)
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
//                        Log.d(TAG, "===================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "===================onComplete ");
                    }
                });
    }

    //===================onSubscribe
    //===================onNext 2

    //可以看到事件1并没有发送出去，现在将间隔时间改为1000，看看打印结果：

    //===================onSubscribe
    //===================onNext 1
    //===================onNext 2

    /**
     * firstElement() && lastElement()
     * firstElement() 取事件序列的第一个元素，lastElement() 取事件序列的最后一个元素。
     */
    void firstElement() {
      Disposable d =  Observable.just(1, 2, 3, 4)
                .firstElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "====================firstElement " + integer);
                    }
                });

        // 1

        Disposable d2 = Observable.just(1, 2, 3, 4)
                .lastElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "====================lastElement " + integer);
                    }
                });
        // 4
    }

    /**
     * elementAt() & elementAtOrError()
     * elementAt() 可以指定取出事件序列中事件，但是输入的 index 超出事件序列的总数的话就不会出现任何结果。
     * 这种情况下，你想发出异常信息的话就用 elementAtOrError() 。
     */
    void elementAt() {
     Disposable d = Observable.just(1, 2, 3, 4)
                .elementAt(0)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "====================accept " + integer);
                    }
                });
     //   1
    }
}
