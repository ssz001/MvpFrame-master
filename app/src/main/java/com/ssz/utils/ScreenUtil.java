package com.ssz.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.ssz.base.BaseApp;
import com.ssz.base.Framework;
import com.ssz.utils.log.LogUtils;

import java.lang.reflect.Method;


/**
 * @author zsp
 * create at 2019/1/22 15:14
 * 关于界面的操作工具类
 */
public class ScreenUtil {

    public static int dpToPx(int dp){
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,metrics);
    }

    public static int pxToDp(int px){
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) (px / metrics.density);
    }

    public static int spToPx(int sp){
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,metrics);
    }

    public static int pxToSp(int px){
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) (px / metrics.scaledDensity);
    }

    public static DisplayMetrics getDisplayMetrics(){
        return BaseApp.instance
                .getResources()
                .getDisplayMetrics();
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
        return BaseApp.instance.getResources().getString(stringId);
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
        return BaseApp.instance.getResources().getColor(colorId);
    }

    /**
     * 获取颜色资源
     */
    public static int getColor(Context context,int colorId){
        return context.getResources().getColor(colorId);
    }

    /**
     * 获取Drawable资源
     * 在某些平台会无法得到建议用传入 Context的获取方式
     */
    public static Drawable getDrawable(int drawAbleId){
        return BaseApp.instance.getResources().getDrawable(drawAbleId);
    }

    /**
     * 获取Drawable资源
     */
    public static Drawable getDrawable(Context context,int drawAbleId){
        return context.getResources().getDrawable(drawAbleId);
    }

    /**
     * 获取Dimen资源
     * 在某些平台会无法得到建议用传入 Context的获取方式
     */
    public static int getDimen(int dimenId){
        return (int) BaseApp.instance.getResources().getDimension(dimenId);
    }

    /**
     * 获取Dimen资源
     */
    public static int getDimen(Context context,int dimenId){
        return (int)context.getResources().getDimension(dimenId);
    }

    /**
     * 获取导航栏的高度
     * @return
     */
    public static int getStatusBarHeight(){
        Resources resources = BaseApp.instance.getResources();
        int resourceId = resources.getIdentifier("status_bar_height","dimen","android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 获取虚拟按键的高度
     * @return
     */
    public static int getNavigationBarHeight() {
        int navigationBarHeight = 0;
        Resources rs = BaseApp.instance.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && hasNavigationBar()) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

    /**
     * 是否存在虚拟按键
     * @return
     */
    private static boolean hasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = BaseApp.instance.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }

    private static Context getContext(){
        return Framework.get();
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
        LogUtils.INSTANCE.e("屏幕参数","*************** 屏幕参数 ****************");
        LogUtils.INSTANCE.e("屏幕参数","heightPx : widthPx  = (" + screenHeight + "×" + screenWidth +")");
        LogUtils.INSTANCE.e("屏幕参数","dpi ：" + dm.densityDpi);
        LogUtils.INSTANCE.e("屏幕参数","dpi ：" + "屏幕密度:             3         4                    6         8           12          16     ");
        LogUtils.INSTANCE.e("屏幕参数","dpi ：" + "屏幕密度:每寸像素(120(ldpi)/160(mdpi)/213(tvdpi)/240(hdpi)/320(xhdpi)/480(xxhdpi)/640(xxxhdpi)");
    }
}
