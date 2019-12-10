package com.ssz.studydemo.data.remote;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * @author : zsp
 * time : 2019 08 2019/8/30 13:58
 */
public class Net {
    private static volatile Net instance;
    private Retrofit mRetrofit;
    private Api api;

    public static Net getInstance() {
        if (null == instance) {
            synchronized (Net.class) {
                if (null == instance) {
                    instance = new Net();
                }
            }
        }
        return instance;
    }

    public OkHttpClient getOkhttpClient(){
        return RemoteHelper.getInstance().getOkHttpClient();
    }

    private Net() {
        mRetrofit = RemoteHelper.getInstance().getRetrofit();
        api = mRetrofit.create(Api.class);
    }

    public void updateRetrofit(){
        mRetrofit = RemoteHelper.getInstance().updateRetrofit();
        api = mRetrofit.create(Api.class);
    }

    public static Api request(){
       return getInstance().getApi();
    }

    public Api getApi(){
        return Objects.requireNonNull(api,"null == api ?");
    }

    /**
     * json 数据快速生成RequestBody
     */
    public static RequestBody toRequestBody(String json){
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
    }
}
