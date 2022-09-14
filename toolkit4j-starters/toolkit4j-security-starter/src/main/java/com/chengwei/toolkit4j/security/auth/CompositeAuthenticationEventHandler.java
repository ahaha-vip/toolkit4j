package com.chengwei.toolkit4j.security.auth;

import cn.hutool.core.thread.GlobalThreadPool;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

/**
 * 聚合的认证事件处理器
 *
 * @author chengwei
 * @since 2022/1/5
 */
public class CompositeAuthenticationEventHandler implements AuthenticationEventHandler {

    /**
     * 异步执行线程池
     */
    private final Executor executor;

    /**
     * 真正执行业务逻辑的处理器
     */
    private final List<AuthenticationEventHandler> handlers;

    public CompositeAuthenticationEventHandler(Executor executor, List<AuthenticationEventHandler> handlers) {
        this.executor = Optional.ofNullable(executor).orElse(GlobalThreadPool.getExecutor());
        this.handlers = Optional.ofNullable(handlers).orElse(Collections.emptyList());
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    @Override
    public void onSuccess(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        for (AuthenticationEventHandler handler : handlers) {
            if (handler.supports(authenticationToken)) {
                executor.execute(() -> handler.onSuccess(authenticationToken, authenticationInfo));
            }
        }
    }

    @Override
    public void onFailure(AuthenticationToken authenticationToken, AuthenticationException authenticationException) {
        for (AuthenticationEventHandler handler : handlers) {
            if (handler.supports(authenticationToken)) {
                executor.execute(() -> handler.onFailure(authenticationToken, authenticationException));
            }
        }
    }
}