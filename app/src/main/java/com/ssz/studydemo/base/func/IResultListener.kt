package com.ssz.studydemo.base.func

/**
 * @author : zsp
 * time : 2020 01 2020/1/16 15:38
 */
interface IResultListener<T,R> {
    fun result(result : T)
    fun error(error : R)
}