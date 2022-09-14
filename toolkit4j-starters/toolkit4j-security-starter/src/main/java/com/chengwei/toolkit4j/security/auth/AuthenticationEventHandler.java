package com.chengwei.toolkit4j.security.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 认证事件处理器，策略模式。
 *
 * @author chengwei
 * @since 2022/1/5
 */
public interface AuthenticationEventHandler {

    /**
     * 是否支持处理
     *
     * @param authenticationToken 认证凭证
     * @return true-支持处理；false-不支持处理
     */
    boolean supports(AuthenticationToken authenticationToken);

    /**
     * 认证成功时处理
     *
     * @param authenticationToken 认证凭证
     * @param authenticationInfo  认证通过后的主体封装
     */
    void onSuccess(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo);

    /**
     * 认证失败时处理
     *
     * @param authenticationToken     认证凭证
     * @param authenticationException 认证失败异常
     */
    void onFailure(AuthenticationToken authenticationToken, AuthenticationException authenticationException);
}
