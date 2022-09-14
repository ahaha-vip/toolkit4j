package com.chengwei.toolkit4j.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 缓存配置
 *
 * @author chengwei
 * @since 2021/12/9
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {

    /**
     * 默认缓存时间，单位分，默认值7 * 24 * 60，即7天
     */
    private long defaultCacheTime = 7 * 24 * 60;

    /**
     * redis服务端地址，多个时用英文逗号分隔。默认为：localhost:6379
     */
    private String address = "localhost:6379";

    /**
     * database，默认为0
     */
    private int database = 0;

    /**
     * 认证密码，默认为空
     */
    private String password;

    /**
     * 是否开启ssl，默认关闭
     */
    private boolean ssl = false;

    /**
     * 部署策略，默认单节点模式
     */
    private RedisPolicy policy = RedisPolicy.STANDALONE;
}