package com.ssz.rx.transform;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : zsp
 * time : 2019 10 2019/10/8 13:36
 */
public class Entry {

    /**
     * map
     * 数据类型转换
     */
    void map(){
      Disposable d = Observable.just(1,2)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer+"";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });
    }

    /**
     * flatMap
     * 因为存在类似于 发送的对象中成员变量是一个对象的集合的情况，为了在订阅处- 直接 -得到深层的对象，
     * 就用flatMap 将集合中的对象都转换为一个Observable；
     * 注意：   不保证发送和接收的数据的顺序是一致的。
     */
    void flatMap(){
       List<Person> personList = new ArrayList<>();
       // 目标获取action 的 每个 String
       Observable.fromIterable(personList)
       .flatMap(new Function<Person, Observable<String>>() {
           @Override
           public Observable<String> apply(Person person) throws Exception {
               return Observable.fromIterable(person.action);
           }
       }).subscribe();
    }

    static class Person{
        String name;
        int age;
        List<String> action = new ArrayList<>();
    }

    /**
     * concatMap 和 flatMap 类似，唯一区别在于 concatMap 发送和接收顺序是一致的的
     */
    void concatMap(){
        List<Person> personList = new ArrayList<>();
        Observable.fromIterable(personList)
                .concatMap(new Function<Person, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Person person) throws Exception {
                        return Observable.fromIterable(person.action);
                    }
                })
                .subscribe();
    }

    /**
     * buffer
     * public final Observable<List<T>> buffer(int count, int skip)
     * 从需要发送的事件当中获取一定数量的事件，并将这些事件放到缓冲区当中一并发出。
     *
     */
    void buffer(){
        Observable.just(1, 2, 3, 4, 5)
                // count: 缓冲区大小;
                // skip: 缓冲区满了后下一个缓冲区
                // int index = 1 ; 1 -> {1,2},
                // 因为skip == 1 所以下个buffer 起始为 index+1 = 2  index = 2 --> {2,3}
                //
                .buffer(2, 1)
                .subscribe(new Observer< List < Integer >>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List < Integer > integers) {
//                        Log.d(TAG, "================缓冲区大小： " + integers.size());
                        for (Integer i: integers) {
//                            Log.d(TAG, "================元素： " + i);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //================元素： 1
        //================元素： 2
        //================缓冲区大小： 2
        //================元素： 2
        //================元素： 3
        //================缓冲区大小： 2
        //================元素： 3
        //================元素： 4
        //================缓冲区大小： 2
        //================元素： 4
        //================元素： 5
        //================缓冲区大小： 1
        //================元素： 5
    }

    /**
     * groupBy
     * public final <K> Observable<GroupedObservable<K, T>> groupBy(Function<? super T, ? extends K> keySelector)
     * 将发送的数据进行分组，每个分组都会返回一个被观察者。
     * 观察获取的结果：GroupedObservable<Integer, Integer> .getKey 就是组别，value 就是该组的成员
     */
    void groupBy() {
        Observable.just(5, 2, 3, 4, 1, 6, 8, 9, 7, 10)
                .groupBy(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        // 分组规则：满足结果相同的为同一组
                        // 0  :  3,6,9
                        // 1  :  4,1,7,10
                        // 2  :  5,2,8
                        return integer % 3;
                    }
                })
                .subscribe(new Observer<GroupedObservable<Integer, Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "====================onSubscribe ");
                    }

                    @Override
                    public void onNext(GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
//                        Log.d(TAG, "====================onNext ");
                        integerIntegerGroupedObservable.subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {
//                                Log.d(TAG, "====================GroupedObservable onSubscribe ");
                            }

                            @Override
                            public void onNext(Integer integer) {
//                                Log.d(TAG, "====================GroupedObservable onNext  groupName: " + integerIntegerGroupedObservable.getKey() + " value: " + integer);
                            }

                            @Override
                            public void onError(Throwable e) {
//                                Log.d(TAG, "====================GroupedObservable onError ");
                            }

                            @Override
                            public void onComplete() {
//                                Log.d(TAG, "====================GroupedObservable onComplete ");
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "====================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "====================onComplete ");
                    }
                });
        //====================onSubscribe
        //====================onNext
        //====================GroupedObservable onSubscribe   ====================  GroupedObservable onNext  groupName: 2 value: 5
        //====================GroupedObservable onNext  groupName: 2 value: 2
        //====================onNext
        //====================GroupedObservable onSubscribe
        //====================GroupedObservable onNext  groupName: 0 value: 3
        //05-26 14:38:02.064 21451-21451/com.example.rxjavademo D/chan: ====================onNext
        //====================GroupedObservable onSubscribe
        //====================GroupedObservable onNext  groupName: 1 value: 4
        //====================GroupedObservable onNext  groupName: 1 value: 1
        //====================GroupedObservable onNext  groupName: 0 value: 6
        //====================GroupedObservable onNext  groupName: 2 value: 8
        //====================GroupedObservable onNext  groupName: 0 value: 9
        //====================GroupedObservable onNext  groupName: 1 value: 7
        //====================GroupedObservable onNext  groupName: 1 value: 10
        //05-26 14:38:02.065 21451-21451/com.example.rxjavademo D/chan: ====================GroupedObservable onComplete
        //====================GroupedObservable onComplete
        //====================GroupedObservable onComplete
        //====================onComplete

    }

    /**
     * scan
     * public final Observable<T> scan(BiFunction<T, T, T> accumulator)
     * 将数据以一定的逻辑聚合起来
     */
    void scan() {
        Disposable d = Observable.just(1, 2, 3, 4, 5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
//                        Log.d(TAG, "====================apply ");
//                        Log.d(TAG, "====================integer " + integer);
//                        Log.d(TAG, "====================integer2 " + integer2);

                        //                        1 - 2
                        //                        3 - 3
                        //                        6 - 4     -->  首项为前两项逻辑操作结果
                        //                        10 - 5

                        // 定义逻辑这里为加
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "====================accept " + integer);
                    }
                });
