package com.chengwei.toolkit4j.core.exception.server;


import com.chengwei.toolkit4j.core.exception.ServerException;

/**
 * 服务端不合法的异常
 *
 * @author chengwei
 * @since 2021/12/22
 */
public class ServerIllegalException extends ServerException {

    public ServerIllegalException() {
    }

    public ServerIllegalException(String message, Object... args) {
        super(message, args);
    }

    public ServerIllegalException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ServerIllegalException(Throwable cause) {
        super(cause);
    }

    public ServerIllegalException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}