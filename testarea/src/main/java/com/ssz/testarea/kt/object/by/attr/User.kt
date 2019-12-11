package com.ssz.testarea.kt.`object`.by.attr

/**
 * @author : zsp
 * time : 2019 09 2019/9/16 15:39
 * 利用（var / val）map 存储属性，看它的初始化方法
 */
class User(map : Map<String,Any?>) {

    /**
     * key 是 String ，value 是任意类型
     *
     * 这里的成员变量类型 是 value
     */

    val name : String by map
    val age : Int by map
    val gender : String by map
}