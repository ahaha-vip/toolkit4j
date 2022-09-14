package com.chengwei.toolkit4j.core.exception.server;


import com.chengwei.toolkit4j.core.exception.ServerException;

/**
 * 文件服务相关异常。调用文件服务器的上传、下载、删除等接口时发生的异常。
 *
 * @author chengwei
 * @since 2021/12/9
 */
public class BlobStoreException extends ServerException {
    public BlobStoreException() {
    }

    public BlobStoreException(String message, Object... args) {
        super(message, args);
    }

    public BlobStoreException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public BlobStoreException(Throwable cause) {
        super(cause);
    }

    public BlobStoreException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}