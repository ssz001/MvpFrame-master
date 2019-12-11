package com.ssz.testarea.rx.create;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * @author : zsp
 * time : 2019 10 2019/10/8 10:47
 */
public class Entry {

    /**
     *   create
     *   public static <T> Observable<T> create(ObservableOnSubscribe<T> source) {}
     */
    void create(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 发射器
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onComplete();
            }
        }).subscribe();
    }

    /**
     * just
     *    public static <T> Observable<T> just(T item) {}
     */
    void just(){
        // onNext - 1
        // onComplete();
        Observable.just("1").subscribe();
        // onNext - 1
        // onNext - 2
        // onNext - 3
        // onComplete();
        Observable.just("1","2","3").subscribe();
    }

    /**
     * fromArray
     *  public static <T> Observable<T> fromArray(T... items) {
     */
   void fromArray(){
       // onNext - 1
       // onNext - 2
       // OnNext - 10
       Observable.fromArray("1","2","10").subscribe();
   }

   /**
    * fromCallAble
    * public static <T> Observable<T> fromCallable(Callable<? extends T> supplier) {}
    *
    * 是将 java.util.concurrent 中的 Callable ｛ V call() throws Exception;｝
    * 的返回值 V 生成Observabel 对象，不是将Callble 发送出去
    */
   void fromCallAble(){
     Disposable d = Observable.fromCallable(new Callable<String>() {
           @Override
           public String call() throws Exception {
               return "zhongguo";
           }
       }).subscribe(new Consumer<String>() {
           @Override
           public void accept(String s) throws Exception {

           }
       });
   }

   /**
    * fromFuture
    * java.util.concurrent 中的 FutureTask
    */
   void fromFuture(){
       FutureTask<String> futureTask = new FutureTask<>(new Callable<String>(){
           @Override
           public String call() throws Exception {
               return "返回结果";
           }
       });

       Observable.fromFuture(futureTask)
               .doOnSubscribe(new Consumer<Disposable>() {
                   @Override
                   public void accept(Disposable disposable) throws Exception {
                       // 这里不能用 get ？
                       futureTask.run();
                   }
               }).subscribe();
   }

   /**
    * formIterable
    * public static <T> Observable<T> fromIterable(Iterable<? extends T> source)
    */
   void fromIterable(){
       List<String> strList = new ArrayList<>();
       strList.add("1");
       strList.add("2");
       strList.add("3");
       Observable.fromIterable(strList).subscribe();
       // onNext - 1;
       // onNext - 2;
       // onNext - 3;
   }

   /**
    * defer
    * 直到被观察者被订阅后才会创建被观察者
    * public static <T> Observable<T> defer(Callable<? extends ObservableSource<? extends T>> supplier) {}
    */
   Integer i = 100;
   void defer(){
       Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
           @Override
           public ObservableSource<? extends Integer> call() throws Exception {
               // observable.subscribe(observer) 被调用，这个方法才会被调用
               return Observable.just(i);
           }
       });

       i = 200;

       observable.subscribe();
       // 200

       i = 300;

       observable.subscribe();
       //300
   }

   /**
    * 当到指定时间后就会发送一个 0L 的值给观察者。
    *  public static Observable<Long> timer(long delay, TimeUnit unit) {}
    *  public static Observable<Long> timer(long delay, TimeUnit unit, Scheduler scheduler) {}
    */
   void timer(){
     Observable.timer(2,TimeUnit.SECONDS).subscribe();
     // onNext - 0 ;
   }

   /**
    * 每隔一段时间就会发送一个事件，这个事件是从0开始，不断增1的数字。
    * public static Observable<Long> interval(long period, TimeUnit unit)
    * public static Observable<Long> interval(long initialDelay, long period, TimeUnit unit)
    */
   void interval(){
       // 第一个参数 initialDelay 订阅开始后，开始间隔发送的延迟时间；
       Observable.interval(10,4,TimeUnit.SECONDS).subscribe();

       Observable.interval(4,TimeUnit.SECONDS).subscribe();
       // onNext - 0;
       // onNext - 1;
       // onNext - 2;
       // onNext - ...;
   }

   /**
    * intervalRange
    * 可以指定发送事件的开始值和数量，其他与 interval() 的功能一样。
    * public static Observable<Long> intervalRange(long start, long count, long initialDelay, long period, TimeUnit unit)
    * public static Observable<Long> intervalRange(long start, long count, long initialDelay, long period, TimeUnit unit, Scheduler scheduler)
    */
   void intervalRange(){
      Observable.intervalRange(0,10,5,4,TimeUnit.SECONDS).subscribe();
       // onNext - 0;
       // onNext - 1;
       //     ...
       // onNext - 10;
   }

   /**
    * range
    * public static Observable<Integer> range(final int start, final int count){}
    * 同时发送一定范围的事件序列。
    */
   void range(){
      Observable.range(2,5).subscribe();
       // onNext - 2
       // onNext - 3
       //      ...
       // onNext - 6
   }

   /**
    * rangLong
    * public static Observable<Long> rangeLong(long start, long count){}
    * 和range类似，只不过是long的
    */
   void rangeLong(){
      Observable.rangeLong(2,5).subscribe();
       // onNext - 2
       // onNext - 3
       //      ...
       // onNext - 6
   }

   /**
    * 其它
    *
    * empty() ： 直接发送 onComplete() 事件
    * never()：不发送任何事件                    ---  这三个方法都会调用 onSubscribe()
    * error()：发送 onError() 事件
    */
   void other(){
       Observable.empty().subscribe();
       Observable.error(new NullPointerException()).subscribe();
       Observable.never().subscribe();
   }
}
