package com.chengwei.toolkit4j.cache;

import com.chengwei.toolkit4j.cache.codec.CacheJsonJacksonCodec;
import org.redisson.config.Config;

/**
 * redisson配置工厂
 *
 * @author chengwei
 * @since 2021/12/9
 */
public interface RedissonConfigFactory {

    /**
     * 构建redisson配置
     *
     * @param cacheProperties 缓存配置
     * @return redisson配置
     */
    default Config create(CacheProperties cacheProperties) {
        Config config = new Config();
        config.setCodec(new CacheJsonJacksonCodec());
        return config;
    }

    /**
     * 组装服务端地址，拼装协议
     *
     * @param ssl     是否启用ssl
     * @param address 服务端地址
     * @return 服务端地址
     */
    default String getRedissonAddress(boolean ssl, String address) {
        if (ssl) {
            return RedissonConstants.SSL_PROTOCOL_PREFIX + address;
        }
        return RedissonConstants.DEFAULT_PROTOCOL_PREFIX + address;
    }
}
