package com.ssz.framejava.base.app.helper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ExitAppBroadcast extends BroadcastReceiver {

    public static final String EditAction = "edit_app_action_by_zsp";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context instanceof Activity) {
            Activity activity = (Activity)context;
            activity.unregisterReceiver(this);
            // 必须满足 isTaskRoot
            if (activity.isTaskRoot()){
                activity.finish();
            }
        }
        else {
            throw new IllegalStateException("context must be activity !");
        }
    }
}
