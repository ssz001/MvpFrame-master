package com.ssz.studydemo.model.remote.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssz.studydemo.BuildConfig
import com.ssz.studydemo.utils.LoggingInterceptor
import com.ssz.studydemo.utils.converter.GsonConverterFactory
import com.ssz.studydemo.utils.log.LogUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

/**
 * @author : zsp
 * time : 2019 12 2019/12/17 15:00
 */
class RemoteHelper private constructor() {

    private var mRetrofit : Retrofit
    private var mOkHttpClient: OkHttpClient

    companion object{
        private val mRemoteHelper : RemoteHelper by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { RemoteHelper()}
        fun getInstance() = mRemoteHelper
    }

    init {
        val interceptor = HttpLoggingInterceptor { message ->
            try {
                val msg = URLDecoder.decode(message, "UTF-8")
                LogUtils.e("okhttp_", "-----$msg")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                LogUtils.e("okhttp_", "-----$message")
            }
        }
        // level 是必须的，否则不打印
        interceptor.level.apply {
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE}
        mOkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(LoggingInterceptor())
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()

        mRetrofit = Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun getOkHttpClient(): OkHttpClient {
        return mOkHttpClient
    }

    fun getRetrofit(): Retrofit {
        return mRetrofit
    }

    private fun buildGson(): Gson {
        return GsonBuilder()
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