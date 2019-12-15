package com.ssz.studydemo.test.activity

import android.os.Bundle

interface IActivityLifeCycle {

    fun onCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onSaveInstanceState(outState: Bundle)

    fun onDestroy()
}