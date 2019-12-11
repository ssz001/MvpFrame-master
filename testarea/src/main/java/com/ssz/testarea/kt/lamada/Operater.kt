package com.ssz.testarea.kt.lamada

import android.view.View
import com.ssz.testarea.kt.lamada.iface.IOperate0
import com.ssz.testarea.kt.lamada.iface.IOperate1
import com.ssz.testarea.kt.lamada.iface.IOperate2

/**
 * @author : zsp
 * time : 2019 08 2019/8/23 13:57
 */
class Operater {

//    1. 无参数的情况 ：
//    val/var 变量名 = { 操作的代码 }

//    2. 有参数的情况
//    val/var 变量名 : (参数的类型，参数类型，...) -> 返回值类型 = {参数1，参数2，... -> 操作参数的代码 }

//    可等价于
//    // 此种写法：即表达式的返回值类型会根据操作的代码自推导出来。
//    val/var 变量名 = { 参数1 ： 类型，参数2 : 类型, ... -> 操作参数的代码 }

//    3. lambda表达式作为函数中的参数的时候，这里举一个例子：
//    fun test(a : Int, 参数名 : (参数1 ： 类型，参数2 : 类型, ... ) -> 表达式返回类型){
//        ...
//    }

    val view : View? = null

    fun entry(){
        // 首先 setOnClickListener() 是一个函数
        view?.setOnClickListener({view -> print("lamada")})
        // 拖尾： 如果函数的最后一个参数是函数，它能够移到圆括号外面。
        view?.setOnClickListener(){view -> print("lamada")}
        // 如果函数仅有一个参数，且它是一个函数，则圆括号能删除
        view?.setOnClickListener{view -> print("lamada")}
        // 如果你不用Lambda参数，你可以删除函数的左边部分,
        view?.setOnClickListener{print("lamada")}

        onClickX(View.OnClickListener { v -> {}})

        // kotlin interface

        onClick0(object : IOperate0 {
            override fun operate() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        /**
         *  java interface
         */
        onClick0Java(com.ssz.testarea.kt.lamada.iface.IOperate0Java { })

        onClick1Java(com.ssz.testarea.kt.lamada.iface.IOperate1Java { name ->  })

        onClick2Java(com.ssz.testarea.kt.lamada.iface.IOperate2Java{ name, age -> {} })

        /**
         * lamada 无参数
         */
        onClick0Lamada({  println("lamada")})
        //拖尾
        onClick0Lamada (){ println("lamada") }
        onClick0Lamada { println("lamada") }
        /**
         * lamada 一个参数
         */
        onClick1Lamada({name -> println("name") })
        // 拖尾
        onClick1Lamada(){name -> println("name") }
        onClick1Lamada{name -> println("name") }
        // 只有一个的时候可以省略参数，有it代替
        onClick1Lamada{println("name") };onClick1Lamada{println(it)};onClick1Lamada {}

        /**
         * lamada 两个参数
         */
        onClick2Lamada({name: String, age: Int ->  })
        // 拖尾
        onClick2Lamada(){name: String, age: Int ->  }
        // 不可以省略参数，不能用it代替
        onClick2Lamada { name, age ->  }
    }

    fun onClick0(operate0: IOperate0){

    }

    fun onClick1(operate1: IOperate1){

    }

    fun onClick2(operate2: IOperate2){

    }

    fun onClick0Java(operate0: com.ssz.testarea.kt.lamada.iface.IOperate0Java){

    }

    fun onClick1Java(operate1: com.ssz.testarea.kt.lamada.iface.IOperate1Java){

    }

    fun onClick2Java(operate2: com.ssz.testarea.kt.lamada.iface.IOperate2Java){

    }

    fun onClick0Lamada(operate: ()->Unit){

    }

    fun onClick1Lamada(operate: (String) -> Unit){

    }

    fun onClick2Lamada(operate: (String,Int) -> Unit){

    }

    fun onClickX(listener : View.OnClickListener){

    }
}