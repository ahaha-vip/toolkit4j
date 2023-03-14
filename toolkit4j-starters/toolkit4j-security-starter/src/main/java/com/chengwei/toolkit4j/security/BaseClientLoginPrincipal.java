package com.chengwei.toolkit4j.security;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 认证客户端，后台服务间调用时的认证主体。
 *
 * @author chengwei
 * @since 2022/2/23
 */
@Getter
@Setter
@Schema(description = "认证客户端")
public abstract class BaseClientLoginPrincipal implements LoginPrincipal {

    @Schema(description = "认证令牌")
    protected String accessToken;

    @Schema(description = "应用id")
    protected String appId;

    @Schema(description = "角色集合")
    protected Set<String> roles;

    @Schema(description = "权限集合")
    protected Set<String> permissions;

    @Schema(description = "认证主体id，同应用id")
    @Override
    public String getPrincipalId() {
        return appId;
    }

    @Schema(description = "认证主体类型，固定为客户端")
    @Override
    public PrincipalType getPrincipalType() {
        return PrincipalType.CLIENT;
    }

    @Override
    public String toString() {
        return StrUtil.format("appId：{}", this.appId);
    }
}