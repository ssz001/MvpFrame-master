package com.ssz.studydemo.app

import com.ssz.studydemo.base.app.helper.AppHelper

/**
 * @author : zsp
 * time : 2019 12 2019/12/17 13:06
 * App 层面的一些标准函数拓展
 */
fun AppHelper.getAppContext():AppContext{
    return getApplication() as AppContext
}
