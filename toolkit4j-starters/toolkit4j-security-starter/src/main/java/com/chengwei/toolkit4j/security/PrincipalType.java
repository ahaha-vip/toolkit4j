package com.chengwei.toolkit4j.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证主体类型
 *
 * @author chengwei
 * @since 2022/2/23
 */
@Getter
@AllArgsConstructor
public enum PrincipalType {

    /**
     * 用户
     */
    USER("user"),

    /**
     * 客户端
     */
    CLIENT("client"),

    /**
     * 匿名
     */
    ANONYMOUS("anonymous");

    /**
     * 类型
     */
    private final String type;
}
