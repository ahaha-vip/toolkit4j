package com.chengwei.toolkit4j.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录平台
 *
 * @author chengwei
 * @since 2022/1/25
 */
@Getter
@AllArgsConstructor
public enum LoginPlatform {

    /**
     * 移动端APP
     */
    APP("app"),

    /**
     * PC端
     */
    PC("pc"),

    /**
     * 微信小程序
     */
    WECHAT_APPLET("wechat_applet"),

    /**
     * 支付宝小程序
     */
    ALIPAY_APPLET("alipay_applet"),

    /**
     * 钉钉
     */
    DING_TALK("ding_talk"),

    /**
     * 其他
     */
    OTHER("other");

    /**
     * 平台类型
     */
    private final String type;
}
