package com.chengwei.toolkit4j.core.exception.client;

import org.apache.shiro.authc.AccountException;

/**
 * 账号异常，一般发生在认证主体被限制或者处于一些其他的异常状态。
 *
 * @author chengwei
 * @since 2021/12/22
 */
public class IllegalAccountException extends AccountException {

    public IllegalAccountException() {
    }

    public IllegalAccountException(String message) {
        super(message);
    }

    public IllegalAccountException(Throwable cause) {
        super(cause);
    }

    public IllegalAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}