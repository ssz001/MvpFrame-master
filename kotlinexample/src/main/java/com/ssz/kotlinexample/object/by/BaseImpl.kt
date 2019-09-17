package com.ssz.kotlinexample.`object`.by

/**
 * @author : zsp
 * time : 2019 09 2019/9/16 14:05
 */
class BaseImpl(val name : String) : Base{
    override fun print() {
       println(name)
    }
}