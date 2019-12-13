package com.ssz.studydemo.model.remote.net

import com.google.gson.annotations.SerializedName

/**
 * @author : zsp
 * time : 2019 12 2019/12/6 16:00
 */
data class Result<T> (
    var code: Int = -1,
    var message: String,
    @SerializedName(value = "data", alternate = ["result"])
    var data: T
)