package com.ssz.studydemo.test.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration

interface IAppLifeCycle {
    fun attachBaseContext(base: Context)
    fun onCreate(application : Application)
    fun onTrimMemory(application : Application)
    fun onConfigurationChanged(newConfig: Configuration?)
    fun onTrimMemory(level: Int)
}