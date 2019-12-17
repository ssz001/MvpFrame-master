package com.ssz.studydemo.base.app.helper

import android.app.Application
import android.content.Intent
import com.ssz.studydemo.base.app.helper.ExitAppBroadcast.Companion.EDIT_ACTION
import com.ssz.studydemo.utils.ScreenUtil
import com.ssz.studydemo.utils.SystemUtil

/**
 * @author : zsp
 * time : 2019 12 2019/12/17 9:41
 * 静态持有 Application 类对象，然后全局再使用
 */
object AppHelper {

    /**
     * debug 模式
     */
    const val isDebug = true

    /**
     * 静态持有Application instance
     */
    private var mContext : Application? = null
    fun init(context : Application):AppHelper{
        mContext = context
        return this
    }

    fun getApplication() = mContext ?: throw NullPointerException("AppHolder not must be init() first !")
//    fun getAppContext() = getApplication() as AppContext

    @AppStatus
    private var appStatus :String = AppStatus.KILLED
    fun setAppStatus(@AppStatus status : String){
        this.appStatus = status
    }
    fun getAppStatus() = appStatus
    fun isKilled() = appStatus == AppStatus.KILLED

    fun toLog():AppHelper{
        SystemUtil.log()
        ScreenUtil.log()
        return this
    }

    fun exitApp(){
        getApplication().sendBroadcast(Intent(EDIT_ACTION))

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val activityManager = getApplication().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//            val appTaskList = activityManager.appTasks
//            for (appTask in appTaskList) {
//                appTask.finishAndRemoveTask()
//            }
//        }
    }

    fun restartApp(){
        val application = getApplication()
        val intent = application.packageManager
                .getLaunchIntentForPackage(application.packageName)
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            application.startActivity(intent)
        }
    }
}