package com.ssz.testarea.kt.channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

/**
 * @author : zsp
 * time : 2019 09 2019/9/27 14:57
 */
//fun main()  = runBlocking {
//    val channel = Channel<String>()
//    launch {
//        channel.send("")
//    }
//}


//fun main() = runBlocking {
//    val channel = Channel<Int>()
//    launch {
//        for (x in 1..5) channel.send(x * x)
//        channel.close() // 我们结束发送
//    }// 这⾥我们使⽤ `for` 循环来打印所有被接收到的元素（直到通道被关闭）
//    for (y in channel) println(y)
//    println("Done!")
//    produce { send("") }
//}


fun CoroutineScope.produceNumbers() = produce {
    var x = 1
    while (true) send(x++)
    // 在流中开始从 1 ⽣产⽆穷多个整数
}

fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) send(x * x)
}

fun main() = runBlocking {
    // 从 1 开始⽣产整数
     val numbers = produceNumbers()
    // 对整数做平⽅
     val squares = square(numbers)
     for (i in 1..5) println(squares.receive())
    // 打印前 5 个数字
     println("Done!")
    // 我们的操作已经结束了
     coroutineContext.cancelChildren()
    // 取消⼦协程

}