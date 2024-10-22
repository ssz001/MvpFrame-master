package com.ssz.framejava.utils.toast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * @author : zsp
 * time : 2019 10 2019/10/12 9:44
 * 解决了android 7.x 设备的Token丢失导致的崩溃问题
 */
public class ToastUtil {

    private static final String TAG = "ToastUtils";
    private static Toast mToast  = null;
    private static Field sField_TN = null;
    private static Field sField_TN_Handler = null;
    private static boolean sIsHookFieldInit = false;
    private static final String FIELD_NAME_TN = "mTN";
    private static final String FIELD_NAME_HANDLER = "mHandler";

    /**
     * Non-blocking showing Toast
     * @param context  context，Application or Activity
     * @param text     the text show on the Toast
     * @param duration Toast.LENGTH_SHORT（default,2s） or Toast.LENGTH_LONG（3.5s）
     */
    public static void showToast(Context context,CharSequence text ,int duration) {
        ToastRunnable toastRunnable = new ToastRunnable(context,
                text, duration,null,-1,0,0);
        if (isOnMainThread()){
            toastRunnable.run();
            return;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity)context;
            if (!activity.isFinishing()) {
                activity.runOnUiThread(toastRunnable);
            }
        } else {
            new Handler(Looper.getMainLooper()).post(toastRunnable);
        }
    }

    /**
     * Non-blocking showing Toast,default duration is Toast.LENGTH_SHORT
     * @param context  context，Application or Activity
     * @param text     the text show on the Toast
     */
    public static void showToast(Context context,CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * cancel the toast
     */
    public static void cancelToast() {
        if (isOnMainThread()){
            mToast.cancel();
        }else {
            new Handler(Looper.getMainLooper()).post(() -> mToast.cancel());
        }
    }

    /**
     * Hook Toast,fix the BadTokenException happened on the device 7.x
     * while showing Toast which will cause your app to crash
     *
     * @param toast
     */
    private static void hookToast(Toast toast) {
        if (!isNeedHook()) {
            return;
        }
        try {
            if (!sIsHookFieldInit) {
                sField_TN = Toast.class.getDeclaredField(FIELD_NAME_TN);
                sField_TN.setAccessible(true);
                sField_TN_Handler = sField_TN.getType().getDeclaredField(FIELD_NAME_HANDLER);
                sField_TN_Handler.setAccessible(true);
                sIsHookFieldInit = true;
            }
            Object tn = sField_TN.get(toast);
            Handler originHandler = (Handler)sField_TN_Handler.get(tn);
            sField_TN_Handler.set(tn,new SafelyHandlerWrapper(originHandler));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是不是主线程
     */
    private static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * Check if Toast need hook，only hook the device 7.x(api = 24/25)
     *
     * @return true for need hook to fit system bug,false for don't need hook
     */
    private static boolean isNeedHook(){
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1 || Build.VERSION.SDK_INT == Build.VERSION_CODES.N;
    }

    private static class ToastRunnable implements Runnable {

        /**
         * Application Context is necessary
         */
        private final Context context;
        private final CharSequence text;
        private final int duration;
        private final View toastView;

        private final int xoffset;
        private final int yoffset;

        private final int gravity;

        private ToastRunnable(Context context,
                              CharSequence text,
                              int duration,
                              View toastView,
                              int gravity,
                              int xoffset,
                              int yoffset){
            this.context = context.getApplicationContext();
            this.text = text;
            this.duration = duration;
            this.toastView = toastView;
            this.xoffset = xoffset;
            this.yoffset = yoffset;
            this.gravity = gravity;
        }

        @SuppressLint("ShowToast")
        @Override
        public void run() {
            if (mToast == null) {
                if (null == toastView){
                    mToast = Toast.makeText(context,text,duration);
                }else {
                    mToast = new Toast(context);
                    mToast.setView(toastView);
                    mToast.setDuration(duration);
                    if (gravity != -1)
                    mToast.setGravity(gravity,xoffset,yoffset);
                }
            } else {
                // 解决多次点击后只有第一次有效的问题
                mToast.cancel();
                if (null == toastView){
                    mToast = Toast.makeText(context,text,duration);
                } else {
                    mToast = new Toast(context);
                    mToast.setView(toastView);
                    mToast.setDuration(duration);
                    if (gravity != -1)
                    mToast.setGravity(gravity,xoffset,yoffset);
                }
            }
            hookToast(mToast);
            mToast.show();
        }
    }

    /**
     * Safe outside Handler class which just warps the system origin handler object in the Toast.class
     */
    private static class SafelyHandlerWrapper extends Handler {

        private final Handler originHandler;

        private SafelyHandlerWrapper(Handler originHandler){
             this.originHandler = originHandler;
        }

        @Override
        public void dispatchMessage(Message msg) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
                Log.e(TAG, "Catch system toast exception:"+e);
            }
        }

        @Override
        public void handleMessage(Message msg){
            originHandler.handleMessage(msg);
        }
    }

    /************************************** Toast Expand *************************************/

    public static void showToast(ToastUtil.Builder builder) {
        final Context context = builder.context;
        ToastRunnable toastRunnable = new ToastRunnable(
                context,
                builder.text,
                builder.duration,
                builder.view,
                builder.gravity,
                builder.xoffset,
                builder.yoffset
        );
        if (isOnMainThread()){
            toastRunnable.run();
            return;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity)context;
            if (!activity.isFinishing()) {
                activity.runOnUiThread(toastRunnable);
            }
        } else {
            new Handler(Looper.getMainLooper()).post(toastRunnable);
        }
    }

    public static final class Builder {

        private int gravity = -1;
        private int duration = Toast.LENGTH_SHORT;
        private View view;
        private Context context;
        private String text = "";
        private int xoffset;
        private int yoffset;

        public Builder context(Context context){
            this.context = context;
            return this;
        }

        public Builder gravity(int gravity){
            this.gravity = gravity;
            return this;
        }

        public Builder xOffset(int xoffset){
            this.xoffset = xoffset;
            return this;
        }

        public Builder yOffset(int yOffset){
            this.yoffset = yOffset;
            return this;
        }

        public Builder view(View view){
            this.view = view;
            return this;
        }

        public Builder text(String text){
            this.text = text;
            return this;
        }

        public Builder duration(int duration){
            this.duration = duration;
            return this;
        }

        public Builder build(){
            return this;
        }

        public void show(){
            ToastUtil.showToast(this);
        }
    }
}
