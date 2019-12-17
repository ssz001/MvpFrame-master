package com.ssz.studydemo.model.remote.net

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit

/**
 * @author : zsp
 * time : 2019 12 2019/12/17 14:55
 */
class Net private constructor(){

    private var api: Api
    private var mRetrofit: Retrofit = RemoteHelper.getInstance().getRetrofit()

    companion object {
        private val instance: Net by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { Net() }
        fun getNet() = instance
        fun request() = instance.api
    }

    init {
        api = mRetrofit.create(Api::class.java)
    }

    fun getOkhttpClient(): OkHttpClient {
        return RemoteHelper.getInstance().getOkHttpClient()
    }

    /**
     * json 数据快速生成RequestBody
     */
    fun toRequestBody(json: String): RequestBody {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
    }
}