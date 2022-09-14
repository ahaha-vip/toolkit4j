package com.chengwei.toolkit4j.core.exception.client;


import com.chengwei.toolkit4j.core.exception.ClientException;

/**
 * 服务不可用异常，一般在服务未启动或者服务重启时发生，版本迭代时可能会出现，暂且视为客户端异常。
 *
 * @author chengwei
 * @since 2022/1/11
 */
public class ServiceUnavailableException extends ClientException {

    public ServiceUnavailableException() {
    }

    public ServiceUnavailableException(String message, Object... args) {
        super(message, args);
    }

    public ServiceUnavailableException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ServiceUnavailableException(Throwable cause) {
        super(cause);
    }

    public ServiceUnavailableException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}