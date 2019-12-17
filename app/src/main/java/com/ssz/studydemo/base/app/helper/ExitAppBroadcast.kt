package com.ssz.studydemo.base.app.helper

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * @author : zsp
 * time : 2019 12 2019/12/17 9:05
 */
class ExitAppBroadcast : BroadcastReceiver(){
    companion object{
        const val EDIT_ACTION = "edit_app_action_by_zsp"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context is Activity) {
            context.unregisterReceiver(this)
            // 必须满足 isTaskRoot
            if (context.isTaskRoot){
                context.finish()
            }
        }
        else throw IllegalStateException("context must be activity !")
    }
}