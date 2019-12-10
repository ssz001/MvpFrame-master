package com.ssz.studydemo.app

import com.ssz.frame.base.BaseApp
import com.ssz.frame.utils.network.NetworkManager

/**
 * @author : zsp
 * time : 2019 09 2019/9/18 15:44
 */
class AppContext : BaseApp(){

    companion object {
       fun getInstance() : AppContext{
           return instance as AppContext
       }
    }

    override fun onCreate() {
        super.onCreate()
        // 网络状态监听框架注册
        NetworkManager.getDefault().init(this)
    }
}