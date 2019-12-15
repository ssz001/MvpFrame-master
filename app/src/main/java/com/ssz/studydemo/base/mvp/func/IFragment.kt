package com.ssz.studydemo.base.mvp.func

import android.os.Bundle

interface IFragment {
    fun getLayoutId(): Int
    fun bindPresenter()
    fun afterOnCreateView(savedInstanceState: Bundle?)
    fun setEvent()
}