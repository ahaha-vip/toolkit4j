package com.chengwei.toolkit4j.core.exception.client;


import com.chengwei.toolkit4j.core.exception.ClientException;

/**
 * 尝试获取锁异常。一般发生在获取锁超时，或者获取锁失败等。
 *
 * @author chengwei
 * @since 2021/12/10
 */
public class OperationConflictException extends ClientException {
    public OperationConflictException() {
    }

    public OperationConflictException(String message, Object... args) {
        super(message, args);
    }

    public OperationConflictException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public OperationConflictException(Throwable cause) {
        super(cause);
    }

    public OperationConflictException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}