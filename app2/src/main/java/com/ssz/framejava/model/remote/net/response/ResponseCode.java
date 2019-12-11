package com.ssz.framejava.model.remote.net.response;

/**
 * @author : zsp
 * time : 2019 11 2019/11/4 9:54
 */
public final class ResponseCode {

    /************************  local errorCode  ********************************/
    /**  未知错误 */
    public static final int UNKNOWN = 1000;
    /**  解析错误 */
    public static final int PARSE_ERROR = 1001;
    /**  连接出错 */
    public static final int NETWORK_ERROR = 1002;
    /**  连接超时 */
    public static final int NETWORK_TIMEOUT = 1003;
    /**  HTTP 异常 */
    public static final int HTTP_ERROR = 1004;

    /************************  remote statusCode  ********************************/

    /**
     * 请求成功
     */
    public static final int SUCCESS = 200;

    /**
     * 凭证失效
     */
    public static final int CERTIFICATE_INVALID = 401;

    /**
     *
     */
    public static final int CODE_500 = 500;
}
