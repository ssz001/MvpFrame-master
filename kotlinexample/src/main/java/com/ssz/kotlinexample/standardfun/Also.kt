package com.ssz.kotlinexample.standardfun

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

//val original = "abc"
//// Evolve the value and send to the next chain
//original.let {
//    println("The original String is $it") // "abc"
//    it.reversed() // evolve it as parameter to send to next let
//}.let {
//    println("The reverse String is $it") // "cba"
//    it.length  // can be evolve to other type
//}.let {
//    println("The length of the String is $it") // 3
//}
/**
 * 因为 also -> return this ,所以also “永远”都是 和 源 一样的。
 */
//// Wrong
//// Same value is sent in the chain (printed answer is wrong)
//original.also {
//    println("The original String is $it") // "abc"
//    it.reversed() // even if we evolve it, it is useless
//}.also {
//    println("The reverse String is ${it}") // "abc"
//    it.length  // even if we evolve it, it is useless
//}.also {
//    println("The length of the String is ${it}") // "abc"
//}

//// Corrected for also (i.e. manipulate as original string
//// Same value is sent in the chain
//original.also {
//    println("The original String is $it") // "abc"
//}.also {
//    println("The reverse String is ${it.reversed()}") // "cba"
//}.also {
//    println("The length of the String is ${it.length}") // 3
//}