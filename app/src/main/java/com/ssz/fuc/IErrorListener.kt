package com.ssz.fuc

/**
 * @author : zsp
 * time : 2019 12 2019/12/10 16:23
 */
interface IErrorListener<T> {
    fun error(e : T)
}