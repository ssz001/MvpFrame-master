package com.ssz.frame.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.ssz.frame.utils.ObjectHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author : zsp
 * time : 2019 10 2019/10/12 13:37
 * 情况1：单纯的图片过渡：
 *        这种情况如果不用初始化很复杂的逻辑可以不用SplashActivity
 * 情况2：图片过度后到广告页面（有跳过按钮）:(图片页面应该是初始化逻辑代码时间)
 *        在super.onCreate()前设置SplashActivity 的专属Theme(主要是windowBackground:App启动页图片),然后在super.onCreate()之后，
 *        setContentView()之前,重置SplashActivity 的Theme
 */
abstract class BaseSplashActivity : BaseActivity() {

    private val mHandler = Handler()

    /**
     * 如果setContentView()之前花费比较多的时间，会出现短暂的白屏或黑屏
     * 因为Window窗口先显示，然后再加载的布局。期间出现默认背景空白。
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        aVoidDoubleEnter()

        windowInit()
        super.onCreate(savedInstanceState)
        post(Runnable {
            resetTheme()
            getLayoutId().apply {if (Integer.MAX_VALUE != this)setContentView(this)}
            afterOnCreate()
        },1000)
    }

    /**
     * 初始化逻辑
     */
    protected abstract fun afterOnCreate()

    /**
     * 获取Layout
     */
    protected open fun getLayoutId(): Int = Integer.MAX_VALUE

    protected open fun resetTheme() {
        //todo 这里重置SplashActivity 的 Theme
    }

    /**
     * 全屏显示
     * 或者：Application activity设置style
     *
     * <style name="AppTheme.NoTitle">
    <item name="windowNoTitle">true</item>
    <item name="windowActionBar">false</item>
    </style>
     *
     * <style name="AppTheme.NoTitle.FullScreen">
    <item name="android:windowFullscreen">true</item>
    </style>
     */
    private fun windowInit() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /**
     * 避免从桌面启动程序后，启动Intent不一致，导致的再次实例化
     */
    private fun aVoidDoubleEnter() {
        if (!this.isTaskRoot) {
            val intent = intent
            if (intent != null) {
                val action = intent.action
                // 这里过滤了 其它Activity 里启动的情况？
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                    finish()
                }
            }
        }
    }

    /**
     * 延迟后进入
     */
    protected fun post(run: Runnable,delayTime: Long) {
        mHandler.postDelayed(run, delayTime)
    }

    /**
     * 延迟后进入
     */
    protected fun post(view: View, run: Runnable, delayTime: Long) {
        view.postDelayed(run, delayTime)
    }

    /**
     * 倒计时监听
     */
    protected open fun countDownListener(count: Long) {

    }

    private var disposable: Disposable? = null
    protected fun countDown(count: Long) {
        if (ObjectHelper.nonNull(disposable)) return
        disposable = Observable.interval(1, TimeUnit.SECONDS)
                .take(count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ aLong -> countDownListener(count - aLong!!)},{})
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}