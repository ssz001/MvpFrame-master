package com.ssz.frame.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import java.lang.reflect.Field

/**
 * @author : zsp
 * time : 2019 09 2019/9/19 18:14
 * 解决了Android 7.x 中的Toast 出现Token 失效的问题
 */
object ToastUtils {

    private val TAG = "ToastUtils"
    private var mToast: Toast? = null
    private var sField_TN: Field? = null
    private var sField_TN_Handler: Field? = null
    private var sIsHookFieldInit = false
    private val FIELD_NAME_TN = "mTN"
    private val FIELD_NAME_HANDLER = "mHandler"

    /**
     * Non-blocking showing Toast
     * @param context  context，Application or Activity
     * @param text     the text show on the Toast
     * @param duration Toast.LENGTH_SHORT（default,2s） or Toast.LENGTH_LONG（3.5s）
     */
    fun showToast(context: Context, text: CharSequence, duration: Int) {
        val toastRunnable = ToastRunnable(context, text, duration)
        if (context is Activity) {
            if (!context.isFinishing) {
                context.runOnUiThread(toastRunnable)
            }
        } else {
            val handler = Handler(context.mainLooper)
            handler.post(toastRunnable)
        }
    }

    /**
     * Non-blocking showing Toast,default duration is Toast.LENGTH_SHORT
     * @param context  context，Application or Activity
     * @param text     the text show on the Toast
     */
    fun showToast(context: Context, text: CharSequence) {
        showToast(context, text, Toast.LENGTH_SHORT)
    }

    /**
     * cancel the toast
     */
    fun cancelToast() {
        val looper = Looper.getMainLooper()
        if (looper.thread === Thread.currentThread()) {
            mToast!!.cancel()
        } else {
            Handler(looper).post { mToast!!.cancel() }
        }
    }

    /**
     * Hook Toast,fix the BadTokenException happened on the device 7.x while showing Toast which will cause your app to crash
     *
     * @param toast
     */
    private fun hookToast(toast: Toast?) {
        if (!isNeedHook()) {
            return
        }
        try {
            if (!sIsHookFieldInit) {
                sField_TN = Toast::class.java.getDeclaredField(FIELD_NAME_TN)
                sField_TN!!.isAccessible = true
                sField_TN_Handler = sField_TN!!.type.getDeclaredField(FIELD_NAME_HANDLER)
                sField_TN_Handler!!.isAccessible = true
                sIsHookFieldInit = true
            }
            val tn = sField_TN!!.get(toast)
            val originHandler = sField_TN_Handler!!.get(tn) as Handler
            sField_TN_Handler!!.set(tn, SafelyHandlerWrapper(originHandler))
        } catch (e: Exception) {
            Log.e(TAG, "Hook toast exception=$e")
        }
    }

    /**
     * Check if Toast need hook，only hook the device 7.x(api = 24/25)
     *
     * @return true for need hook to fit system bug,false for don't need hook
     */
    private fun isNeedHook(): Boolean {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1 || Build.VERSION.SDK_INT == Build.VERSION_CODES.N
    }

    private class ToastRunnable(private val context: Context, private val text: CharSequence, private val duration: Int) : Runnable {

        @SuppressLint("ShowToast")
        override fun run() {
            if (mToast == null) {
                mToast = Toast.makeText(context, text, duration)
            } else {
                mToast!!.cancel()
                mToast = Toast.makeText(context, text, duration)
//                mToast!!.setText(text)
//                mToast!!.duration = duration
            }
            hookToast(mToast)
            mToast!!.show()
        }
    }

    /**
     * Safe outside Handler class which just warps the system origin handler object in the Toast.class
     */
    private class SafelyHandlerWrapper(private val originHandler: Handler?) : Handler() {

        override fun dispatchMessage(msg: Message) {
            // The outside hanlder SafelyHandlerWrapper object just catches the Exception while dispatch the message
            // if the the inside system origin hanlder object throw the BadTokenException，the outside safe SafelyHandlerWrapper object
            // just catches the exception here to avoid the app crashing
            try {
                super.dispatchMessage(msg)
            } catch (e: Exception) {
                Log.e(TAG, "Catch system toast exception:$e")
            }

        }

