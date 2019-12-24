package com.ssz.baselibrary.app.helper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ssz.baselibrary.utils.toast.ToastUtil;

/**
 * @author zsp
 * create at 2019/12/18 15:02
 * 一种App退出方式
 */
public class ExitAppBroadcast extends BroadcastReceiver {

    public static final String EDIT_ACTION = "edit_app_action_by_zsp";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context instanceof Activity) {
            Activity activity = (Activity)context;
            activity.unregisterReceiver(this);
            // 必须满足 isTaskRoot
            if (activity.isTaskRoot()){
                activity.finish();
            }else {
                ToastUtil.showToast(AppHelper.getApplication(),"isTaskRoot == false");
            }
        }
        else
            throw new IllegalStateException("context must be activity !");
    }
}
