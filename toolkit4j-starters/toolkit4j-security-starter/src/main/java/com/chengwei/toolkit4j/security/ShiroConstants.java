package com.chengwei.toolkit4j.security;

import cn.hutool.core.util.StrUtil;

/**
 * shiro常量
 *
 * @author chengwei
 * @since 2021/12/10
 */
public abstract class ShiroConstants {

    /**
     * 认证请求头名称
     */
    public static final String AUTHENTICATION_REQUEST_HEADER = "authentication_token";

    /**
     * 认证过滤器名称
     */
    public static final String AUTHENTICATION_FILTER_NAME = "authentication";

    /**
     * 认证事件处理器线程池名
     */
    public static final String AUTHENTICATION_EVENT_HANDLER_THREAD_POOL = "AuthenticationEventHandlerThreadPool";

    /**
     * 用户与令牌映射关系的缓存过期时间（单位：天）
     */
    public static final long USER_MAPPING_TOKEN_EXPIRE_TIME = 365L;

    /**
     * 刷新令牌的过期时间（单位：天）
     */
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 30L;

    /**
     * 获取认证令牌的缓存键，缓存值为{@link LoginPrincipal}
     *
     * @param applicationName 服务名称
     * @param accessToken     认证令牌
     * @return 缓存键
     */
    public static String getAccessTokenCacheKey(String applicationName, String accessToken) {
        return StrUtil.format("{}:accessToken:{}", applicationName, accessToken);
    }

    /**
     * 获取刷新令牌的缓存键，缓存值为{@link LoginPrincipal}
     *
     * @param applicationName 服务名称
     * @param refreshToken    刷新令牌
     * @return 缓存键
     */
    public static String getRefreshTokenCacheKey(String applicationName, String refreshToken) {
        return StrUtil.format("{}:refreshToken:{}", applicationName, refreshToken);
    }

    /**
     * 获取用户与令牌映射关系的缓存键，缓存值为{@link UserMappingToken}
     *
     * @param applicationName 服务名称
     * @param loginPlatform   登录平台
     * @param userId          用户id
     * @return 缓存键
     */
    public static String getUserMappingTokenCacheKey(String applicationName, LoginPlatform loginPlatform, String userId) {
        return StrUtil.format("{}:userMappingToken:{}:{}", applicationName, loginPlatform.getType(), userId);
    }

    /**
     * OAuth2授权通过后的用户信息临时缓存键
     *
     * @param applicationName 服务名称
     * @param tempToken       临时令牌
     * @return 缓存键
     */
    public static String getOAuth2UserTempCacheKey(String applicationName, String tempToken) {
        return StrUtil.format("{}:registerUser:oAuth2UserTempToken:{}", applicationName, tempToken);
    }
}