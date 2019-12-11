package com.ssz.studydemo.app

import android.support.annotation.StringDef

/**
 * @author : zsp
 * time : 2019 12 2019/12/11 13:20
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@StringDef(AppStatus.KILLED, AppStatus.ALIVE)
annotation class AppStatus {
    companion object{
       const val KILLED = "killed"
       const val ALIVE = "alive"
    }
}