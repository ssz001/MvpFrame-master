package com.ssz.framejava.base.dagger.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssz.framejava.model.remote.net.Api;
import com.ssz.framejava.model.remote.net.Net;
import com.ssz.framejava.model.remote.net.URL;
import com.ssz.framejava.utils.converter.GsonConverterFactory;
import com.ssz.framejava.utils.log.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author : zsp
 * time : 2019 11 2019/11/19 11:07
 * Net 和 Api 两种方法，选择其中一个来用，不要混合用,实际使用的时候可以注释掉其中一个
 */
@Module
public class NetModule {

    @Singleton
    @Provides
    public Net provideNet() {
        return Net.getInstance();
    }

    @Singleton
    @Provides
    public Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(URL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    @Singleton
    @Provides
    public Interceptor provideInterceptor() {
        return new HttpLoggingInterceptor(msg -> {
            try {
                String message = URLDecoder.decode(msg, "UTF-8");
                LogUtil.e("okhttp_", "-----" + message);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                LogUtil.e("okhttp_", "-----" + msg);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
//               这个属性别开否则缺少@Expose注解的属性将无法解析
//               .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
//              .registerTypeAdapter(int.class,INTEGER)
//              .registerTypeAdapter(Integer.class,INTEGER)
                // long 似乎有点问题
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
