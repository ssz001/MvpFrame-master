package com.ssz.frame.base

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author : zsp
 * time : 2019 09 2019/9/18 15:33
 */
abstract class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
//        ButterKnife.bind(this)
        initView()
        initData()
        setEvent()
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun setEvent()

    /**
     * 以下改动目的：字体大小不随系统字体大小的改变而改变
     * @param newConfig
     */

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (newConfig?.fontScale != 1f) { //fontScale不为1，需要强制设置为1
            resources
        }
    }

    override fun getResources(): Resources {
        val resources = super.getResources()
        if (resources.configuration.fontScale != 1f) { //fontScale不为1，需要强制设置为1
            val newConfig = Configuration()
            newConfig.setToDefaults()//设置成默认值，即fontScale为1
            resources.updateConfiguration(newConfig, resources.displayMetrics)
        }
        return resources
    }

}