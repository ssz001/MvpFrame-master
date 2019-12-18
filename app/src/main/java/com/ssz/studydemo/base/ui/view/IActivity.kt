package com.ssz.studydemo.base.ui.view

import android.os.Bundle

interface IActivity {
    fun afterOnCreate(savedInstanceState: Bundle?)
    fun getLayoutId(): Int
    fun setEvent()
}