package com.ssz.framejava.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.ssz.framejava.base.app.helper.AppHelper;
import com.ssz.framejava.base.app.helper.AppStatus;
import com.ssz.framejava.utils.ObjectHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : zsp
 * time : 2019 10 2019/10/12 13:37
 * 情况1：单纯的图片过渡：
 * 这种情况如果不用初始化很复杂的逻辑可以不用SplashActivity
 * 情况2：图片过度后到广告页面（有跳过按钮）:(图片页面应该是初始化逻辑代码时间)
 * 在super.onCreate()前设置SplashActivity 的专属Theme(主要是windowBackground:App启动页图片),然后在super.onCreate()之后，
 * setContentView()之前,重置SplashActivity 的Theme
 */
@SuppressLint("Registered")
public abstract class BaseSplashActivity extends AppCompatActivity {

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 如果setContentView()之前花费比较多的时间，会出现短暂的白屏或黑屏
     * 因为Window窗口先显示，然后再加载的布局。期间出现默认背景空白。
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AppHelper.get().setAppStatus(AppStatus.ALIVE);
        aVoidDoubleEnter();

        windowInit();
        super.onCreate(savedInstanceState);
        post(() -> {
            resetTheme();
            if (getLayoutId() != 0) {
                setContentView(getLayoutId());
            }
            afterOnCreate(savedInstanceState);
        }, getDelayTime());
    }

    /**
     * 重写覆盖加载布局
     */
    protected int getLayoutId() {
        return 0;
    }

    protected long getDelayTime() {
        return 1000;
    }

    protected void resetTheme() {
        //todo 这里重置SplashActivity 的 Theme
    }

    /**
     * 全屏显示
     * 或者：Application activity设置style
     *
     * <style name="AppTheme.NoTitle">
     * <item name="windowNoTitle">true</item>
     * <item name="windowActionBar">false</item>
     * </style>
     *
     * <style name="AppTheme.NoTitle.FullScreen">
     * <item name="android:windowFullscreen">true</item>
     * </style>
     */
    private void windowInit() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 避免从桌面启动程序后，启动Intent不一致，导致的再次实例化
     */
    public void aVoidDoubleEnter() {
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                // 这里过滤了 其它Activity 里启动的情况？
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                }
            }
        }
    }

    /**
     * 延迟后进入
     */
    public void post(Runnable run, long delayTime) {
        mHandler.postDelayed(run, delayTime);
    }

    /**
     * 倒计时监听
     */
    protected void countDownListener(long count) {

    }

    protected abstract void afterOnCreate(@Nullable Bundle savedInstanceState);

    private Disposable disposable;
    protected void countDown(final long count) {
        if (ObjectHelper.nonNull(disposable)) return;
        disposable = Observable.interval(1, TimeUnit.SECONDS)
                .take(count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> countDownListener(count - aLong),
                        throwable -> {
                        });
    }

    @Override
    protected void onDestroy() {
        if (ObjectHelper.nonNull(disposable)) {
            disposable.dispose();
        }
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
