package com.chengwei.toolkit4j.core.exception.client;

import org.apache.shiro.authc.CredentialsException;

/**
 * 错误的凭证异常，继承自shiro的凭据异常类{@link CredentialsException}。
 * <p>
 * 一般发生在客户端调用认证接口，如密码、验证码错误时抛出。
 *
 * @author chengwei
 * @since 2021/12/14
 */
public class BadCredentialsException extends CredentialsException {

    public BadCredentialsException() {
    }

    public BadCredentialsException(String message) {
        super(message);
    }

    public BadCredentialsException(Throwable cause) {
        super(cause);
    }

    public BadCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}