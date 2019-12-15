package com.ssz.studydemo.base.dagger.func

import android.os.Bundle

interface IFragment{
    fun getLayoutId():Int
    fun initInject()
    fun setEvent()
    fun afterOnCreateView(savedInstanceState: Bundle?)
}