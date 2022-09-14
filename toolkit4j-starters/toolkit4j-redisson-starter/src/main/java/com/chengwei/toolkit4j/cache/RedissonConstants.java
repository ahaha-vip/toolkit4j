package com.chengwei.toolkit4j.cache;

/**
 * 常量
 *
 * @author chengwei
 * @since 2021/12/9
 */
public class RedissonConstants {

    /**
     * 默认协议，不开启SSL
     */
    public static final String DEFAULT_PROTOCOL_PREFIX = "redis://";

    /**
     * 开启SSL
     */
    public static final String SSL_PROTOCOL_PREFIX = "rediss://";

    /**
     * 服务端地址分隔符
     */
    public static final String SEPARATOR = ",";
}