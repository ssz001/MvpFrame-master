package com.ssz.studydemo.base.func

/**
 * @author : zsp
 * time : 2019 12 2019/12/10 16:23
 */
interface ISuccessListener<T> {
    fun result(result:T)
}