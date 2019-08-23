package com.ssz.kotlinexample.standardfun

import android.util.Log
import com.ssz.kotlinexample.`object`.dataclass.Student

/**
 * @author : zsp
 * time : 2019 08 2019/8/23 16:41
 * 链式编程专用
 */
class Also {

    fun entry(){
        val student = Student("default",0,"default")
                .also { it.name = "zsp" }.also { it.age = 18 }.also { it.gender = "男" }
        println(student)
    }
}