package com.chengwei.toolkit4j.security.auth;

import com.chengwei.toolkit4j.security.ShiroAutoConfiguration;
import com.chengwei.toolkit4j.security.ShiroConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AbstractAuthenticator;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 认证、授权相关的事件监听器。由事件处理器异步处理。
 * <p>
 * 初始化过程见{@link ShiroAutoConfiguration#securityManager(List, List)}
 *
 * @author chengwei
 * @since 2021/12/23
 */
@Slf4j
@Component
public class AuthenticationListener implements org.apache.shiro.authc.AuthenticationListener {

    /**
     * 事件执行器
     */
    private final AuthenticationEventHandler authenticationEventHandler;

    public AuthenticationListener(@Autowired(required = false) @Qualifier(ShiroConstants.AUTHENTICATION_EVENT_HANDLER_THREAD_POOL) Executor executor,
                                  @Autowired(required = false) List<AuthenticationEventHandler> handlers) {
        this.authenticationEventHandler = new CompositeAuthenticationEventHandler(executor, handlers);
    }

    /**
     * Realm认证成功后执行
     *
     * @see AbstractAuthenticator#authenticate(AuthenticationToken)
     */
    @Override
    public void onSuccess(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        authenticationEventHandler.onSuccess(authenticationToken, authenticationInfo);
    }

    /**
     * Realm认证失败后执行
     *
     * @see AbstractAuthenticator#authenticate(AuthenticationToken)
     */
    @Override
    public void onFailure(AuthenticationToken authenticationToken, AuthenticationException authenticationException) {
        authenticationEventHandler.onFailure(authenticationToken, authenticationException);
    }

    /**
     * Realm登出前执行，这里不执行任何逻辑。登出逻辑由Realm处理。
     *
     * @see ModularRealmAuthenticator#onLogout(PrincipalCollection)
     */
    @Override
    public void onLogout(PrincipalCollection principals) {
        // noop
    }
}