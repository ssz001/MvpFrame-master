package com.ssz.framejava.model.remote.net.tool.interceptor;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.ssz.framejava.account.AccountManager;
import com.ssz.framejava.base.app.helper.AppHelper;
import com.ssz.framejava.model.remote.net.URL;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author zsp
 * create at 2019/1/21 10:20
 * 自动刷新Token的拦截器，bean类需要自定义，需要和后台约定返回的HttpException == 401
 */
public class TokenRefreshInterceptor implements Interceptor {

    /**  取消订阅: disposable.dispose() */
    private Disposable disposable;
    /**  TokenExpiredException 过期前 x 毫秒就可以刷新Token */
    private static final long REFRESH_TIME = 20*1000L;

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Log.d("rToken","未刷新前：    TokenExpiredException     ----  "+AccountManager.getToken(AppHelper.getApplication()));
        Log.d("rToken","未刷新前： RefreshToken ----  "+AccountManager.getRefreshToken(AppHelper.getApplication()));
        Log.d("rToken","拦截器里的线程是："+ Thread.currentThread().getName());
        // 对应登录
        if (TextUtils.isEmpty(AccountManager.getToken(AppHelper.getApplication()))){
            Request request = chain.request()
                    .newBuilder()
                    .build();
            return chain.proceed(request);
        }
        // 对应每次非登录的请求
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Authorization","bearer "+AccountManager.getToken(AppHelper.getApplication()));
        Response response = chain.proceed(builder.build());
        // 检查是不是 401 错误
        if (isTokenExpired(response)) {
            getNewToken();
//            getNewToken1();
            Request.Builder newBuilder = chain.request().newBuilder();
            newBuilder.addHeader("Authorization", "bearer " + AccountManager.getToken(AppHelper.getApplication()));
            return chain.proceed(newBuilder.build());
        }
        return response;
    }

    /**
     * @param response ;
     * 判断Token是否过期，通过判断 code == 401 ；
     * 本项目 Token过期 属于  HttpException code = 401 的其中一种
     */
    private boolean isTokenExpired(Response response){
        return response.code() == 401;
    }

    /**
     * Token过期获取新的Token
     * 因为同步，所以可能有多个不同线程的请求在等待进入这个方法，而我只要第一次更新成功Token，就可以了
     * 加入判断，第一个更新Token成功后，第二个以后进来的线程不要去执行更新Token操作；判断依据：更新Token后在本地存入新Token的到期时间：
     * System.currentTimeMillis() + tokenBean.getData().getExpires_in() * 1000L
     * 判断当前时间System.currentTimeMillis() 是不是 大于等于 本地时间 已经存入的时间
     *      是：说明确实需要更新。
     *      否：说明已经更新过了。
     *      这样可以过滤很多不必要的操作；
     */
    private synchronized void getNewToken() throws IOException{
        Log.d("newToken","--------------------走了getNewToken-----------------------");
        Log.d("newToken","-------"+System.currentTimeMillis()+"-------------"+AccountManager.getTokenTime(AppHelper.getApplication())+"-----------"+(System.currentTimeMillis() - AccountManager.getTokenTime(AppHelper.getApplication()))+"------------");
        long now = System.currentTimeMillis();
        long tokenTime = AccountManager.getTokenTime(AppHelper.getApplication());
        if (now >= tokenTime || tokenTime - now <= 20000L){
            Log.d("newToken","跟新了-------------------------------------------");
            Log.d("too", "获取Token的线程是：" + Thread.currentThread().getName());
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("refreshToken", AccountManager.getRefreshToken(AppHelper.getApplication()))
                    .build();
            Request request = new Request.Builder()
                    .url(URL.get() + "ground-wire/online/refresh-token")
                    .post(body)
                    .build();
            Call call = okHttpClient.newCall(request);
            String response = call.execute().body().string();
            Log.d("rToken", response);
            if (!TextUtils.isEmpty(response)) {
//                RefreshTokenBean tokenBean = new Gson().fromJson(response, RefreshTokenBean.class);
//                if (tokenBean.getCode() == 200) {
//                    AccountManager.saveToken(Framework.context, tokenBean.getData().getAccess_token());
//                    Log.d("rToken","新Token " + tokenBean.getData().getAccess_token());
//                    AccountManager.saveTokenTime(Framework.context, System.currentTimeMillis() + tokenBean.getData().getExpires_in() * 1000L);
//                    Log.d("rToken", "Token有效时间 "+tokenBean.getData().getExpires_in() + "");
//                    AccountManager.saveRefreshToken(Framework.context, tokenBean.getData().getRefresh_token());
//                    Log.d("rToken", "刷新Token "+tokenBean.getData().getRefresh_token());
//                }
            }
        }
    }

    /**
     * 调度都在UI线程（同一线程）
     * Token过期获取新的Token
     * 因为同步，所以可能有多个不同线程的请求在等待进入这个方法，而我只要第一次更新成功Token，就可以了
     * 加入判断，第一个更新Token成功后，第二个以后进来的线程不要去执行更新Token操作；判断依据：更新Token后在本地存入新Token的到期时间：
     * System.currentTimeMillis() + tokenBean.getData().getExpires_in() * 1000L
     * 判断当前时间System.currentTimeMillis() 是不是 大于等于 本地时间 已经存入的时间
     *      是：说明确实需要更新。
     *      否：说明已经更新过了。
     *
     *      过滤很多不必要的操作；
     */
    private synchronized void getNewToken1(){
//        long now = System.currentTimeMillis();
//        long tokenTime = AccountManager.getTokenTime(Framework.context);
//        if (now >= tokenTime || tokenTime - now <= REFRESH_TIME) {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .client(new OkHttpClient.Builder()
//                            .addInterceptor(new LoggingInterceptor())
//                            .readTimeout(Constant.CON_READ_TIME_OUT, TimeUnit.SECONDS)
//                            .connectTimeout(Constant.CON_CONNECT_TIME_OUT, TimeUnit.SECONDS)
//                            .retryOnConnectionFailure(true)
//                            .build())
//                    .baseUrl(AccountManager.getServerAddress(Framework.context))
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            Api api = retrofit.create(Api.class);
//            api.refreshToken(AccountManager.getRefreshToken(Framework.context))
//                    .compose(ResponseTransformer200.handleException())
//                    // 必须是同步请求
//                    .compose(RxIoScheduler.getInstance().uiToIo())
//                    .subscribe(new Observer<TokenBean>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//                            disposable = d;
//                        }
//
//                        @Override
//                        public void onNext(TokenBean tokenBean) {
//                            AccountManager.saveToken(Framework.context, tokenBean.getAccessToken());
//                            AccountManager.saveTokenTime(Framework.context, tokenBean.getExpiresIn());
//                            AccountManager.saveRefreshToken(Framework.context, tokenBean.getRefreshToken());
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            if (e instanceof ApiException){
//                                ApiException ae = (ApiException)e;
//                                ToastUtil.showToast(Framework.context,""+ae.getMessage());
//                            }
//                            if (disposable != null) {
//                                disposable.dispose();
//                            }
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            if (disposable != null) {
//                                disposable.dispose();
//                            }
//                        }
//                    });
//        }
    }
}
