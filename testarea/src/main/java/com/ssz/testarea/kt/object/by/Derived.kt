package com.ssz.testarea.kt.`object`.by

/**
 * @author : zsp
 * time : 2019 09 2019/9/16 14:10
 * 类的委托即一个类中定义的方法实际是调用另一个类的对象的方法来实现的。
 */

/**
 * 这里的Base 只能为接口 不能为BaseImpl
 *
 * BaseImpl 可以为其中一个实现类，因为多态
 */
class Derived(b : BaseImpl) : Base by b

/**
 * 如果不用 by 必须实现这个接口的方法
 */
class Derived2(b : Base) : Base{
    override fun print() {

    }
}