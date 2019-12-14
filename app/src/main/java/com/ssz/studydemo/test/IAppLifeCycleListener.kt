package com.ssz.studydemo.test

import android.app.Application
import android.content.Context

interface IAppLifeCycleListener {
    fun attachBaseContext(base: Context?)
    fun onCreate(application : Application)
    fun onTrimMemory(application : Application)
}