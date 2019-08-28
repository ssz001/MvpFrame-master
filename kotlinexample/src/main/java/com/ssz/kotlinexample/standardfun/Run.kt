package com.ssz.kotlinexample.standardfun

import com.ssz.kotlinexample.`object`.dataclass.Student

/**
 * @author : zsp
 * time : 2019 08 2019/8/23 17:00
 *
 * public inline fun <R> run(block: () -> R): R{}
 *
 */
class Run {
    val lamada : (() -> String)? = null
    val student : Student? = null

    fun entry(){
        val str = lamada.run { this }
        println(str)

        val stu = student
        val sr = stu.run {
            this?.age
            this?.name
        }
    }
}