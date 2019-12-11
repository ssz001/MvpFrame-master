package com.ssz.testarea.rx.combination;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * @author : zsp
 * time : 2019 10 2019/10/9 10:11
 */
public class Entry {

    /**
     * concat
     * 可以将多个观察者组合在一起，然后按照之前发送顺序发送事件。
     * 需要注意的是，concat() 最多只可以发送4个事件。
     * 串行发送
     */
    void concat(){
        Observable.concat(Observable.just(1, 2),
                Observable.just(3, 4),
                Observable.just(5, 6),
                Observable.just(7, 8))
                .subscribe(new Observer< Integer >() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
//                        Log.d(TAG, "================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //================onNext 1
        //================onNext 2
        //================onNext 3
        //================onNext 4
        //================onNext 5
        //================onNext 6
        //================onNext 7
        //================onNext 8
    }

    /**
     * concatArray
     * 与 concat() 作用一样，不过 concatArray() 可以发送多于 4 个被观察者。
     */
    void concatArray(){

    }


    /**
     *  merge
     * 和concat类似，但是是并行发送的
     * 如下：
     * merge（） 的话 Observable-1 和 Observable-2 会同时发送  --> 并行
     * concat（）的话，先发送Observable-1 再 发送Observable-2  --> 串行
     */
    void merge(){
        Observable.merge(
                // Observable-1
                Observable.interval(1, TimeUnit.SECONDS).map(new Function < Long, String > () {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        return "A" + aLong;
                    }
                }),
                // Observable-2
                Observable.interval(1, TimeUnit.SECONDS).map(new Function< Long, String >() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        return "B" + aLong;
                    }
                }))
                .subscribe(new Observer < String > () {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
//                        Log.d(TAG, "=====================onNext " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * concatArrayDelayError() & mergeArrayDelayError()
     * 在 concatArray() 和 mergeArray() 两个方法当中，如果其中有一个被观察者发送了一个 Error 事件，
     * 那么就会停止发送事件，如果你想 onError() 事件延迟到所有被观察者都发送完事件后再执行的话，
     * 就可以使用
     * concatArrayDelayError() 和 mergeArrayDelayError()
     */
    void concatArrayDelayError(){
        Observable.concatArrayDelayError(
                Observable.create(new ObservableOnSubscribe< Integer >() {
                    @Override
                    public void subscribe(ObservableEmitter< Integer > e) throws Exception {
                        e.onNext(1);
                        // 这里发生错误发送后本来会中断发送过程，但是用了concatArrayDelayError()后
                        //===================onNext 1
                        // 用concatArray()的话 这里就会 onError 中断发射，
                        //===================onNext 2
                        //===================onNext 3
                        //===================onNext 4
                        //===================onError
                        e.onError(new NumberFormatException());
                    }
                }), Observable.just(2, 3, 4))
                .subscribe(new Observer < Integer > () {
                    @Override
                    public void onSubscribe(Disposable d) {

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

                    }
                });
    }

    /**
     * zip
     * 会将多个被观察者合并，根据各个被观察者发送事件的顺序一个个结合起来，
     * 最终发送的事件数量会与源 Observable 中最少事件的数量一样。
     */
    void zip(){
        Observable.zip(Observable.intervalRange(1, 5, 1, 1, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                String s1 = "A" + aLong;
//                                Log.d(TAG, "===================A 发送的事件 " + s1);
                                return s1;
                            }}),
                Observable.intervalRange(1, 6, 1, 1, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                String s2 = "B" + aLong;
//                                Log.d(TAG, "===================B 发送的事件 " + s2);
                                return s2;
                            }
                        }),
                new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        String res = s + s2;
                        return res;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "===================onSubscribe ");
                    }

                    @Override
                    public void onNext(String s) {
//                        Log.d(TAG, "===================onNext " + s);
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
        // 05-22 09:10:39.952 5338-5338/com.example.rxjavademo D/chan: ===================onSubscribe
        //05-22 09:10:40.953 5338-5362/com.example.rxjavademo D/chan: ===================A 发送的事件 A1
        //05-22 09:10:40.953 5338-5363/com.example.rxjavademo D/chan: ===================B 发送的事件 B1
        //===================onNext A1B1
        //05-22 09:10:41.953 5338-5362/com.example.rxjavademo D/chan: ===================A 发送的事件 A2
        //05-22 09:10:41.954 5338-5363/com.example.rxjavademo D/chan: ===================B 发送的事件 B2
        //===================onNext A2B2
        //05-22 09:10:42.953 5338-5362/com.example.rxjavademo D/chan: ===================A 发送的事件 A3
        //05-22 09:10:42.953 5338-5363/com.example.rxjavademo D/chan: ===================B 发送的事件 B3
        //05-22 09:10:42.953 5338-5362/com.example.rxjavademo D/chan: ===================onNext A3B3
        //05-22 09:10:43.953 5338-5362/com.example.rxjavademo D/chan: ===================A 发送的事件 A4
        //05-22 09:10:43.953 5338-5363/com.example.rxjavademo D/chan: ===================B 发送的事件 B4
        //05-22 09:10:43.954 5338-5363/com.example.rxjavademo D/chan: ===================onNext A4B4
        //05-22 09:10:44.953 5338-5362/com.example.rxjavademo D/chan: ===================A 发送的事件 A5
        //05-22 09:10:44.953 5338-5363/com.example.rxjavademo D/chan: ===================B 发送的事件 B5
        //05-22 09:10:44.954 5338-5363/com.example.rxjavademo D/chan: ===================onNext A5B5
        //===================onComplete
    }

    /**
     * combineLatest() 的作用与 zip() 类似，但是 combineLatest() 发送事件的序列是与发送的时间线有关的，
     * 当 combineLatest() 中所有的 Observable 都发送了事件，只要其中有一个 Observable 发送事件，
     * 这个事件就会和其他 Observable 最近发送的事件结合起来发送，
     * 这样可能还是比较抽象，看看以下例子代码。
     */
    void combinetLatest(){
        Observable.combineLatest(
                Observable.intervalRange(1, 4, 1, 1, TimeUnit.SECONDS)
                        .map(new Function < Long, String > () {@Override
                        public String apply(Long aLong) throws Exception {
                            String s1 = "A" + aLong;
//                            Log.d(TAG, "===================A 发送的事件 " + s1);
                            return s1;
                        }
                        }),
                Observable.intervalRange(1, 5, 2, 2, TimeUnit.SECONDS)
                        .map(new Function < Long, String > () {@Override
                        public String apply(Long aLong) throws Exception {
                            String s2 = "B" + aLong;
//                            Log.d(TAG, "===================B 发送的事件 " + s2);
                            return s2;
                        }
                        }),
                new BiFunction < String, String, String > () {@Override
                public String apply(String s, String s2) throws Exception {
                    String res = s + s2;
                    return res;
                }
                })
                .subscribe(new Observer < String > () {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "===================onSubscribe ");
                    }

                    @Override
                    public void onNext(String s) {
//                        Log.d(TAG, "===================最终接收到的事件 " + s);
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
        // 11:41:20.859 15104-15104/? D/chan: ===================onSubscribe
        //05-22 11:41:21.859 15104-15128/com.example.rxjavademo D/chan: ===================A 发送的事件 A1
        //05-22 11:41:22.860 15104-15128/com.example.rxjavademo D/chan: ===================A 发送的事件 A2
        //05-22 11:41:22.861 15104-15129/com.example.rxjavademo D/chan: ===================B 发送的事件 B1
        //05-22 11:41:22.862 15104-15129/com.example.rxjavademo D/chan: ===================最终接收到的事件 A2B1
        //05-22 11:41:23.860 15104-15128/com.example.rxjavademo D/chan: ===================A 发送的事件 A3
        //===================最终接收到的事件 A3B1
        //05-22 11:41:24.860 15104-15128/com.example.rxjavademo D/chan: ===================A 发送的事件 A4
        //05-22 11:41:24.861 15104-15129/com.example.rxjavademo D/chan: ===================B 发送的事件 B2
        //05-22 11:41:24.861 15104-15128/com.example.rxjavademo D/chan: ===================最终接收到的事件 A4B1
        //05-22 11:41:24.861 15104-15129/com.example.rxjavademo D/chan: ===================最终接收到的事件 A4B2
        //05-22 11:41:26.860 15104-15129/com.example.rxjavademo D/chan: ===================B 发送的事件 B3
        //05-22 11:41:26.861 15104-15129/com.example.rxjavademo D/chan: ===================最终接收到的事件 A4B3
        //05-22 11:41:28.860 15104-15129/com.example.rxjavademo D/chan: ===================B 发送的事件 B4
        //05-22 11:41:28.861 15104-15129/com.example.rxjavademo D/chan: ===================最终接收到的事件 A4B4
        //05-22 11:41:30.860 15104-15129/com.example.rxjavademo D/chan: ===================B 发送的事件 B5
        //05-22 11:41:30.861 15104-15129/com.example.rxjavademo D/chan: ===================最终接收到的事件 A4B5
        //===================onComplete
    }


    void reduce(){
       Disposable d =  Observable.just(0, 1, 2, 3)
                .reduce(new BiFunction < Integer, Integer, Integer > () {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        int res = integer + integer2;
//                        Log.d(TAG, "====================integer " + integer);
//                        Log.d(TAG, "====================integer2 " + integer2);
//                        Log.d(TAG, "====================res " + res);
                        return res;
                    }
                })
                .subscribe(new Consumer< Integer >() {
                    @Override
                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "==================accept " + integer);
                    }
                });
       //05-22 14:21:46.042 17775-17775/? D/chan: ====================integer 0
        //====================integer2 1
        //====================res 1
        //====================integer 1
        //====================integer2 2
        //====================res 3
        //====================integer 3
        //====================integer2 3
        //====================res 6
        //==================accept 6
    }

    /**
     * collect
     * 将数据收集到数据结构当中。
     */
    void collect(){
        Disposable d = Observable.just(1, 2, 3, 4)
                .collect(new Callable<ArrayList<Integer>>() {
                             @Override
                             public ArrayList<Integer> call() throws Exception {
                                 return new ArrayList<>();
                             }
                         },
                        new BiConsumer<ArrayList<Integer>, Integer>() {
                            @Override
                            public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
                                integers.add(integer);
                            }
                        })
                .subscribe(new Consumer<ArrayList<Integer>>() {
                    @Override
                    public void accept(ArrayList<Integer> integers) throws Exception {
//                        Log.d(TAG, "===============accept " + integers);
                    }
                });
    }

    /**
     * startWith() & startWithArray()
     * public final Observable<T> startWith(T item)
     * public final Observable<T> startWithArray(T... items)
     * 在发送事件之前追加事件，startWith() 追加一个事件，startWithArray() 可以追加多个事件。追加的事件会先发出。
     */
    void startWith(){
      Disposable d =  Observable.just(5, 6, 7)
                .startWithArray(2, 3, 4)
                .startWith(1)
                .subscribe(new Consumer < Integer > () {
                    @Override
                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "================accept " + integer);
                    }
                });
        // accept - 1
        // accept - 2
        //    ...
        // accept - 7
    }

    /**
     * count
     * 返回被观察者发送事件的数量。
     */
    void count(){
       Disposable d = Observable.just(1, 2, 3)
                .count()
                .subscribe(new Consumer< Long >() {
                    @Override
                    public void accept(Long aLong) throws Exception {
//                        Log.d(TAG, "=======================aLong " + aLong);
                    }
                });
        // aLong = 3
    }

}
