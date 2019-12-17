package com.ssz.studydemo.model.remote.net

import com.ssz.studydemo.data.remote.bean.SayBean
import com.ssz.studydemo.model.remote.net.response.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author : zsp
 * time : 2019 12 2019/12/6 14:36
 */
interface Api {
    companion object{
        // URL
        const val BASE_URL = "http://192.168.0.100:8000"
        // 状态码
        const val SUCCESS = 200
    }

    @GET("https://api.apiopen.top/getJoke")
    fun getJoke(@Query("page") page :Int, @Query("count") count:Int, @Query("type") type : String): Call<Result<List<SayBean>>>
}