package com.ssz.studydemo.model.remote.net

import com.ssz.studydemo.data.remote.SayBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author : zsp
 * time : 2019 12 2019/12/6 14:36
 */
interface Api {

    @GET("https://api.apiopen.top/getJoke")
    fun getJoke(@Query("page") page :Int, @Query("count") count:Int, @Query("type") type : String): Call<Result<List<SayBean>>>
}