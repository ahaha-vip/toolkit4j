package com.chengwei.toolkit4j.cache.policy;

import cn.hutool.core.lang.Assert;
import com.chengwei.toolkit4j.cache.CacheProperties;
import com.chengwei.toolkit4j.cache.RedissonConfigFactory;
import com.chengwei.toolkit4j.cache.RedissonConstants;
import org.redisson.config.Config;

/**
 * 主从模式
 *
 * @author chengwei
 * @since 2021/12/10
 */
public class MasterSlaveRedissonConfigFactory implements RedissonConfigFactory {

    @Override
    public Config create(CacheProperties cacheProperties) {
        Config config = RedissonConfigFactory.super.create(cacheProperties);
        String address = cacheProperties.getAddress();
        String password = cacheProperties.getPassword();
        int database = cacheProperties.getDatabase();
        boolean ssl = cacheProperties.isSsl();

        String[] addressList = address.split(RedissonConstants.SEPARATOR);
        Assert.state(addressList.length > 1, "主从模式下redis服务端地址配置有误");

        // 第一个为主节点
        String masterAddress = getRedissonAddress(ssl, addressList[0]);
        config.useMasterSlaveServers().setMasterAddress(masterAddress);

        // 其他为从节点
        for (int i = 1; i < addressList.length; i++) {
            String slaveAddress = getRedissonAddress(ssl, addressList[i]);
            config.useMasterSlaveServers().addSlaveAddress(slaveAddress);
        }

        config.useMasterSlaveServers().setDatabase(database);
        config.useMasterSlaveServers().setPassword(password);

        return config;
    }
}