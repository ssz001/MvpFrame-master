package com.ssz.studydemo.main.data.remote2;

import com.ssz.frame.utils.converter.GsonConverterFactory;
import com.ssz.studydemo.main.data.URL;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class Net2 {

    private static volatile Net2 instance;
    private Retrofit mRetrofit;
    private Api2 api2;

    public static Net2 getInstance() {
        if (null == instance) {
            synchronized (Net2.class) {
                if (null == instance) {
                    instance = new Net2();
                }
            }
        }
        return instance;
    }

    private Net2 (){
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                // 切换BaseUrl
                .addInterceptor(new BaseUrlInterceptor());

        OkHttpClient okHttpClient = client.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(URL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        api2 = mRetrofit.create(Api2.class);
    }

    public Api2 getApi2() {
        return Objects.requireNonNull(api2,"null == api2 ?");
    }

    /**
     * json 数据快速生成RequestBody
     */
    public static RequestBody toRequestBody(String json){
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
    }
}
