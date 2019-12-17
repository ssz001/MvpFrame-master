package com.ssz.framejava.base.app.helper;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import com.ssz.framejava.app.AppContext;
import com.ssz.framejava.utils.ObjectHelper;
import com.ssz.framejava.utils.ScreenUtil;
import com.ssz.framejava.utils.SystemUtil;

import java.util.List;

public class AppHelper {

    private static final AppHelper instance = new AppHelper();
    private static Application application;
    public static final boolean isDebug = Boolean.parseBoolean("true");

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

    public static AppContext getAppContext(){
        return (AppContext)getApplication();
    }

    public static Application getApplication(){
        return ObjectHelper.requireNotNull(application,"AppHolder not must be init() first !");
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
        SystemUtil.log();
        ScreenUtil.log();
        return this;
    }

    public static void exitApp(){
        boolean flag = Boolean.parseBoolean("true");
        if (flag){
            getApplication().sendBroadcast(new Intent(ExitAppBroadcast.EditAction));
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                ActivityManager activityManager = (ActivityManager)getApplication().getSystemService(Context.ACTIVITY_SERVICE);
                List<android.app.ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();
                for (android.app.ActivityManager.AppTask appTask : appTaskList) {
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
