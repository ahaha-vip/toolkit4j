package com.chengwei.toolkit4j.core.exception.client;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 未认证异常，继承shiro最高级认证异常类{@link AuthenticationException}。
 * <p>
 * 一般发生在客户端请求需要认证的资源，但是携带的令牌失效或无效时抛出。
 *
 * @author chengwei
 * @since 2021/12/14
 */
public class UnauthenticatedException extends AuthenticationException {

    public UnauthenticatedException() {
    }

    public UnauthenticatedException(String message) {
        super(message);
    }

    public UnauthenticatedException(Throwable cause) {
        super(cause);
    }

    public UnauthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }
}