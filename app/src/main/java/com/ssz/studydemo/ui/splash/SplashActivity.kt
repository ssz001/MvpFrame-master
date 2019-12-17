package com.ssz.studydemo.ui.splash

import android.content.Intent
import com.ssz.studydemo.R
import com.ssz.studydemo.base.BaseSplashActivity
import com.ssz.studydemo.ui.dispatch.DispatchActivity

/**
 * @author : zsp
 * time : 2019 10 2019/10/24 13:10
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
class SplashActivity : BaseSplashActivity() {

    override fun resetTheme() {
        setTheme(R.style.AppTheme_NoTitle)
    }

    /**
     * 获取Layout,默认不设置
     */
    override fun getLayoutId(): Int {
        return super.getLayoutId()
    }

    /**
     * 初始化逻辑
     */
    override fun afterOnCreate() {
        startActivity(Intent(this, DispatchActivity::class.java))
        overridePendingTransition(0, 0)
        finish()
//        post(Runnable {
//        },3000)
    }
}