package com.ssz.studydemo.model.remote.net.response

import com.google.gson.annotations.SerializedName

/**
 * @author : zsp
 * time : 2019 12 2019/12/6 16:00
 */
data class Result<T>(
        @SerializedName(value = "code", alternate = ["status"])
        var code: Int = -1,
        @SerializedName(value = "message", alternate = ["msg"])
        var message: String,
        @SerializedName(value = "data", alternate = ["result"])
        var data: T
)