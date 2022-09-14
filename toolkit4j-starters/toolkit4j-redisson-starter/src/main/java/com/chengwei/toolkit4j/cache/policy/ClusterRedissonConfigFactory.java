package com.chengwei.toolkit4j.cache.policy;

import cn.hutool.core.lang.Assert;
import com.chengwei.toolkit4j.cache.CacheProperties;
import com.chengwei.toolkit4j.cache.RedissonConfigFactory;
import com.chengwei.toolkit4j.cache.RedissonConstants;
import org.redisson.config.Config;

import java.util.Arrays;

/**
 * 集群模式
 *
 * @author chengwei
 * @since 2021/12/9
 */
public class ClusterRedissonConfigFactory implements RedissonConfigFactory {

    @Override
    public Config create(CacheProperties cacheProperties) {
        Config config = RedissonConfigFactory.super.create(cacheProperties);
        String address = cacheProperties.getAddress();
        String password = cacheProperties.getPassword();
        boolean ssl = cacheProperties.isSsl();

        String[] addressList = address.split(RedissonConstants.SEPARATOR);
        Assert.state(addressList.length > 1, "集群模式下redis服务端地址配置有误");

        Arrays.stream(addressList).forEach(node -> {
            String redissonAddress = getRedissonAddress(ssl, node);
            config.useClusterServers().addNodeAddress(redissonAddress);
        });
        config.useClusterServers().setPassword(password);

        return config;
    }
}