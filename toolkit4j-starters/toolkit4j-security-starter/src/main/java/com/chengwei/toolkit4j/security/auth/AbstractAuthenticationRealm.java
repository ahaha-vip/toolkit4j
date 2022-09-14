package com.chengwei.toolkit4j.security.auth;

import com.chengwei.toolkit4j.security.LoginPlatform;
import com.chengwei.toolkit4j.security.LoginPrincipal;
import com.chengwei.toolkit4j.security.ShiroConstants;
import com.chengwei.toolkit4j.security.ShiroProperties;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

/**
 * 抽象认证、授权器，提供模板方法。
 * shiro的认证逻辑基于策略模式实现，因此重写{@link AuthenticatingRealm#getAuthenticationTokenClass()}非常重要。
 * {@link Realm}和{@link AuthenticationToken}为一一对应的关系{@link Realm#supports(AuthenticationToken)}。
 *
 * @author chengwei
 * @since 2021/12/10
 */
public abstract class AbstractAuthenticationRealm<T extends LoginPrincipal> extends AuthorizingRealm {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    protected ShiroProperties shiroProperties;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        T loginPrincipal = getLoginPrincipal(authenticationToken);
        return new SimpleAuthenticationInfo(loginPrincipal, authenticationToken.getCredentials(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 用户的角色、权限
        LoginPrincipal loginPrincipal = (LoginPrincipal) principals.getPrimaryPrincipal();
        Set<String> roles = loginPrincipal.getRoles();
        Set<String> permissions = loginPrincipal.getPermissions();

        // 授权信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 角色
        simpleAuthorizationInfo.setRoles(roles);
        // 权限
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 基于{@code authenticationToken}获取{@link LoginPrincipal}信息
     *
     * @param authenticationToken 认证凭据
     * @return 登录用户
     * @throws AuthenticationException 认证失败时抛出异常
     */
    protected abstract T getLoginPrincipal(AuthenticationToken authenticationToken) throws AuthenticationException;

    /**
     * 获取认证令牌缓存键
     *
     * @param accessToken 令牌
     * @return 缓存键
     */
    protected String getAccessTokenCacheKey(String accessToken) {
        String applicationName = getApplicationName();
        return ShiroConstants.getAccessTokenCacheKey(applicationName, accessToken);
    }

    /**
     * 获取刷新令牌的缓存键
     *
     * @param refreshToken 刷新令牌
     * @return 缓存键
     */
    protected String getRefreshTokenCacheKey(String refreshToken) {
        String applicationName = getApplicationName();
        return ShiroConstants.getRefreshTokenCacheKey(applicationName, refreshToken);
    }

    /**
     * 获取用户与令牌映射关系的缓存键
     *
     * @param userId        用户id
     * @param loginPlatform 登录平台
     * @return 缓存键
     */
    protected String getUserMappingTokenCacheKey(String userId, LoginPlatform loginPlatform) {
        String applicationName = getApplicationName();
        return ShiroConstants.getUserMappingTokenCacheKey(applicationName, loginPlatform, userId);
    }

    /**
     * 获取服务名，默认直接在上下文中获取服务名。
     *
     * @return 服务名
     */
    protected String getApplicationName() {
        return applicationName;
    }
}