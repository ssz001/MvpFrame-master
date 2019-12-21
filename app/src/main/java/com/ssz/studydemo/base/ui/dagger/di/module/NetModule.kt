package com.ssz.studydemo.base.ui.dagger.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssz.studydemo.BuildConfig
import com.ssz.studydemo.model.remote.net.Api
import com.ssz.studydemo.model.remote.net.Net
import com.ssz.studydemo.utils.converter.GsonConverterFactory
import com.ssz.studydemo.utils.log.LogUtils
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author : zsp
 * time : 2019 12 2019/12/11 14:28
 *
 */
@Module
class NetModule {

    @Singleton
    @Provides
    fun provideNet():Net{
        return Net.getNet()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor {
        return HttpLoggingInterceptor { msg ->
            try {
                val message = URLDecoder.decode(msg, "UTF-8")
                LogUtils.e("okhttp_", "-----$message")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                LogUtils.e("okhttp_", "-----$msg")
            }
        }.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
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
                .create()
    }

    /**
     * 后台返回字段不规则的自定义解析器
     */
//    private static final StringDefaultAdapter STRING = new StringDefaultAdapter();
//    private static final IntegerDefault0Adapter INTEGER = new IntegerDefault0Adapter();
//    private static final LongDefault0Adapter LONG = new LongDefault0Adapter();
//    private static final DoubleDefault0Adapter DOUBLE = new DoubleDefault0Adapter();
}