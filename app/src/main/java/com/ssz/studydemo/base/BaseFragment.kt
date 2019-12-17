package com.ssz.studydemo.base

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.widget.PopupWindow
import android.widget.TextView
import com.ssz.studydemo.base.app.helper.AppHelper
import com.ssz.studydemo.utils.toast.ToastUtil

/**
 * @author : zsp
 * time : 2019 10 2019/10/15 13:28
 */
abstract class BaseFragment : Fragment() {

    protected fun getTextToInt(view: TextView):Int{
        val str = view.text.toString()
        return if (str.isEmpty()) 0 else str.toInt()
    }

    protected fun getText(view: TextView) = view.text.toString()

    protected fun dismiss(dialog: Dialog) {
        if (dialog.isShowing) dialog.dismiss()
    }

    protected fun dismiss(popupWindow: PopupWindow) {
        if (popupWindow.isShowing) popupWindow.dismiss()
    }

    protected fun showToast(msg: String) {
        ToastUtil.showToast(activity, msg)
    }

    protected fun showToast(msg: String, gravity: Int) {
        ToastUtil.showToast(activity, msg, gravity)
    }

    /*************************** get resources *************************/

    fun getColorById(res : Int): Int{
        return ContextCompat.getColor(AppHelper.getApplication(),res)
    }

    fun getDrawableById(res : Int): Drawable? {
        return ContextCompat.getDrawable(AppHelper.getApplication().applicationContext,res)
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
        activity?.startActivity(Intent(activity, clz))
    }

    /**
     * 跳转页面
     *
     * @param clz         所跳转的Activity类
     * @param requestCode 请求码
     */
    fun startActivityForResult(clz: Class<*>, requestCode: Int) {
        activity?.startActivityForResult(Intent(activity, clz), requestCode)
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        activity?.startActivity(Intent(activity, clz).also { it.putExtra("bundle", bundle) })
    }

    /**
     * 跳转页面
     *
     * @param clz         所跳转的Activity类
     * @param bundle      跳转所携带的信息
     * @param requestCode 请求码
     */
    fun startActivityForResult(clz: Class<*>, requestCode: Int, bundle: Bundle?) {
        activity?.startActivityForResult(Intent(activity, clz)
                .also { it.putExtra("bundle", bundle) }, requestCode)
    }
}