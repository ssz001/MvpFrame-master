package com.ssz.framejava.model.remote.net;


import com.ssz.framejava.T.SayBean;
import com.ssz.framejava.model.remote.net.response.Result;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author : zsp
 * time : 2019 08 2019/8/30 14:09
 */
public interface Api {

    /**
     * example:
     * 1.retrofit 不设置 BaseUrl。
     */
    String jop = "https://api.apiopen.top";

    @Headers({"access_token = token"})
    @POST("xxxxxxxxx")
    Single<String> get0(@Body RequestBody tagList);

    @POST("xxxxxxxxx")
    Single<Result<Boolean>> get0();

//     https://api.apiopen.top/getJoke?page=1&count=2&type=video
//     @POST("/getJoke")
//     @FormUrlEncoded
//     Single<ResultType<List<SayBean>>> getJoke(@Field("page") int page, @Field("count") int count, @Field("type") String type);

//    @Headers("app:value")
    @GET(jop+"/getJoke")
    Single<Result<List<SayBean>>> getJoke(@Query("page") int page, @Query("count") int count, @Query("type") String type);

    @POST
    Single<Result<Boolean>> get2(@Url String url, @Header("access_token") String token);

    @POST
    Single<Result<Boolean>> get3(@Url String k, @HeaderMap Map<String, String> map);
}
