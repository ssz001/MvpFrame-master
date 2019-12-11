package com.ssz.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.NonNull;

import com.ssz.utils.log.LogUtils;
import com.ssz.utils.ObjectHelper;
import com.ssz.utils.ScreenUtil;
import com.ssz.utils.SystemUtil;

import java.util.List;

/**
 * @author : zsp
 * time : 2019 11 2019/11/7 12:59
 */
@SuppressLint("StaticFieldLeak")
public final class Framework {

    private static final boolean TO_LOG = Boolean.parseBoolean("true");
    private static final String EDIT_ACTION = "edit_app_action_build_by_zsp";
    private static Context context;
    private static Framework mFramework = new Framework();
    private IReleaseListener mReleaseListener;

    // app的生命状态
    private @AppStatus
    String appStatus =  AppStatus.KILLED;
    private Class<Object> type;

    private Framework() {

    }

    public static Framework init(Context context) {
        Framework.context = context;
        log();
        return mFramework;
    }

    public static Context get() {
        return ObjectHelper.requireNotNull(context, "Framework.context not init !");
    }

    /**
     * 获取本项目ApplicationContext对象，类型转换
     */
    public static <T extends Application> T get(Class<T> clz) {
        return (T) get();
    }

    private static void log() {
        if (TO_LOG) {
            SystemUtil.log();
            ScreenUtil.log();
        }
    }

    /**
     * 重启，注意 Application 并不会重新初始化
     */
    public static void restart() {
        final Application application = get(Application.class);
        final Intent intent = application.getPackageManager()
                .getLaunchIntentForPackage(application.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            application.startActivity(intent);
        }
    }

    /**
     * 只需要适配5.0及其以上的时候
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void exitApp5() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager activityManager = (ActivityManager) get().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();
            for (ActivityManager.AppTask appTask : appTaskList) {
                appTask.finishAndRemoveTask();
            }
        }
        releaseFrameWork();
    }

    private static void exitAppB() {
        get().sendBroadcast(new Intent(EDIT_ACTION));
    }

    public static void exitApp() {
        // 使用广播方式,配套registerAppExitActivity()使用
        exitAppB();
        // 只用适配android 5.0 以上的时候，但是不需要配合registerAppExitActivity()使用
//        exitApp5();
    }

    private static void releaseFrameWork() {
        if (null != mFramework.mReleaseListener) mFramework.mReleaseListener.release();
    }

    public static void setAppStatus(final @AppStatus String status) {
        mFramework.appStatus = status;
    }

    public static @AppStatus
    String getAppStatus() {
        return mFramework.appStatus;
    }

    public static boolean isAppKilled() {
        return mFramework.appStatus == AppStatus.KILLED;
    }

    public Framework registerReleaseListener(IReleaseListener mReleaseListener) {
        this.mReleaseListener = mReleaseListener;
        return this;
    }

    public static <T extends Activity> void registerAppExitActivity(final @NonNull T singleTask) {
        LogUtils.INSTANCE.d("registerAppExit", "栈底Activity ？ = " + singleTask.isTaskRoot());
        singleTask.registerReceiver(new ExitAppBroadCast(), new IntentFilter(EDIT_ACTION));
    }

    /**
     * @author : zsp
     * time : 2019 11 2019/11/7 15:14
     * 适用于: 主Activity 满足 该Activity 在栈底位置且启动模式为 SingleTask
     */
    static final class ExitAppBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                activity.unregisterReceiver(this);
                activity.finish();
                releaseFrameWork();
            }
        }
    }

    public interface IReleaseListener {
        void release();
    }
}