        override fun handleMessage(msg: Message) {
            //just pass the Message to the origin handler object to handle
            originHandler?.handleMessage(msg)
        }
    }

//    private static final String TAG = "ToastUtils";
//    private static Toast mToast  = null;
//    private static Field sField_TN = null;
//    private static Field sField_TN_Handler = null;
//    private static boolean sIsHookFieldInit = false;
//    private static final String FIELD_NAME_TN = "mTN";
//    private static final String FIELD_NAME_HANDLER = "mHandler";
//
//    /**
//     * Non-blocking showing Toast
//     * @param context  context，Application or Activity
//     * @param text     the text show on the Toast
//     * @param duration Toast.LENGTH_SHORT（default,2s） or Toast.LENGTH_LONG（3.5s）
//     */
//    public static void showToast(Context context,CharSequence text ,int duration) {
//        ToastRunnable toastRunnable = new ToastRunnable(context, text, duration);
//        if (isOnMainThread()){
//            toastRunnable.run();
//            return;
//        }
//        if (context instanceof Activity) {
//            Activity activity = (Activity)context;
//            if (!activity.isFinishing()) {
//                activity.runOnUiThread(toastRunnable);
//            }
//        } else {
//            new Handler(context.getMainLooper()).post(toastRunnable);
//        }
//    }
//
//    /**
//     * Non-blocking showing Toast,default duration is Toast.LENGTH_SHORT
//     * @param context  context，Application or Activity
//     * @param text     the text show on the Toast
//     */
//    public static void showToast(Context context,CharSequence text) {
//        showToast(context, text, Toast.LENGTH_SHORT);
//    }
//
//    /**
//     * cancel the toast
//     */
//    public static void cancelToast() {
//        if (isOnMainThread()){
//            mToast.cancel();
//        }else {
//            new Handler(Looper.getMainLooper()).post(() -> mToast.cancel());
//        }
//    }
//
//    /**
//     * Hook Toast,fix the BadTokenException happened on the device 7.x
//     * while showing Toast which will cause your app to crash
//     *
//     * @param toast
//     */
//    private static void hookToast(Toast toast) {
//        if (!isNeedHook()) {
//            return;
//        }
//        try {
//            if (!sIsHookFieldInit) {
//                sField_TN = Toast.class.getDeclaredField(FIELD_NAME_TN);
//                sField_TN.setAccessible(true);
//                sField_TN_Handler = sField_TN.getType().getDeclaredField(FIELD_NAME_HANDLER);
//                sField_TN_Handler.setAccessible(true);
//                sIsHookFieldInit = true;
//            }
//            Object tn = sField_TN.get(toast);
//            Handler originHandler = (Handler)sField_TN_Handler.get(tn);
//            sField_TN_Handler.set(tn,new SafelyHandlerWrapper(originHandler));
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 是不是主线程
//     */
//    public static boolean isOnMainThread() {
//        return Looper.myLooper() == Looper.getMainLooper();
//    }
//
//    /**
//     * Check if Toast need hook，only hook the device 7.x(api = 24/25)
//     *
//     * @return true for need hook to fit system bug,false for don't need hook
//     */
//    private static boolean isNeedHook(){
//        return Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1 || Build.VERSION.SDK_INT == Build.VERSION_CODES.N;
//    }
//
//    private static class ToastRunnable implements Runnable {
//
//        /**
//         * Application Context is necessary
//         */
//        private final Context context;
//        private final CharSequence text;
//        private final int duration;
//
//        private ToastRunnable(Context context,CharSequence text,int duration){
//            this.context = context.getApplicationContext();
//            this.text = text;
//            this.duration = duration;
//        }
//
//        @SuppressLint("ShowToast")
//        @Override
//        public void run() {
//            if (mToast == null) {
//                mToast = Toast.makeText(context, text, duration);
//            } else {
//                // 解决多次点击后只有第一次有效的问题
//                mToast.cancel();
//                mToast = Toast.makeText(context, text, duration);
//
////                mToast.setText(text);
////                mToast.setDuration(duration);
//            }
//            hookToast(mToast);
//            mToast.show();
//        }
//    }
//
//    /**
//     * Safe outside Handler class which just warps the system origin handler object in the Toast.class
//     */
//    private static class SafelyHandlerWrapper extends Handler {
//
//        private final Handler originHandler;
//
//        private SafelyHandlerWrapper(Handler originHandler){
//            this.originHandler = originHandler;
//        }
//
//        @Override
//        public void dispatchMessage(Message msg) {
//            try {
//                super.dispatchMessage(msg);
//            } catch (Exception e) {
//                Log.e(TAG, "Catch system toast exception:"+e);
//            }
//        }
//
//        @Override
//        public void handleMessage(Message msg){
//            originHandler.handleMessage(msg);
//        }
//    }
}