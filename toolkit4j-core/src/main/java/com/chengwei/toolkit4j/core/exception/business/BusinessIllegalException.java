package com.chengwei.toolkit4j.core.exception.business;


import com.chengwei.toolkit4j.core.exception.BusinessException;

/**
 * 业务上发生了不符合预期的异常
 *
 * @author chengwei
 * @since 2022/3/23
 */
public class BusinessIllegalException extends BusinessException {

    public BusinessIllegalException() {
    }

    public BusinessIllegalException(String message, Object... args) {
        super(message, args);
    }

    public BusinessIllegalException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public BusinessIllegalException(Throwable cause) {
        super(cause);
    }

    public BusinessIllegalException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}