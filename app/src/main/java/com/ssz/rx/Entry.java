package com.ssz.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : zsp
 * time : 2019 09 2019/9/30 9:10
 */
public class Entry {

    public void main() {
//
//        List<? extends Number> list = null;
//        list = new ArrayList<Integer>();
//        Number num1 = list.get(1);
//        Integer num2 = list.get(1);
//        list.add(new Integer(1));

        Observable mObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            }
        });

        // ep:
        Observable.just("String")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return 8;
                    }
                })
                .subscribe();

//      生成了  ObservableJust<T> (是Observable<T> 的finial子类)
        Observable<String> observableJust = Observable.just("4545555");

//        public final <R> Observable<R> map(Function<? super T, ? extends R> mapper) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        return RxJavaPlugins.onAssembly(new ObservableMap<T, R>(this, mapper));}
//       上面的的 this 是 observableJust,也就是说ObservableMap<T,R> 对象会持有 ObservableJust （更精确的应该为上游Observable对象）
//       查看Observable子类发现每次 Observable 的变换操作符都会新建立一个对应的 Observable 对象，而这个新建的Observable 对象都会
//       持有上游Observable 的（同样为Observable）对象的引用；

        // ? super T 可以放T 或者T 的父类
        // ? extends R 可以放R 以及R的子类
        // 为什么不能用ObservableMap<T,R> 来接收？
        // 假设 (T 为 String ，R 为Integer,这是运行时确定的唯一类型) -
        // 所以编译器是根据Function<? super T, ? extends R>来推断返回值类型，因此列出ObservableMap<T,R> 的可能类型
        // 1. ObservableMap<String,Integer>               {根据左侧的推出 T 不能唯一确定，
        // 2. ObservableMap<Object,Integer>     ------->   因为无法确定上界（Object 没有意义），
        // 3. ObservableMap<String,Integer的子类>          R 虽然也不能唯一确定，但是可以用上界Integer接收}
        // 4. ObservableMap<Object,Integer的子类>

        // 新建了一个ObservableMap<T,R>,因为T 无法推断，所以用Observable<R> 来接收 -- // 修改为 Function<? super T, ? extends R> mapper
        Observable<Integer> observableMap = observableJust.map(new Function<String,Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return 125;
            }
        });

//    订阅： public final void subscribe(Observer<? super T> observer) {}
//    <? super T> 这里的T 是Observable<T> 里的 T ;

       observableMap
               .subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<Integer>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(Integer integer) {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onComplete() {

          }
      });
       /***********************************************/
       Observable.just("String")
               .map(new Function<String, Integer>() {
                   @Override
                   public Integer apply(String s) throws Exception {
                       return null;
                   }
               }).subscribe();

       //  ObservableJust == this
//       1:  protected void subscribeActual(Observer<? super T> observer) {
//            ScalarDisposable<T> sd = new ScalarDisposable<T>(observer, value);
//            observer.onSubscribe(sd);
//            sd.run();
//        }

        // ObservableMap == this
//       2:  public void subscribeActual(Observer<? super U> t) {
//            这里的 source 其实就是ObservableJust
//            source.subscribe(new MapObserver<T, U>(t, function));
//        }

//      1. ObservableJust 调用 subscribe() 订阅产生的地方
//      2. ObservableMap  实际 调用subscribe() 订阅产生的地方
//      3. 经过 map 变换，将 observer.onSubscribe(sd)   --->   observer.onSubscribe(new MapObserver<T,U>(t,function))

        // 源变换后
        //2:  protected void subscribeActual(Observer<? super T> observer) {
//            ScalarDisposable<T> sd = new ScalarDisposable<T>(new MapObserver<T, U>(t, function), value);
//            observer.onSubscribe(sd);
//            sd.run();
//        }
    }
}
