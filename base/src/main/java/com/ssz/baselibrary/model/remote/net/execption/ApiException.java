package com.ssz.baselibrary.model.remote.net.execption;


import com.ssz.baselibrary.model.remote.net.response.ResponseCode;

/**
 * @author zsp
 * create at 2019/1/18 9:41
 * 请求异常统一封装在这个类，提供 code 和 message 属性获取异常信息
 */
public final class ApiException extends Exception {

    /**
     * Json Response 中的 code
     */
    private int code;
    /**
     * Json Response 中的 message
     */
    private String message;

    public ApiException(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    /********************* 安全拓展 ***************************/

    /**
     * 异常转换
     */
    public ApiException(Throwable ex) {
        if (ex instanceof TokenExpiredException){
            this.code = ResponseCode.CERTIFICATE_INVALID;
            this.message = "凭证失效";
        } else if (ex instanceof ApiException){
            ApiException ae = (ApiException)ex;
            this.code = ae.code;
            this.message = ae.message;
        } else {
            this.code = ResponseCode.UNKNOWN;
            this.message = ex.getMessage();
        }
    }

    public static ApiException cast(Throwable throwable){
        return (ApiException)throwable;
    }
}
