package com.chengwei.toolkit4j.core.exception;

/**
 * 客户端异常基类。
 * 注：异常信息一般会暴露给客户端，因此定义异常信息时需谨慎。
 *
 * @author chengwei
 * @since 2021/12/10
 */
public abstract class ClientException extends TemplatedException {
    public ClientException() {
    }

    public ClientException(String message, Object... args) {
        super(message, args);
    }

    public ClientException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }

    public ClientException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}