package com.chengwei.toolkit4j.core.exception.client;


import com.chengwei.toolkit4j.core.exception.ClientException;

/**
 * 服务未找到异常，语义上同http的404，一般由于请求地址不正确。
 *
 * @author chengwei
 * @since 2022/1/11
 */
public class ServiceNotFoundException extends ClientException {

    public ServiceNotFoundException() {
    }

    public ServiceNotFoundException(String message, Object... args) {
        super(message, args);
    }

    public ServiceNotFoundException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ServiceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ServiceNotFoundException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}