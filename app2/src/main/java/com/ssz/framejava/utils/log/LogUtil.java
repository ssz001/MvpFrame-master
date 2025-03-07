package com.ssz.framejava.utils.log;


/**
 * @author : zsp
 * time : 2019 11 2019/11/4 13:43
 */
public final class LogUtil {

//    private static boolean DEBUG = BuildConfig.DEBUG;
    private static boolean DEBUG = Boolean.parseBoolean("true");

    public static void i(String tag, String msg) {
        if (DEBUG) android.util.Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG) android.util.Log.e(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG) android.util.Log.d(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (DEBUG) android.util.Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (DEBUG) android.util.Log.w(tag, msg);
    }
}
