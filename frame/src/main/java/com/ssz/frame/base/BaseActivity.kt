package com.ssz.frame.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.PopupWindow
import android.widget.TextView
import com.ssz.frame.utils.toast.ToastUtil

/**
 * @author : zsp
 * time : 2019 09 2019/9/18 15:33
 */
@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity(){

    protected fun getText(view: TextView) = view.text.toString()

    /**
     * 获取数字，空默认为0
     */
    protected fun getTextToInt(view: TextView):Int{
        val str = view.text.toString()
        return if (str.isEmpty()) 0 else str.toInt()
    }

    protected fun dismiss(dialog: Dialog) {
        if (dialog.isShowing) dialog.dismiss()
    }

    protected fun dismiss(popupWindow: PopupWindow) {
        if (popupWindow.isShowing) popupWindow.dismiss()
    }

    fun showToast(msg: String) {
        ToastUtil.showToast(this, msg)
    }

    fun showToast(msg: String, gravity: Int) {
        ToastUtil.showToast(this, msg, gravity)
    }

    /*************************** get resources *************************/

    fun getColorById(res : Int): Int{
       return ContextCompat.getColor(this,res)
    }

    fun getDrawableById(res : Int): Drawable? {
        return ContextCompat.getDrawable(this,res)
    }

    fun getDimenById(dimenId : Int):Int{
        return resources.getDimension(dimenId).toInt()
    }

    fun getDimenFloatById(dimenId : Int):Float{
        return resources.getDimension(dimenId)
    }

    fun getStringById(resId : Int):String{
        return resources.getString(resId)
    }

    /*************************** get resources end *************************/

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    fun startActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))
    }

    /**
     * 跳转页面
     *
     * @param clz         所跳转的Activity类
     * @param requestCode 请求码
     */
    fun startActivityForResult(clz: Class<*>, requestCode: Int) {
        startActivityForResult(Intent(this, clz), requestCode)
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, clz)
        if (bundle != null) {
            intent.putExtra("bundle", bundle)
        }
        startActivity(intent)
    }

    /**
     * 跳转页面
     *
     * @param clz         所跳转的Activity类
     * @param bundle      跳转所携带的信息
     * @param requestCode 请求码
     */
    fun startActivityForResult(clz: Class<*>, requestCode: Int, bundle: Bundle?) {
        val intent = Intent(this, clz)
        if (bundle != null) {
            intent.putExtra("bundle", bundle)
        }
        startActivityForResult(intent, requestCode)
    }

}