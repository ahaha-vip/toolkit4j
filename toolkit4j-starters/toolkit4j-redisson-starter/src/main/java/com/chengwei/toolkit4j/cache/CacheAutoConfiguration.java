package com.chengwei.toolkit4j.cache;

import com.chengwei.toolkit4j.cache.policy.ClusterRedissonConfigFactory;
import com.chengwei.toolkit4j.cache.policy.MasterSlaveRedissonConfigFactory;
import com.chengwei.toolkit4j.cache.policy.SentinelRedissonConfigFactory;
import com.chengwei.toolkit4j.cache.policy.StandaloneRedissonConfigFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存自动化配置，基于redis实现。
 * <p>
 * TODO：时间原因，暂时只测试了单节点模式，其他模式待后续测试。
 *
 * @author chengwei
 * @since 2021/12/9
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CacheProperties.class)
public class CacheAutoConfiguration {

    @Bean
    public RedissonClient redissonClient(CacheProperties cacheProperties) {
        switch (cacheProperties.getPolicy()) {
            case STANDALONE:
                return Redisson.create(new StandaloneRedissonConfigFactory().create(cacheProperties));
            case CLUSTER:
                return Redisson.create(new ClusterRedissonConfigFactory().create(cacheProperties));
            case SENTINEL:
                return Redisson.create(new SentinelRedissonConfigFactory().create(cacheProperties));
            case MASTER_SLAVE:
                return Redisson.create(new MasterSlaveRedissonConfigFactory().create(cacheProperties));
            default:
                throw new UnsupportedOperationException("不支持的策略");
        }
    }

    @Bean
    public CacheTemplate cacheTemplate(RedissonClient redissonClient, CacheProperties cacheProperties) {
        return new RedissonCacheTemplate(redissonClient, cacheProperties.getDefaultCacheTime());
    }
}