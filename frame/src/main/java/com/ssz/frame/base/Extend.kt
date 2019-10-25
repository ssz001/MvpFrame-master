package com.ssz.frame.base

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.ssz.frame.utils.Framework
import com.ssz.frame.utils.toast.ToastUtil

/**
 * @author : zsp
 * time : 2019 10 2019/10/25 16:36
 * android 扩展类
 */

fun AppCompatActivity.getColorById(res : Int):Int{
    return ContextCompat.getColor(this,res)
}

fun AppCompatActivity.getDrawableById(res : Int): Drawable? {
    return ContextCompat.getDrawable(this,res)
}

fun <T> showToast(msg: String) {
    ToastUtil.showToast(Framework.context, msg)
}

fun <T> showToast(msg: String, gravity: Int) {
    ToastUtil.showToast(Framework.context, msg, gravity)
}