package com.chengwei.toolkit4j.core.resolver.support;

import lombok.*;

/**
 * 客户端登录信息
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
public class ClientLoginInfo {

    /**
     * 是否认证通过
     */
    private boolean authenticated = false;

    /**
     * 认证信息
     */
    private Object principal;
}