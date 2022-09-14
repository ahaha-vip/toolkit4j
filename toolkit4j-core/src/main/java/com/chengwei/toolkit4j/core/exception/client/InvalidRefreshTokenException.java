package com.chengwei.toolkit4j.core.exception.client;


import com.chengwei.toolkit4j.core.exception.ClientException;

/**
 * 无效的刷新令牌异常
 *
 * @author chengwei
 * @since 2022/2/23
 */
public class InvalidRefreshTokenException extends ClientException {

    public InvalidRefreshTokenException() {
    }

    public InvalidRefreshTokenException(String message, Object... args) {
        super(message, args);
    }

    public InvalidRefreshTokenException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public InvalidRefreshTokenException(Throwable cause) {
        super(cause);
    }

    public InvalidRefreshTokenException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}