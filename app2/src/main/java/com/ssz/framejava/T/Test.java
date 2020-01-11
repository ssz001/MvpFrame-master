package com.ssz.framejava.T;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssz.framejava.base.app.helper.AppHelper;
import com.ssz.framejava.data.local.bean.Student;
import com.ssz.framejava.model.remote.net.Net;
import com.ssz.framejava.model.remote.net.URL;
import com.ssz.framejava.model.remote.net.execption.ApiException;
import com.ssz.framejava.model.remote.net.execption.TokenExpiredException;
import com.ssz.framejava.model.remote.net.handler.single.net200.RetryTransformer200;
import com.ssz.framejava.model.remote.net.response.Result;
import com.ssz.framejava.model.remote.net.schedulers.lamada.RxIo;
import com.ssz.framejava.model.remote.net.tool.gson.DoubleDefault0Adapter;
import com.ssz.framejava.model.remote.net.tool.gson.IntegerDefault0Adapter;
import com.ssz.framejava.model.remote.net.tool.gson.LongDefault0Adapter;
import com.ssz.framejava.model.remote.net.tool.gson.StringDefaultAdapter;
import com.ssz.framejava.utils.gsonutils.ReflectUtil;
import com.ssz.framejava.utils.log.LogUtil;
import com.ssz.framejava.utils.toast.ToastUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author : zsp
 * time : 2019 11 2019/11/11 14:59
 */
public class Test {

    public void startTest() {
//        operate();
//        start2();
//        test5555();
//        test();
//        gsonTest();


    }

    Gson buildGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
//               这个属性别开否则缺少@Expose注解的属性将无法解析
//               .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .registerTypeAdapter(int.class, INTEGER)
                .registerTypeAdapter(Integer.class, INTEGER)
                .registerTypeAdapter(long.class, LONG)
                .registerTypeAdapter(Long.class, LONG)
                .registerTypeAdapter(double.class, DOUBLE)
                .registerTypeAdapter(Double.class, DOUBLE)
                .registerTypeAdapter(String.class, STRING)
                .setVersion(1.0)
                .create();
    }

    /**
     * 后台返回字段不规则的自定义解析器
     */
    private static final StringDefaultAdapter STRING = new StringDefaultAdapter();
    private static final IntegerDefault0Adapter INTEGER = new IntegerDefault0Adapter();
    private static final LongDefault0Adapter LONG = new LongDefault0Adapter();
    private static final DoubleDefault0Adapter DOUBLE = new DoubleDefault0Adapter();

    //
    public void gsonTest() {
       Gson gson = buildGson();
       Student student = new Student();
       student.setId(1L);
       student.setName("zsp");
       student.setAge(18);
       String str = ReflectUtil.toJson(student);
       LogUtil.d("PPPPS",str+"");

       String sts = "{\"age\": \"\",\"id\": null,\"name\": \"zsp\"}";

      //      {
        //      "age": 18,
        //      "id": 1,
        //      "name": "zsp"
        //    }

       Student stu = gson.fromJson(sts,Student.class);

       LogUtil.d("tuuui",""+stu);
    }

    private void test() {
//        URL.operate().setIp("192.168.0.100").setPort(8888);
        URL.BASE_URL = "https://api.apiopen.top";

        Disposable d = Net.request().getJoke(1, 2, "video")
                .compose(RxIo.applySinale())
                .compose(RetryTransformer200.handleException())
                .subscribe(new Consumer<List<SayBean>>() {
                    @Override
                    public void accept(List<SayBean> sayBeans) throws Exception {
                        LogUtil.d("sayBean", sayBeans.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtil.showToast(AppHelper.getApplication(), throwable.toString());
                    }
                });
    }

    @NonNull
    private Single<Result<Student>> getStudent() {
        Student stu = new Student();
        stu.setId(1L);
        stu.setAge(33);
        stu.setName("student");
        Result<Student> rs = new Result<>();
        rs.setCode(401);
        rs.setMessage("success");
        rs.setData(stu);
        return Single.just(rs);
    }

    public void test5555() {
        Disposable d = getStudent()
                .compose(RxIo.applySinale())
                .compose(RetryTransformer200.handleException())
                .subscribe(new Consumer<Student>() {
                    @Override
                    public void accept(Student student) throws Exception {
                        LogUtil.d("token_", student.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.d("token_", throwable.toString());
                    }
                });
    }


    class Code {
        public int code;
        public String message;
        public String data;

        public Code(int code, String message, String data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }
    }

    int in = 0;

    Code code = new Code(401, "凭证失效", "");

    private void setCode(Code code) {
        this.code = code;
    }

    private void setCode() {
        this.code.code = 200;
        this.code.message = "请求成功";
    }

    private Code getCode() {
        Log.d("getCode", "do getCode");
        return code;
    }

    public void start() {
        Log.d("start1", "operate ++++ ");
        Disposable d = Observable.just(getCode())
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Code>>() {
                    @Override
                    public ObservableSource<? extends Code> apply(Throwable throwable) throws Exception {
                        return Observable.error(new ApiException(0, "请求过程中发生的异常"));
                    }
                })
                .flatMap(new Function<Code, ObservableSource<? extends Result<String>>>() {
                    @Override
                    public ObservableSource<Result<String>> apply(Code code) throws Exception {
                        if (code.code == 401) {
                            Log.d("start1", "TokenExpiredExection()");
                            return Observable.error(new TokenExpiredException());
                        }
                        Result<String> re = new Result<>();
                        re.setCode(code.code);
                        re.setMessage(code.message);
                        re.setData("message");
                        return Observable.just(re);
                    }
                }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> thr) throws Exception {
                        return thr.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                if (throwable instanceof TokenExpiredException) {
                                    return Observable.just("")
                                            .compose(RxIo.applyObservable())
                                            .doOnNext(new Consumer<String>() {
                                                @Override
                                                public void accept(String s) throws Exception {
                                                    setCode();
                                                    Log.d("start1", "___________________setCode()");
//                                            setCode(new Code(200,"success","[]"));
                                                }
                                            });
                                }

                                return Observable.error(throwable);
                            }
                        });
                    }
                })
                .subscribe(new Consumer<Result<String>>() {
                    @Override
                    public void accept(Result<String> stringResult) throws Exception {
                        Log.d("start1", "success :" + stringResult.getCode() + " " + stringResult.getMessage() + "  " + stringResult.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("start1", "throwable : " + throwable.toString());
                    }
                });
    }


    public void start2() {
        Log.d("test2", "operate ++++ ");
        Observable.just(code)
                .compose(RxIo.applyObservable())
                .flatMap(new Function<Code, ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<String> apply(Code code) throws Exception {
                        if (code.code == 401) {
                            return Observable.error(new IllegalStateException(code.message));
                        }
                        return Observable.just("" + code.code + " " + code.message + " " + code.data);
                    }
                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> apply(Throwable throwable) throws Exception {
                        if (throwable instanceof IllegalStateException) {
                            return Observable.error(new ApiException(1, "1"));
                        }
                        return Observable.error(throwable);
                    }
                })
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<String> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return Observable.just("").doOnNext(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Log.d("test2", "before setcode ");
                                setCode(new Code(200, "success", "[]"));
                            }
                        });
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("test2", s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("test2", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
