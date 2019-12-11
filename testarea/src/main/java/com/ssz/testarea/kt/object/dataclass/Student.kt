package com.ssz.testarea.kt.`object`.dataclass

/**
 * @author : zsp
 * time : 2019 08 2019/8/23 16:59
 * data class 至少一个参数，且构造方法里的才会加到toString里,val 不可以修改，var 可以修改
 */
data class Student(var name : String,var age : Int,var gender :String)