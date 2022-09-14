package com.chengwei.toolkit4j.core.springdoc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 接口文档定义
 *
 * @author chengwei
 * @since 2022/1/10
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "springdoc")
public class SpringDocProperties {

    /**
     * host
     */
    private String host;

    /**
     * port
     */
    private int port;

    /**
     * contextPath
     */
    private String contextPath;
}