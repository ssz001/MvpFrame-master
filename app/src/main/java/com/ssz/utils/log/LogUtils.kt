package com.ssz.utils.log

import com.ssz.studydemo.BuildConfig


/**
 * @author : zsp
 * time : 2019 09 2019/9/18 15:12
 */
object LogUtils {

    val DEBUG = BuildConfig.DEBUG

    fun i(tag: String, msg: String) {
        if (DEBUG)
            android.util.Log.i(tag, msg)
    }

    fun e(tag: String, msg: String) {
        if (DEBUG)
            android.util.Log.e(tag, msg)
    }

    fun d(tag: String, msg: String) {
        if (DEBUG)
            android.util.Log.d(tag, msg)
    }

    fun v(tag: String, msg: String) {
        if (DEBUG)
            android.util.Log.v(tag, msg)
    }

    fun w(tag: String, msg: String) {
        if (DEBUG)
            android.util.Log.w(tag, msg)
    }
}