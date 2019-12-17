package com.ssz.studydemo.base.ui.mvp.func

import android.os.Bundle

interface IActivity {
    fun bindPresenter()
    fun afterOnCreate(savedInstanceState: Bundle?)
    fun getLayoutId(): Int
    fun setEvent()
}