package com.ssz.baselibrary.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.ssz.baselibrary.app.helper.AppHelper;
import com.ssz.baselibrary.utils.log.LogUtil;


/**
 * @author zsp
 * create at 2019/1/22 15:14
 * 关于界面的操作工具类
 */
public final class ScreenUtil {

    private static Context getContext(){
        return AppHelper.getApplication();
    }

    /**
     * 将px值转换为dp值
     */
    public static int pxTodp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dp值转换为px值
     */
    public static int dpTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将dp值转换为px值
     */
    public static float dpTopxF(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

    /**
     * 将px值转换为sp值
     */
    public static int pxTosp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     */
    public static int spTopx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕的宽
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**、
     * 布局需要绘制完成
     * 获取控件相对于窗口的坐标,小
     */
    public static int[] getViewIntWindow(View v){
        int[] arr = new int[2];
        v.getLocationInWindow(arr);
        return arr;
    }

    /**
     * 布局需要绘制完成
     * 获取控件相对于屏幕的坐标,大
     */
    public static int[] getViewOnScreen(View v){
        int[] arr = new int[2];
        v.getLocationOnScreen(arr);
        return arr;
    }

    /**
     * 获取字符串资源
     * 在某些平台会无法得到建议用传入 Context的获取方式
     */
    public static String getString(int stringId){
        return getString(getContext(),stringId);
    }


    /**
     * 获取字符串资源
     */
    public static String getString(Context context,int stringId){
        return context.getResources().getString(stringId);
    }

    /**
     * 获取颜色资源
     * 在某些平台会无法得到建议用传入 Context的获取方式
     */
    public static int getColor(int colorId){
        return getColor(getContext(),colorId);
    }

    /**
     * 获取颜色资源
     */
    public static int getColor(Context context,int colorId){
        return ContextCompat.getColor(context,colorId);
    }

    /**
     * 获取Drawable资源
     * 在某些平台会无法得到建议用传入 Context的获取方式
     */
    public static Drawable getDrawable(int drawAbleId){
        return getDrawable(getContext(),drawAbleId);
    }

    /**
     * 获取Drawable资源
     */
    public static Drawable getDrawable(Context context,int drawAbleId){
        return ContextCompat.getDrawable(context,drawAbleId);
    }

    /**
     * 获取Drawable资源
     * 在某些平台会无法得到建议用传入 Context的获取方式
     */
    public static int getDimen(int dimenId){
        return getDimen(getContext(),dimenId);
    }

    /**
     * 获取Drawable资源
     */
    public static int getDimen(Context context,int dimenId){
        return (int)context.getResources().getDimension(dimenId);
    }

    /**
     * 打印屏幕信息
     */
    public static void log(){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        int screenWidth = dm.widthPixels;
        //                 3          :    4   :     x     :    6    :     8     :   12
        // 屏幕密度（每寸像素120(ldpi)/160(mdpi)/213(tvdpi)/240(hdpi)/320(xhdpi)/480(xxhdpi)）
        LogUtil.e("屏幕参数","*************** 屏幕参数 ****************");
        LogUtil.e("屏幕参数","heightPx : widthPx  = (" + screenHeight + "×" + screenWidth +")");
        LogUtil.e("屏幕参数","dpi ：" + dm.densityDpi);
        LogUtil.e("屏幕参数","dpi ：" + "屏幕密度:             3         4                    6         8           12          16     ");
        LogUtil.e("屏幕参数","dpi ：" + "屏幕密度:每寸像素(120(ldpi)/160(mdpi)/213(tvdpi)/240(hdpi)/320(xhdpi)/480(xxhdpi)/640(xxxhdpi)");
    }
}
