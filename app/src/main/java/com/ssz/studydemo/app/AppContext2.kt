package com.ssz.studydemo.app

import android.app.Application

class AppContext2 : Application() {

    companion object{
       private lateinit var mApplication : AppContext2
       fun getInstance() = mApplication
    }

    override fun onCreate() {
        super.onCreate()
        mApplication = this
        Framework.init(this)
    }
}