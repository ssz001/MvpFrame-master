package com.ssz.framejava.model.remote.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssz.framejava.model.remote.net.tool.interceptor.LoggingInterceptor;
import com.ssz.framejava.utils.converter.GsonConverterFactory;
import com.ssz.framejava.utils.log.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author : zsp
 * time : 2019 08 2019/8/30 15:18
 */
final class RemoteHelper {

    private static volatile RemoteHelper instance;
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;

    public static RemoteHelper getInstance(){
        if (null == instance){
            synchronized (RemoteHelper.class){
                if (null == instance){
                    instance = new RemoteHelper();
                }
            }
        }
        return instance;
    }

    private RemoteHelper(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> {
            try {
                String msg = URLDecoder.decode(message,"UTF-8");
                LogUtil.e("okhttp_","-----"+msg);
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
                LogUtil.e("okhttp_","-----"+message);
            }
        });
        // level 是必须的，否则不打印
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new LoggingInterceptor())
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(URL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    Retrofit getRetrofit(){
        return mRetrofit;
    }

    Retrofit updateRetrofit(){
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(URL.get())
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return mRetrofit;
    }

    Gson buildGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
//               这个属性别开否则缺少@Expose注解的属性将无法解析
//               .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
//              .registerTypeAdapter(int.class,INTEGER)
//              .registerTypeAdapter(Integer.class,INTEGER)
                // long 类型测试后发现似乎没有用，未继续研究。。
//              .registerTypeAdapter(long.class,LONG)
//              .registerTypeAdapter(Long.class,LONG)
//              .registerTypeAdapter(double.class,DOUBLE)
//              .registerTypeAdapter(Double.class,DOUBLE)
//              .registerTypeAdapter(String.class,STRING)
                .setVersion(1.0)
                .create();
    }

    /**
     * 后台返回字段不规则的自定义解析器
     */
//    private static final StringDefaultAdapter STRING = new StringDefaultAdapter();
//    private static final IntegerDefault0Adapter INTEGER = new IntegerDefault0Adapter();
//    private static final LongDefault0Adapter LONG = new LongDefault0Adapter();
//    private static final DoubleDefault0Adapter DOUBLE = new DoubleDefault0Adapter();
}
