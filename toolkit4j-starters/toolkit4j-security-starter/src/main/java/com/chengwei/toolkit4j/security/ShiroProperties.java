package com.chengwei.toolkit4j.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Set;

/**
 * shiro配置
 *
 * @author chengwei
 * @since 2021/12/10
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {

    /**
     * 是否开启shiro自动化配置，默认关闭。
     */
    private boolean enabled = false;

    /**
     * 令牌过期时间（单位：分），默认60分钟。
     */
    private long expireTime = 60L;

    /**
     * 匿名资源路径，即认证与否皆可访问。如：登录、验证码接口等。
     */
    private Set<String> anons = Collections.emptySet();
}