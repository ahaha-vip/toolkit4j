package com.chengwei.toolkit4j.core.exception.business;


import com.chengwei.toolkit4j.core.exception.BusinessException;

/**
 * 第三方系统发生的异常。这种异常不属于客户端，也不属于后台自身的异常，可友好的告知客户端异常原因。
 *
 * @author chengwei
 * @since 2021/12/15
 */
public class ThirdPartyException extends BusinessException {

    public ThirdPartyException() {
    }

    public ThirdPartyException(String message, Object... args) {
        super(message, args);
    }

    public ThirdPartyException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ThirdPartyException(Throwable cause) {
        super(cause);
    }

    public ThirdPartyException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}