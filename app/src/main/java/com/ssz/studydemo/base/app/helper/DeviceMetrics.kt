package com.ssz.studydemo.base.app.helper

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager


/**
 * @author : zsp
 * time : 2019 12 2019/12/17 11:21
 * 获取设备参数，懒加载
 */
object DeviceMetrics{

    val screenWidth by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        val dm = DisplayMetrics()
        val windowManager = AppHelper.getApplication().
                getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(dm)
        dm.widthPixels
    }

    val screenHeight  by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        val dm = DisplayMetrics()
        val windowManager = AppHelper.getApplication().
                getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(dm)
        dm.heightPixels
    }
}

