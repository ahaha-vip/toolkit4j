package com.chengwei.toolkit4j.security;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 认证用户基类
 *
 * @author chengwei
 * @since 2022/1/10
 */
@Getter
@Setter
@Schema(description = "认证用户基类")
public abstract class BaseUserLoginPrincipal implements LoginPrincipal {

    @Schema(description = "登录平台")
    protected LoginPlatform loginPlatform;

    @Schema(description = "认证令牌")
    protected String accessToken;

    @Schema(description = "用户id")
    protected String userId;

    @Schema(description = "角色集合")
    protected Set<String> roles;

    @Schema(description = "权限集合")
    protected Set<String> permissions;

    @Schema(description = "认证主体id，同用户id")
    @Override
    public String getPrincipalId() {
        return userId;
    }

    @Schema(description = "认证主体类型，固定为用户")
    @Override
    public PrincipalType getPrincipalType() {
        return PrincipalType.USER;
    }

    @Override
    public String toString() {
        return StrUtil.format("PrincipalType：{}，PrincipalId：{}", this.getPrincipalType(), this.getPrincipalId());
    }
}