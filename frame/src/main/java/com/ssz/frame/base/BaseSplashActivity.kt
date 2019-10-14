package com.ssz.frame.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author : zsp
 * time : 2019 10 2019/10/12 13:51
 */
class BaseSplashActivity : AppCompatActivity(){

     override fun onCreate(savedInstanceState: Bundle?) {
        aVoidDoubleEnter()
        super.onCreate(savedInstanceState)
    }

    /**
     * 避免从桌面启动程序后，会重新实例化入口类
     */
    private fun aVoidDoubleEnter() {
        if(!isTaskRoot){
            if (null != intent
                    && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
                    && Intent.ACTION_MAIN === intent.action){
                finish()
            }
        }
    }
}