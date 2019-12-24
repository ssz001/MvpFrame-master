package com.ssz.baselibrary.app.helper;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import com.ssz.baselibrary.BuildConfig;
import com.ssz.baselibrary.app.AppContext;
import com.ssz.baselibrary.utils.ObjectHelper;
import com.ssz.baselibrary.utils.ScreenUtil;
import com.ssz.baselibrary.utils.SystemUtil;

import java.util.List;

/**
 * @author zsp
 * create at 2019/12/18 15:07
 * AppHelper
 */
public class AppHelper {

    private static final AppHelper instance = new AppHelper();
    private static Application application;
//    public static final boolean DEBUG = Boolean.parseBoolean("true");
    public static final boolean DEBUG = BuildConfig.DEBUG;

    @AppStatus
    private String appStatus = AppStatus.KILLED;

    private AppHelper(){}

    public static AppHelper init(@NonNull Application application){
        AppHelper.application = application;
        return instance;
    }

    public static AppHelper get(){
        return instance;
    }

    public static Application getApplication(){
        return ObjectHelper.requireNotNull(application,"AppHolder not must be init() first !");
    }

    public static AppContext getAppContext(){
        return ((AppContext) getApplication());
    }

    public void setAppStatus(@AppStatus String status){
        this.appStatus = status;
    }

    @AppStatus
    public String getAppStatus(){
        return appStatus;
    }

    public boolean isKilled(){
        return appStatus.equals(AppStatus.KILLED);
    }

    public AppHelper toLog(){
        if (DEBUG){
            SystemUtil.log();
            ScreenUtil.log();
        }
        return this;
    }

    public static void exitApp(){
        boolean bellowOsFive = Boolean.parseBoolean("true");
        if (bellowOsFive){
            getApplication().sendBroadcast(new Intent(ExitAppBroadcast.EDIT_ACTION));
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                ActivityManager activityManager = (ActivityManager)getApplication().getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();
                for (ActivityManager.AppTask appTask : appTaskList) {
                    appTask.finishAndRemoveTask();
                }
            }
        }
    }

    public void restartApp(){
        final Application application = getApplication();
        Intent intent = application.getPackageManager().getLaunchIntentForPackage(application.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            application.startActivity(intent);
        }
    }
}
