package com.chengwei.toolkit4j.core.exception.client;


import com.chengwei.toolkit4j.core.exception.ClientException;

/**
 * 一个通用的客户端异常，异常信息会直接暴露给客户端。
 * 如果不想过于细化区分异常类型，只要是客户端的问题都可抛出此异常，主要方便后台开发使用。
 *
 * @author chengwei
 * @since 2021/12/15
 */
public class ClientIllegalException extends ClientException {

    public ClientIllegalException() {
    }

    public ClientIllegalException(String message, Object... args) {
        super(message, args);
    }

    public ClientIllegalException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ClientIllegalException(Throwable cause) {
        super(cause);
    }

    public ClientIllegalException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}