package com.ssz.studydemo.app

import com.ssz.frame.base.AbstractApp

/**
 * @author : zsp
 * time : 2019 09 2019/9/18 15:44
 */
class AppContext : AbstractApp(){

    companion object {
       fun getInstance() : AppContext{
           return AbstractApp.instance as AppContext
       }
    }

    override fun onCreate() {
        super.onCreate()
    }
}