//        accept : 1  -- 第一次不会调用apply ，直接发射 为 1
//        accept : 3
//        accept : 6
//        accept : 10
//        accept : 15
    }

    /**
     * window
     * 发送指定数量的事件时，就将这些事件分为一组。window 中的 count 的参数就是代表指定的数量，
     * 例如将 count 指定为2，那么每发2个数据就会将这2个数据分成一组。
     */
    void window() {
        Observable.just(1, 2, 3, 4, 5)
                .window(2)
                .subscribe(new Observer<Observable<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "=====================onSubscribe ");
                    }

                    @Override
                    public void onNext(Observable<Integer> integerObservable) {
                        // window 分组后，这里的integerObservable 发送完两个后就是再次发送得就是新的源了
                        integerObservable.subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {
//                                Log.d(TAG, "=====================integerObservable onSubscribe ");
                            }

                            @Override
                            public void onNext(Integer integer) {
//                                Log.d(TAG, "=====================integerObservable onNext " + integer);
                            }

                            @Override
                            public void onError(Throwable e) {
//                                Log.d(TAG, "=====================integerObservable onError ");
                            }

                            @Override
                            public void onComplete() {
//                                Log.d(TAG, "=====================integerObservable onComplete ");
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "=====================onError ");
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "=====================onComplete ");
                    }
                });
        //=====================onSubscribe
        //=====================integerObservable onSubscribe
        //=====================integerObservable onNext 1
        //=====================integerObservable onNext 2
        //=====================integerObservable onComplete

        //=====================integerObservable onSubscribe
        //=====================integerObservable onNext 3
        //=====================integerObservable onNext 4
        //=====================integerObservable onComplete

        //=====================integerObservable onSubscribe
        //=====================integerObservable onNext 5
        //=====================integerObservable onComplete
        //=====================onComplete
    }

    // 自

    /**
     *  compose()
     *  将好集合操作符合在一起
     *  compose( ObservableTransformer<? super T, ? extends R> composer )
     */
    void compose(){
        Observable.just(1,2,3,4)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer + "";
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe();

        Observable.just(1,2,3,4)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer + "";
                    }
                })
                .compose(new Transformer())
                .subscribe();

        Observable.just(1,2,3,4)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer + "";
                    }
                })
                .compose(Transformer.handle())
                .subscribe();

      Disposable d = Observable.just(1,2,3,4)
//                .compose(new ObservableTransformer<Integer, Object>() {
//                    @Override
//                    public ObservableSource<Object> apply(Observable<Integer> upstream) {
                      /**  注意这个转换 ObservableTransformer<T, R> 中的转换*/
//                        return null;
//                    }
//                })
                .compose(Transformer.intToString())
                .compose(Transformer.handle())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });
    }

    // new Transformer 新建了ObservableTransformer<String,String>对象
    static class Transformer implements ObservableTransformer<String,String>{
        @Override
        public ObservableSource<String> apply(Observable<String> upstream) {
            return upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.newThread());
        }

//        这个方法返回了ObservableTransformer对象
        public static <T> ObservableTransformer<T,T> handle(){
//             这个upstream 是怎么来的？？？？？？？？？？？  Lamada 表达式匿名对象
//             return upstream ->
//                     upstream.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread());

            return new ObservableTransformer<T, T>() {
                @Override
                public ObservableSource<T> apply(Observable<T> upstream) {
                    return upstream
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };
        }

        public static ObservableTransformer<Integer,String> intToString(){
            return upstream -> upstream.map(new Function<Integer, String>() {
                @Override
                public String apply(Integer integer) throws Exception {
                    return integer + "";
                }
            });
        }

        /**
         * 这里的泛型必须是确定的，不然有问题
         */
        public static <T,R> ObservableTransformer<T,R> intToString2(){
            return upstream -> upstream.map(new Function<T, R>() {
                @Override
                public R apply(T t) throws Exception {
                    return null;
                }
            });
        }
    }
}


