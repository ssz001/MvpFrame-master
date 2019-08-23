package com.ssz.kotlinexample.lamada


/**
 * @author : zsp
 * time : 2019 08 2019/8/7 19:11
 */


fun main(args: Array<String>) {


}




/**
 * 无参数的情况
 */
val onClick  = { println("无参数的情况") }
fun onClick(){
    println("无参数的情况")
}

/**
 * 有参数的情况
 */
fun onClick1(a : Int , b : Int) : Int{
    return a + b
}
val onClick1 : (Int , Int) -> Int = {a , b -> a + b}
val onClick2 = {a : Int , b : Int -> a + b}

/**
 * 匿名对象作为接口的情况
 */
fun test(a : Int, com :(k : Int,j :Int) -> Int){

}

//匿名接口
val onClick4 : ((i1 : Int,i2 : Int) -> String )? = null















//val onClick :  (Int,Int) -> Int = {i1: Int, i2: Int -> i1+i2}
//
//val onClickx :  (Int,Int) -> Unit = {i1: Int, i2: Int -> i1+i2}
//
//// 等价于
//val onClick2 = {i1 : Int,i2 : Int -> i1 + i2}
//
//val onClick3 = {println("onClick2")}
//
//val onClick4 : ((i1 : Int,i2 : Int) -> String )? = null
//
//val sum = { x: Int, y: Int -> x + y }
//
//fun test(a : Int, com :(k : Int,j :Int) -> Int){
