package com.ssz.studydemo.main.data.remote;

import android.util.Log;

import com.google.gson.Gson;
import com.ssz.frame.utils.converter.GsonConverterFactory;
import com.ssz.studydemo.main.data.URL;

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
public class RemoteHelper {

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
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(message -> {
                    try {
                       String msg = URLDecoder.decode(message,"UTF-8");
                       Log.e("okhttp","-----"+msg);
                    }catch (UnsupportedEncodingException e){
                       e.printStackTrace();
                       Log.e("okhttp","-----"+message);
                    }
                }))
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(URL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson().newBuilder().create()))
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
                .baseUrl(URL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson().newBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return mRetrofit;
    }
}
