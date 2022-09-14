package com.chengwei.toolkit4j.cache;

import cn.hutool.core.lang.Assert;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 基于redis的缓存实现
 *
 * @author chengwei
 * @since 2021/12/9
 */
public class RedissonCacheTemplate implements CacheTemplate {

    /**
     * F
     * redis客户端
     */
    private final RedissonClient redissonClient;

    /**
     * 默认缓存时间
     */
    private final long defaultCacheTime;

    /**
     * 默认缓存时间单位
     */
    private final TimeUnit defaultCacheTimeUnit = TimeUnit.MINUTES;

    public RedissonCacheTemplate(RedissonClient redissonClient, long defaultCacheTime) {
        Assert.notNull(redissonClient, "redissonClient不能为空");
        Assert.isTrue(defaultCacheTime > 0, "默认缓存时间必须大于0");
        this.redissonClient = redissonClient;
        this.defaultCacheTime = defaultCacheTime;
    }

    @Override
    public <T> T set(String key, T value) {
        Assert.notEmpty(key, "缓存键不能为空");
        return this.set(key, value, defaultCacheTime, defaultCacheTimeUnit);
    }

    @Override
    public <T> T set(String key, T value, long expireTime, TimeUnit timeUnit) {
        Assert.notEmpty(key, "缓存键不能为空");
        Assert.isTrue(expireTime > 0, "缓存时间必须大于0");
        Assert.notNull(expireTime, "缓存时间单位不能为空");
        redissonClient.getBucket(key).set(value, expireTime, timeUnit);
        return value;
    }

    @Override
    public <T> T get(String key) {
        Assert.notEmpty(key, "缓存键不能为空");
        return redissonClient.<T>getBucket(key).get();
    }

    @Override
    public <T> T get(String key, Supplier<T> cacheLoader) {
        Assert.notEmpty(key, "缓存键不能为空");
        Assert.notNull(cacheLoader, "缓存加载器不能为空");
        return this.get(key, cacheLoader, defaultCacheTime, defaultCacheTimeUnit);
    }

    @Override
    public <T> T get(String key, Supplier<T> cacheLoader, long expireTime, TimeUnit timeUnit) {
        Assert.notEmpty(key, "缓存键不能为空");
        Assert.notNull(cacheLoader, "缓存加载器不能为空");
        Assert.isTrue(expireTime > 0, "缓存时间必须大于0");
        Assert.notNull(expireTime, "缓存时间单位不能为空");

        T result = this.get(key);
        if (result == null) {
            result = cacheLoader.get();
            if (result != null) {
                this.set(key, result, expireTime, timeUnit);
            }
        }
        return result;
    }

    @Override
    public void delete(String key) {
        Assert.notEmpty(key, "缓存键不能为空");
        redissonClient.getBucket(key).delete();
    }

    @Override
    public <T> T getAndDelete(String key) {
        Assert.notEmpty(key, "缓存键不能为空");
        return redissonClient.<T>getBucket(key).getAndDelete();
    }

    @Override
    public long getExpireTime(String key) {
        Assert.notEmpty(key, "缓存键不能为空");
        long expireTime = redissonClient.getBucket(key).remainTimeToLive();
        if (expireTime > 0) {
            return expireTime / 1000;
        }
        return expireTime;
    }

    @Override
    public void expire(String key, long expireTime, TimeUnit timeUnit) {
        Assert.isTrue(expireTime > 0, "缓存时间必须大于0");
        Assert.notNull(expireTime, "缓存时间单位不能为空");
        redissonClient.getBucket(key).expire(expireTime, timeUnit);
    }

    @Override
    public long incrementId(String key) {
        Assert.notEmpty(key, "缓存键不能为空");
        return redissonClient.getAtomicLong(key).getAndIncrement();
    }
}