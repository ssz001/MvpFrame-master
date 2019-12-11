package com.ssz.studydemo.app

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.ssz.studydemo.utils.ScreenUtil
import com.ssz.studydemo.utils.SystemUtil

/**
 * @author : zsp
 * time : 2019 12 2019/12/11 11:26
 */
@SuppressLint("StaticFieldLeak")
object Framework {

    private const val toLog = true
    private const val EDIT_ACTION = "edit_app_action_by_zsp"
    private var mContext : Context? = null
    private var listener : (()->Unit)? = null
    @AppStatus
    private var appStatus = AppStatus.KILLED

    fun <T : Application> init(context : T) : Framework {
        mContext = context
        if (toLog){
            SystemUtil.log()
            ScreenUtil.log()
        }
        return this
    }

    fun setAppStatus(@AppStatus status :String){
        appStatus = status
    }

    @AppStatus
    fun getAppStatus() : String = appStatus

    fun isAppKill() : Boolean = (appStatus == AppStatus.KILLED)

    /**
     * 只需要适配5.0及其以上的时候
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun exitApp5() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val activityManager = get().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val appTaskList = activityManager.appTasks
            for (appTask in appTaskList) {
                appTask.finishAndRemoveTask()
            }
        }
        release()
    }

    private fun exitAppB() {
        get().sendBroadcast(Intent(EDIT_ACTION))
    }

    fun exitApp(){
        exitAppB()
//        exitApp5()
    }

    /**
     * 重启，注意 Application 并不会重新初始化
     */
    fun restartApp() {
        val application = get(Application::class.java)
        val intent = application.packageManager
                .getLaunchIntentForPackage(application.packageName)
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            application.startActivity(intent)
        }
    }

    fun get():Context = mContext ?: throw NullPointerException("Framework no init !")

    operator fun <T : Application> get(clz: Class<T>): T {
        return get() as T
    }

    fun registerReleaseListener(block : ()->Unit){
        listener = block
    }

    private fun release(){
        listener?.invoke()
    }

    class ExitAppBroadCast : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (context is Activity){
                context.unregisterReceiver(this)
                context.finish()
                release()
            }
        }
    }
}