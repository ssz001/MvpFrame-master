package com.ssz.studydemo.test.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityDelegate
@Inject constructor(): Application.ActivityLifecycleCallbacks{


    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity?) {

    }

    override fun onActivityResumed(activity: Activity?) {

    }

    override fun onActivityPaused(activity: Activity?) {

    }

    override fun onActivityDestroyed(activity: Activity?) {

    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

    override fun onActivityStopped(activity: Activity?) {

    }


}