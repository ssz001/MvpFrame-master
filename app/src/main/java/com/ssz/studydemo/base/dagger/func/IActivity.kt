package com.ssz.studydemo.base.dagger.func

import android.os.Bundle

interface IActivity {
    fun getLayoutId():Int
    fun initInject()
    fun setEvent()
    fun afterOnCreate(savedInstanceState: Bundle?)
}