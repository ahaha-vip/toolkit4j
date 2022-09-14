package com.chengwei.toolkit4j.security.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.AuthenticatingRealm;

/**
 * 请求头凭据，shiro会根据{@link AuthenticatingRealm#getAuthenticationTokenClass()}的指定策略由{@link AuthenticatingRealm}来认证。
 *
 * @author chengwei
 * @since 2021/12/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeaderAuthenticationToken implements AuthenticationToken {

    /**
     * 认证令牌
     */
    private String accessToken;

    @Override
    public Object getPrincipal() {
        return accessToken;
    }

    @Override
    public Object getCredentials() {
        return accessToken;
    }
}