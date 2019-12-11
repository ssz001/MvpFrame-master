package com.ssz.testarea.kt.`object`.by.attr

import kotlin.properties.Delegates

/**
 * @author : zsp
 * time : 2019 09 2019/9/16 14:21
 */
class Example {
    var p : String by Delegate()

    /**
     * 相当于一次初始化就行，后面就直接取值第一次赋的值就好了
     * 应该不用set
     */
    val lazy :String by lazy{
        println("lazy 属性初始化一次")
        "lazy"
    }

    /**
     * 参数为初始化化值
     * observable 观察值
     */
    var name : String by Delegates.observable("<no name>"){
        property, oldValue, newValue ->
        println("$oldValue -> $newValue")
    }

    /**
     * 参数为初始化值
     * vetoable 判断是否可以赋值
     */
    var name2 : String by Delegates.vetoable("<no name>") {
        property, oldValue, newValue ->
        newValue.contains("zsp")
    }

    /**
     * 非空
     */
    var name3 : String by Delegates.notNull()

    /**
     * 局部
     */
    fun example(computeFoo: () -> User) {
        val memoizedFoo by lazy(computeFoo)
        // 第一次访问的时候才会被计算，惰性初始化
        if (memoizedFoo.age == 18) {
//            memoizedFoo.doSomething()
        }
    }
}

/**
 * provideDelegate  -- 控制生产代理的过程，其实呢，还是属性委托;
 *
 * 通过定义 provideDelegate 操作符，可以扩展创建属性实现所委托对象的逻辑。
 * 如果 by 右侧所 使⽤的对象将 provideDelegate 定义为成员或扩展函数，那么会调⽤该函数来创建属性委托实例。
 * provideDelegate 的⼀个可能的使⽤场景是在创建属性时（⽽不仅在其 getter 或 setter 中）检查属性⼀致性。
 */

//class ResourceDelegate<T> : ReadOnlyProperty<MyUI, T> {
//    override fun getValue(thisRef: MyUI, property: KProperty<*>): T {
//
//    }
//}
//
//class ResourceLoader<T>(id: ResourceID<T>) {
//    operator fun provideDelegate(thisRef: MyUI, prop: KProperty<*>): ReadOnlyProperty<MyUI, T> {
//        checkProperty(thisRef, prop.name)
//        // 创建委托
//        return ResourceDelegate()
//    }
//
//    private fun checkProperty(thisRef: MyUI, name: String) {
//
//    }
//}
//
//class MyUI {
//    fun <T> bindResource(id: ResourceID<T>): ResourceLoader<T> {
//
//    }
//    val image by bindResource(ResourceID.image_id)
//    val text by bindResource(ResourceID.text_id)
//}

