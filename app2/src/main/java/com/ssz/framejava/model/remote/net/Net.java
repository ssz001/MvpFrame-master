package com.ssz.framejava.model.remote.net;


import com.ssz.framejava.utils.ObjectHelper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * @author : zsp
 * time : 2019 08 2019/8/30 13:58
 * attention：使用Dagger2框架的时候不应该在调用这个类请求网络
 */
public final class Net {
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

    public static Api request(){
        return getInstance().getApi();
    }

    private Net() {
        mRetrofit = RemoteHelper.getInstance().getRetrofit();
        api = mRetrofit.create(Api.class);
    }

    public OkHttpClient getOkhttpClient(){
        return RemoteHelper.getInstance().getOkHttpClient();
    }

    public void updateRetrofit(){
        mRetrofit = RemoteHelper.getInstance().updateRetrofit();
        api = mRetrofit.create(Api.class);
    }

    public Api getApi(){
        return ObjectHelper.requireNotNull(api,"api not init !");
    }

    /**
     * json 数据快速生成RequestBody
     */
    public static RequestBody toRequestBody(String json){
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
    }
}
