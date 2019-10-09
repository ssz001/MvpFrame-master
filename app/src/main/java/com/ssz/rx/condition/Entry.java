package com.ssz.rx.condition;

import java.util.ArrayList;
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
 * time : 2019 10 2019/10/9 13:33
 */
public class Entry {

    /**
     * 判断事件序列是否全部满足某个事件，如果都满足则返回 true，反之则返回 false。
     */
    void all(){
        Disposable d = Observable.just(1, 2, 3, 4)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 5;
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
//                        Log.d(TAG, "==================aBoolean " + aBoolean);
                    }
                });
        //================== aBoolean true
    }


    /**
     * 可以设置条件，当某个数据满足条件时就会发送该数据，反之则不发送。
     */
    void takeWhile() {
        Disposable d = Observable.just(1, 2, 3, 4)
                .takeWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 3;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "========================integer " + integer);
                    }
                });
        //========================integer 1
        //========================integer 2
    }

    /**
     * 可以设置条件，当某个数据满足条件时不发送该数据，反之则发送。
     */
    void skipWhile() {
        Disposable d = Observable.just(1, 2, 3, 4)
                .skipWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 3;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "========================integer " + integer);
                    }
                });
        // 3
        // 4
    }

    /**
     * takeUntil
     *
     * 可以设置条件，当事件满足此条件时，下一次(本次还是会发送的)的事件就不会被发送了。
     */
    void takeUntil() {
        Disposable d = Observable.just(1, 2, 3, 4, 5, 6)
                .takeUntil(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 3;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "========================integer " + integer);
                    }
                });
        //========================integer 1
        //========================integer 2
        //========================integer 3
        //========================integer 4
    }

    /**
     * 当 skipUntil() 中的 Observable 发送事件了，原来的 Observable 才会发送事件给观察者。
     * 从结果可以看出，skipUntil() 里的 Observable 并不会发送事件给观察者。
     */
    void skipUntil() {
        Observable.intervalRange(1, 5, 0, 1, TimeUnit.SECONDS)
                .skipUntil(Observable.intervalRange(6, 5, 3, 1, TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "========================onSubscribe ");
                    }

                    @Override
                    public void onNext(Long along) {
//                        Log.d(TAG, "========================onNext " + along);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "========================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "========================onComplete ");
                    }
                });
        //========================onSubscribe
        //========================onNext 4
        //========================onNext 5
        //========================onComplete
    }

    /**
     * 判断两个 Observable 发送的事件是否相同。
     */
   void sequenceEqual(){
       Disposable d = Observable.sequenceEqual(
               Observable.just(1, 2, 3), Observable.just(1, 2, 3)
       )
               .subscribe(new Consumer<Boolean>() {
                   @Override
                   public void accept(Boolean aBoolean) throws Exception {
//                       Log.d(TAG, "========================onNext " + aBoolean);
                   }
               });
       //  true
   }


   /**
    * 判断事件序列中是否含有某个元素，如果有则返回 true，如果没有则返回 false。
    */
   void contains(){
      Disposable d = Observable.just(1, 2, 3)
               .contains(3)
               .subscribe(new Consumer < Boolean > () {
                   @Override
                   public void accept(Boolean aBoolean) throws Exception {
//                       Log.d(TAG, "========================onNext " + aBoolean);
                   }
               });
      //  true
   }

   /**
    * 判断事件序列是否为空。
    */
   void isEmpty() {
       Disposable d = Observable.create(new ObservableOnSubscribe<Integer>() {

           @Override
           public void subscribe(ObservableEmitter<Integer> e) throws Exception {
               e.onComplete();
           }
       })
               .isEmpty()
               .subscribe(new Consumer<Boolean>() {
                   @Override
                   public void accept(Boolean aBoolean) throws Exception {
//                       Log.d(TAG, "========================onNext " + aBoolean);
                   }
               });
       // true
   }

   /**
    * amb() 要传入一个 Observable 集合，但是只会发送最先发送事件的 Observable 中的事件，其余 Observable 将会被丢弃。
    */
   void amb() {
       ArrayList<Observable<Long>> list = new ArrayList<>();

       list.add(Observable.intervalRange(1, 5, 2, 1, TimeUnit.SECONDS));
       list.add(Observable.intervalRange(6, 5, 0, 1, TimeUnit.SECONDS));

       Disposable d = Observable.amb(list)
               .subscribe(new Consumer<Long>() {
                   @Override
                   public void accept(Long aLong) throws Exception {
//                       Log.d(TAG, "========================aLong " + aLong);
                   }
               });
       //05-26 10:21:29.580 17185-17219/com.example.rxjavademo D/chan: ========================aLong 6
       //05-26 10:21:30.580 17185-17219/com.example.rxjavademo D/chan: ========================aLong 7
       //05-26 10:21:31.579 17185-17219/com.example.rxjavademo D/chan: ========================aLong 8
       //05-26 10:21:32.579 17185-17219/com.example.rxjavademo D/chan: ========================aLong 9
       //05-26 10:21:33.579 17185-17219/com.example.rxjavademo D/chan: ========================aLong 10
   }

   /**
    * 如果观察者只发送一个 onComplete() 事件，则可以利用这个方法发送一个值。
    */
   void defaultIfEmpty() {
       Disposable d = Observable.create(new ObservableOnSubscribe<Integer>() {
           @Override
           public void subscribe(ObservableEmitter<Integer> e) throws Exception {
               e.onComplete();
           }
       })
               .defaultIfEmpty(666)
               .subscribe(new Consumer<Integer>() {
                   @Override
                   public void accept(Integer integer) throws Exception {
//                       Log.d(TAG, "========================onNext " + integer);
                   }
               });
       // = onNext 666
   }
}
