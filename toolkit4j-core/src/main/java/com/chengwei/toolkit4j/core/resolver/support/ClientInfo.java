package com.chengwei.toolkit4j.core.resolver.support;

import lombok.*;

/**
 * 客户端信息
 *
 * @author chengwei
 * @since 2021/12/15
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfo {

    /**
     * 请求路径
     */
    private String requestPath;

    /**
     * 请求方式
     */
    private String httpMethod;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 是否认证通过
     */
    private boolean authenticated;

    /**
     * 登录信息
     */
    private String loginPrincipal;
}