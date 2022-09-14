package com.chengwei.toolkit4j.core.exception;

/**
 * 服务端异常基类。
 * 注：异常信息不会暴露给客户端，后台记录异常日志后响应统一的异常信息。若需要暴露给客户端请定义客户端异常。
 *
 * @author chengwei
 * @since 2021/12/10
 */
public abstract class ServerException extends TemplatedException {
    public ServerException() {
    }

    public ServerException(String message, Object... args) {
        super(message, args);
    }

    public ServerException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}