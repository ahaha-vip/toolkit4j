package com.chengwei.toolkit4j.cache.policy;

import com.chengwei.toolkit4j.cache.CacheProperties;
import com.chengwei.toolkit4j.cache.RedissonConfigFactory;
import org.redisson.config.Config;

/**
 * 单节点模式
 *
 * @author chengwei
 * @since 2021/12/9
 */
public class StandaloneRedissonConfigFactory implements RedissonConfigFactory {

    @Override
    public Config create(CacheProperties cacheProperties) {
        Config config = RedissonConfigFactory.super.create(cacheProperties);
        String address = this.getRedissonAddress(cacheProperties.isSsl(), cacheProperties.getAddress());
        String password = cacheProperties.getPassword();
        int database = cacheProperties.getDatabase();

        config.useSingleServer().setAddress(address);
        config.useSingleServer().setPassword(password);
        config.useSingleServer().setDatabase(database);
        return config;
    }
}