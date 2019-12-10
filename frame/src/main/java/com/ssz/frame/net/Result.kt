package com.ssz.frame.net

/**
 * @author : zsp
 * time : 2019 12 2019/12/6 16:00
 */
class Result<T> {
    var code: Int = -1
    var message: String? = null
    var data: T? = null
}