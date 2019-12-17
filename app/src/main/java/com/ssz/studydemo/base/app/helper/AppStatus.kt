package com.ssz.studydemo.base.app.helper

import android.support.annotation.StringDef

/**
 * @author : zsp
 * time : 2019 12 2019/12/17 9:32
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@StringDef(AppStatus.KILLED, AppStatus.ALIVE)
annotation class AppStatus {
    companion object{
        const val KILLED = "killed"
        const val ALIVE = "alive"
    }
}