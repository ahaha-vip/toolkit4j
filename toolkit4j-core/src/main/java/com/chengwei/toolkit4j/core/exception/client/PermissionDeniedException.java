package com.chengwei.toolkit4j.core.exception.client;

import org.apache.shiro.authz.AuthorizationException;

/**
 * 权限被拒绝异常，集成shiro最高级授权异常类{@link AuthorizationException}
 * <p>
 * 此时一般是认证成功，但是授权失败。一般发生在客户端尝试访问它没有权限的资源，被后端捕获后抛出。
 *
 * @author chengwei
 * @since 2021/12/14
 */
public class PermissionDeniedException extends AuthorizationException {

    public PermissionDeniedException() {
    }

    public PermissionDeniedException(String message) {
        super(message);
    }

    public PermissionDeniedException(Throwable cause) {
        super(cause);
    }

    public PermissionDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}