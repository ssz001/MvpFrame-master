package com.ssz.framejava.model.remote.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * @author zsp
 * create at 2019/1/18 14:45
 * Response 脱去 code 和 message
 */
public final class Result<T> {

    @SerializedName(value = "code",alternate = "status")
    private int code;
    @SerializedName(value = "message",alternate = "msg")
    private String message;
    @SerializedName(value = "data",alternate = "result")
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
