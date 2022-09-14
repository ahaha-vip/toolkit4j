package com.chengwei.toolkit4j.core.exception.client;


import com.chengwei.toolkit4j.core.exception.ClientException;

/**
 * 文件不存在异常，一般由于客户端访问的文件在服务端不存在。
 *
 * @author chengwei
 * @since 2022/1/5
 */
public class FileNotExistException extends ClientException {

    public FileNotExistException() {
    }

    public FileNotExistException(String message, Object... args) {
        super(message, args);
    }

    public FileNotExistException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public FileNotExistException(Throwable cause) {
        super(cause);
    }

    public FileNotExistException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}