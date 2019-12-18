package com.ssz.studydemo.base.ui.view

import android.os.Bundle

interface IFragment {
    fun getLayoutId(): Int
    fun afterOnCreateView(savedInstanceState: Bundle?)
    fun setEvent()
}