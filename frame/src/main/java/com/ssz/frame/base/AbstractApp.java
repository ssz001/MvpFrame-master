package com.ssz.frame.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.ssz.frame.BuildConfig;
import com.ssz.frame.utils.LogUtils;

/**
 * @author : zsp
 * time : 2019 08 2019/8/23 9:56
 */
public abstract class AbstractApp extends Application implements Application.ActivityLifecycleCallbacks {

    public static final String TAG = "AbstractApp";
    /**
     * 屏幕宽高参数
     */
    public static int screenHeight;
    public static int screenWidth;
    /**
     * 全局context的静态引用
     */
    public static AbstractApp instance;

    /**
     * Application中在onCreate()方法里去初始化各种全局的变量数据是一种比较推荐的做法，
     * 但是如果你想把初始化的时间点提前到极致，也可以去重写attachBaseContext()方法，
     */
    @Override
    protected void attachBaseContext(Context base) {
        // 在这里调用Context的方法会崩溃
        super.attachBaseContext(base);
        // 在这里可以正常调用Context的方法
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getScreenMetrics();
        instance = this;
//      from  API 14 每一个Activity的生命周期都会回调到这里的对应方法,只需要在这里注册
        registerActivityLifecycleCallbacks(this);

        Log.d("BuildConfig","是否打印日志：" + BuildConfig.DEBUG);
        LogUtils.d(TAG, "screen width = " + screenWidth + " screen height = " + screenHeight);
    }

    /**
     * onTerminate：当终止应用程序对象时调用，不保证一定被调用，当程序是被内核终止以便为其他应用程序释放资源，
     * 那么将不会提醒，并且不调用应用程序的对象的onTerminate方法而直接终止进程。
     * (- 真机情况下不会被调用 -)
     * ( 模拟器情况下调用 ?)
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG,"onTerminate()");
    }

    /**
     * 获取屏幕宽高 参数
     */
    public void getScreenMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        //                 3          :    4   :     x     :    6    :     8     :   12
        // 屏幕密度（每寸像素120(ldpi)/160(mdpi)/213(tvdpi)/240(hdpi)/320(xhdpi)/480(xxhdpi)）
        Log.d("dpi","设备的DPI = " + dm.densityDpi);
    }

    /**********************区别***********************/
    /**
     * 1，OnLowMemory被回调时，已经没有后台进程；而onTrimMemory被回调时，还有后台进程。
     * 2，OnLowMemory是在最后一个后台进程被杀时调用，一般情况是low memory killer 杀进程后触发；
     * 而OnTrimMemory的触发更频繁，每次计算进程优先级时，只要满足条件，都会触发。
     * 3，通过一键清理后，OnLowMemory不会被触发，而OnTrimMemory会被触发一次。
     */

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory()");
    }

    /**
     * Android 4.0之后提供的API
     * TRIM_MEMORY_COMPLETE：内存不足，并且该进程在后台进程列表最后一个，马上就要被清理
     * TRIM_MEMORY_MODERATE：内存不足，并且该进程在后台进程列表的中部。
     * TRIM_MEMORY_BACKGROUND：内存不足，并且该进程是后台进程。
     * TRIM_MEMORY_UI_HIDDEN：内存不足，并且该进程的UI已经不可见了。
     * 以上4个是4.0增加
     * TRIM_MEMORY_RUNNING_CRITICAL：内存不足(后台进程不足3个)，并且该进程优先级比较高，需要清理内存
     * TRIM_MEMORY_RUNNING_LOW：内存不足(后台进程不足5个)，并且该进程优先级比较高，需要清理内存
     * TRIM_MEMORY_RUNNING_MODERATE：内存不足(后台进程超过5个)，并且该进程优先级比较高，需要清理内存
     * 以上3个是4.1增加
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            // from 4.0
            case TRIM_MEMORY_COMPLETE:
                Log.d(TAG, "onTrimMemory(TRIM_MEMORY_COMPLETE)");
                break;
            case TRIM_MEMORY_MODERATE:
                Log.d(TAG, "onTrimMemory(TRIM_MEMORY_MODERATE)");
                break;
            case TRIM_MEMORY_BACKGROUND:
                Log.d(TAG, "onTrimMemory(TRIM_MEMORY_BACKGROUND)");
                break;
            case TRIM_MEMORY_UI_HIDDEN:
                Log.d(TAG, "onTrimMemory(TRIM_MEMORY_UI_HIDDEN)");
                break;
            //from 4.1
            case TRIM_MEMORY_RUNNING_CRITICAL:
                Log.d(TAG, "onTrimMemory(TRIM_MEMORY_RUNNING_CRITICAL)");
                break;
            case TRIM_MEMORY_RUNNING_LOW:
                Log.d(TAG, "onTrimMemory(TRIM_MEMORY_RUNNING_LOW)");
                break;
            case TRIM_MEMORY_RUNNING_MODERATE:
                Log.d(TAG, "onTrimMemory(TRIM_MEMORY_RUNNING_MODERATE)");
                break;
            default:
                break;
        }
    }

    /**
     * 以下文字针对activity：（系统文字变化等）
     * 当系统的配置信息发生改变时，系统会调用此方法。
     * 注意，只有在配置文件 AndroidManifest 中处理了 configChanges属性 对应的设备配置，该方法才会被调用。
     * 如果发生设备配置与在配置文件中设置的不一致，则Activity会被销毁并使用新的配置重建。
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 注册 app内 所有 Activity 生命周期监听
     */

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG+"LifeCycle",activity.getClass().getSimpleName()+" - onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG+"LifeCycle",activity.getClass().getSimpleName()+" - onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG+"LifeCycle",activity.getClass().getSimpleName()+" - onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(TAG+"LifeCycle",activity.getClass().getSimpleName()+" - onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d(TAG+"LifeCycle",activity.getClass().getSimpleName()+"- onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.d(TAG+"LifeCycle",activity.getClass().getSimpleName()+" - onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG+"LifeCycle",activity.getClass().getSimpleName()+" - onActivityDestroyed");
    }
}
