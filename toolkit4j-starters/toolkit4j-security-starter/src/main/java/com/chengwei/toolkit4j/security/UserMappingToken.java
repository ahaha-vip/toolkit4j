package com.chengwei.toolkit4j.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户与令牌的映射关系
 *
 * @author chengwei
 * @since 2022/2/23
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMappingToken {

    /**
     * 认证令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;
}