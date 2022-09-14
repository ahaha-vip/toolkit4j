package com.chengwei.toolkit4j.core.exception.client;


import com.chengwei.toolkit4j.core.exception.ClientException;

/**
 * 请求被限制异常。一般由于限流、IP黑名单、重复提交等。
 *
 * @author chengwei
 * @since 2021/12/14
 */
public class AccessLimitationException extends ClientException {

    public AccessLimitationException() {
    }

    public AccessLimitationException(String message, Object... args) {
        super(message, args);
    }

    public AccessLimitationException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public AccessLimitationException(Throwable cause) {
        super(cause);
    }

    public AccessLimitationException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}