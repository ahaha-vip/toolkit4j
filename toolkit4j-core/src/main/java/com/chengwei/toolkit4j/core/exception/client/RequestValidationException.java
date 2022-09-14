package com.chengwei.toolkit4j.core.exception.client;


import com.chengwei.toolkit4j.core.exception.ClientException;

/**
 * 请求参数异常
 *
 * @author chengwei
 * @since 2022/3/30
 */
public class RequestValidationException extends ClientException {

    public RequestValidationException() {
    }

    public RequestValidationException(String message, Object... args) {
        super(message, args);
    }

    public RequestValidationException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public RequestValidationException(Throwable cause) {
        super(cause);
    }

    public RequestValidationException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}