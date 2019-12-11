package com.ssz.testarea.kt.`object`.by.attr

import kotlin.reflect.KProperty

/**
 * @author : zsp
 * time : 2019 09 2019/9/16 14:22
 */
class Delegate {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, 这里委托了 ${property.name} 属性"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$thisRef 的 ${property.name} 属性赋值为 $value")
    }

}