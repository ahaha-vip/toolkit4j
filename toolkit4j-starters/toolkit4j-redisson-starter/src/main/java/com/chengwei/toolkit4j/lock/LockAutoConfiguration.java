package com.chengwei.toolkit4j.lock;

import cn.hutool.core.lang.Assert;
import com.chengwei.toolkit4j.cache.CacheAutoConfiguration;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 锁自动化配置，基于redisson实现。
 *
 * @author chengwei
 * @since 2021/12/10
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(CacheAutoConfiguration.class)
@EnableConfigurationProperties(LockProperties.class)
public class LockAutoConfiguration {

    @Bean
    @ConditionalOnBean(RedissonClient.class)
    public LockTemplate lockTemplate(RedissonClient redissonClient, LockProperties lockProperties) {
        long tryLockExpireTime = lockProperties.getTryLockExpireTime();
        Assert.isTrue(tryLockExpireTime > 0, "获取锁超时时间必须为正数");
        LockFactory lockFactory = new RedisLockFactory(redissonClient);
        return new DefaultLockTemplate(lockFactory, tryLockExpireTime);
    }
}