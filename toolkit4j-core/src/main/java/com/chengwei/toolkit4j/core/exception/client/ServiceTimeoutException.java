package com.chengwei.toolkit4j.core.exception.client;


import com.chengwei.toolkit4j.core.exception.ClientException;

/**
 * 服务超时异常
 *
 * @author chengwei
 * @since 2022/1/11
 */
public class ServiceTimeoutException extends ClientException {

    public ServiceTimeoutException() {
    }

    public ServiceTimeoutException(String message, Object... args) {
        super(message, args);
    }

    public ServiceTimeoutException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ServiceTimeoutException(Throwable cause) {
        super(cause);
    }

    public ServiceTimeoutException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}