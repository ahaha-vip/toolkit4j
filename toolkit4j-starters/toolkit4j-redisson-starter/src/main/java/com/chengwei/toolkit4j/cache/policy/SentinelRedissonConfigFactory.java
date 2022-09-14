package com.chengwei.toolkit4j.cache.policy;

import cn.hutool.core.lang.Assert;
import com.chengwei.toolkit4j.cache.CacheProperties;
import com.chengwei.toolkit4j.cache.RedissonConfigFactory;
import com.chengwei.toolkit4j.cache.RedissonConstants;
import org.redisson.config.Config;

/**
 * 哨兵模式
 *
 * @author chengwei
 * @since 2021/12/10
 */
public class SentinelRedissonConfigFactory implements RedissonConfigFactory {

    @Override
    public Config create(CacheProperties cacheProperties) {
        Config config = RedissonConfigFactory.super.create(cacheProperties);
        String address = cacheProperties.getAddress();
        String password = cacheProperties.getPassword();
        int database = cacheProperties.getDatabase();
        boolean ssl = cacheProperties.isSsl();

        String[] addressList = address.split(RedissonConstants.SEPARATOR);
        Assert.state(addressList.length > 1, "哨兵模式下redis服务端地址配置有误");

        // 设置第一个为redis配置文件sentinel.conf配置的sentinel别名
        config.useSentinelServers().setMasterName(addressList[0]);

        // 设置sentinel节点地址
        for (int i = 1; i < addressList.length; i++) {
            String sentinelAddress = getRedissonAddress(ssl, addressList[i]);
            config.useSentinelServers().addSentinelAddress(sentinelAddress);
        }

        config.useSentinelServers().setPassword(password);
        config.useSentinelServers().setDatabase(database);

        return config;
    }
}