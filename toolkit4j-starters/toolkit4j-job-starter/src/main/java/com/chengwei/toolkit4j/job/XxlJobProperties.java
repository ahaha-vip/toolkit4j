package com.chengwei.toolkit4j.job;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * xxl-job配置
 *
 * @author chengwei
 * @since 2021/12/10
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    /**
     * 是否开启定时任务，默认关闭
     */
    private boolean enabled = false;

    /**
     * xxl-job服务端地址（必填，集群以","分隔），如 http://127.0.0.1:8080/xxl-job-admin
     */
    private String adminAddresses;

    /**
     * xxl-job服务端认证令牌
     */
    private String accessToken;

    /**
     * 当前服务名称，默认UUID
     */
    private String applicationName;

    /**
     * 当前服务IP地址（需保证xxl-job服务端能访问），默认获取第一个网卡地址
     */
    private String applicationIp;

    /**
     * 当前服务端口（需保证xxl-job服务端能访问），默认随机获取一个可用端口
     */
    private Integer applicationPort;

    /**
     * 日志路径
     */
    private String logPath;

    /**
     * 日志保留时间（单位：天），默认7天，小于3天则不清理日志
     */
    private int logRetentionDays = 7;
}