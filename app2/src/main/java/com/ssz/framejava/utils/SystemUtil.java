package com.ssz.framejava.utils;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.ssz.framejava.utils.log.LogUtil;

import java.util.Locale;

/**
 * @author : zsp
 * time : 2019 11 2019/11/7 15:36
 */
public class SystemUtil {

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

    public static void log(){
            String TAG = "系统参数：";
            LogUtil.e(TAG, "*********** 系统参数 ***********");
            LogUtil.e(TAG, "手机厂商：" + SystemUtil.getDeviceBrand());
            LogUtil.e(TAG, "手机型号：" + SystemUtil.getSystemModel());
            LogUtil.e(TAG, "手机当前系统语言：" + SystemUtil.getSystemLanguage());
            LogUtil.e(TAG, "Android系统版本号：" + SystemUtil.getSystemVersion());
//            LogUtil.e(TAG, "手机IMEI：" + SystemUtil.getIMEI(Framework.get0()));
    }
}
