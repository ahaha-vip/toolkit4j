package com.chengwei.toolkit4j.ratelimit;

import com.chengwei.toolkit4j.cache.CacheAutoConfiguration;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 限流自动化配置
 *
 * @author chengwei
 * @since 2022/1/21
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(CacheAutoConfiguration.class)
public class RateLimitConfiguration {

    @Bean
    @ConditionalOnBean(RedissonClient.class)
    public RateLimitTemplate rateLimitTemplate(RedissonClient redissonClient) {
        return new DefaultRateLimitTemplate(redissonClient);
    }
}