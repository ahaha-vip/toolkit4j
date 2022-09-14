package com.chengwei.toolkit4j.security.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

/**
 * {@link ModularRealmAuthenticator}认证失败时的处理策略
 *
 * @author chengwei
 * @since 2021/12/10
 */
public class ModularAuthenticationStrategy extends AtLeastOneSuccessfulStrategy {

    @Override
    public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken authenticationToken, AuthenticationInfo singleRealmInfo, AuthenticationInfo aggregateInfo, Throwable t) {
        // 如果是认证异常，直接向上抛出
        if (t instanceof AuthenticationException) {
            throw (AuthenticationException) t;
        }
        // 如果非认证异常，则包装后向上抛出
        if (t != null) {
            throw new AuthenticationException(t);
        }
        // 如果没有异常则使用默认实现
        return super.afterAttempt(realm, authenticationToken, singleRealmInfo, aggregateInfo, null);
    }
}