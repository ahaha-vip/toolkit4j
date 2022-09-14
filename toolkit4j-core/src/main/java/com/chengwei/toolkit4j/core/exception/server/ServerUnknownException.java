package com.chengwei.toolkit4j.core.exception.server;


import com.chengwei.toolkit4j.core.exception.ServerException;

/**
 * 服务端未知异常
 *
 * @author chengwei
 * @since 2021/12/14
 */
public class ServerUnknownException extends ServerException {

    public ServerUnknownException() {
    }

    public ServerUnknownException(String message, Object... args) {
        super(message, args);
    }

    public ServerUnknownException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ServerUnknownException(Throwable cause) {
        super(cause);
    }

    public ServerUnknownException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}