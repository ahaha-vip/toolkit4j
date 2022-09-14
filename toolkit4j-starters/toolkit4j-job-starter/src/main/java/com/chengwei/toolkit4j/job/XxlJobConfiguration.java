package com.chengwei.toolkit4j.job;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * xxl-job自动化配置
 *
 * @author chengwei
 * @since 2021/12/10
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(XxlJobProperties.class)
@ConditionalOnProperty(prefix = "xxl.job", value = "enabled", havingValue = "true")
public class XxlJobConfiguration {

    /**
     * 构建xxl-job执行器，生命周期由spring去管理，详细代码见{@link XxlJobSpringExecutor#afterSingletonsInstantiated()}。
     *
     * @param xxlJobProperties xxl-job配置
     * @return XxlJobSpringExecutor
     */
    @Bean
    public XxlJobSpringExecutor xxlJobExecutor(XxlJobProperties xxlJobProperties) {
        String adminAddresses = xxlJobProperties.getAdminAddresses();
        String accessToken = xxlJobProperties.getAccessToken();
        String applicationName = xxlJobProperties.getApplicationName();
        String applicationIp = xxlJobProperties.getApplicationIp();
        Integer applicationPort = xxlJobProperties.getApplicationPort();
        String logPath = xxlJobProperties.getLogPath();
        int logRetentionDays = xxlJobProperties.getLogRetentionDays();

        // 校验配置并填充默认值
        Assert.notEmpty(adminAddresses, "xxl-job服务端地址不能为空");
        // 默认使用UUID作为执行器名
        applicationName = Optional.ofNullable(applicationName).orElse(IdUtil.simpleUUID());
        // 默认取第一个网卡的地址
        applicationIp = Optional.ofNullable(applicationIp).orElse(NetUtil.getLocalhostStr());
        // 默认随机获取一个可用端口
        applicationPort = Optional.ofNullable(applicationPort).orElse(NetUtil.getUsableLocalPort());

        log.info("初始化任务执行器，应用名：{}，IP：{}，端口：{}", applicationName, applicationIp, applicationPort);

        // 构建xxl-job执行器
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setAppname(applicationName);
        xxlJobSpringExecutor.setIp(applicationIp);
        xxlJobSpringExecutor.setPort(applicationPort);
        xxlJobSpringExecutor.setLogPath(logPath);
        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
        return xxlJobSpringExecutor;
    }
}