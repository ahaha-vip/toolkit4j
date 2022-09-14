package com.chengwei.toolkit4j.security;


import com.chengwei.toolkit4j.security.auth.AbstractAuthenticationRealm;

import java.io.Serializable;
import java.util.Set;

/**
 * 登录主体抽象，定义一些必要接口。
 *
 * @author chengwei
 * @see AbstractAuthenticationRealm
 * @since 2021/12/10
 */
public interface LoginPrincipal extends Serializable {

    /**
     * 获取认证令牌
     *
     * @return 认证令牌
     */
    String getAccessToken();

    /**
     * 获取认证主体id
     *
     * @return 认证主体id
     */
    String getPrincipalId();

    /**
     * 获取认证主体类型
     *
     * @return 主体类型
     */
    PrincipalType getPrincipalType();

    /**
     * 获取角色集合
     *
     * @return 角色集合
     */
    Set<String> getRoles();

    /**
     * 获取权限集合
     *
     * @return 权限集合
     */
    Set<String> getPermissions();
}