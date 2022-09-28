package com.chengwei.toolkit4j.cache;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 缓存接口
 *
 * @author chengwei
 * @since 2021/12/9
 */
public interface CacheTemplate {

    /**
     * 根据缓存键插入缓存值
     *
     * @param key   缓存键
     * @param value 缓存值
     * @param <T>   缓存值类型
     * @return 缓存值
     */
    <T> T set(String key, T value);

    /**
     * 根据缓存键插入缓存值并设置过期时间
     *
     * @param key        缓存键
     * @param value      缓存值
     * @param expireTime 过期时间
     * @param timeUnit   过期时间单位
     * @param <T>        缓存值类型
     * @return 缓存值
     */
    <T> T set(String key, T value, long expireTime, TimeUnit timeUnit);

    /**
     * 根据缓存键获取缓存值
     *
     * @param key 缓存键
     * @param <T> 缓存值类型
     * @return 缓存值
     */
    <T> T get(String key);

    /**
     * 根据缓存键获取缓存值，若缓存值不存在则从{@code cacheLoader}中加载，若加载值不为空则缓存
     *
     * @param key         缓存键
     * @param cacheLoader 缓存加载器
     * @param <T>         缓存值类型
     * @return 缓存值
     */
    <T> T get(String key, Supplier<T> cacheLoader);

    /**
     * 根据缓存键获取缓存值，若缓存值不存在则从{@code cacheLoader}中加载，若加载值不为空则缓存并设置过期时间
     *
     * @param key         缓存键
     * @param cacheLoader 缓存加载器
     * @param expireTime  过期时间
     * @param timeUnit    过期时间单位
     * @param <T>         缓存值类型
     * @return 缓存值
     */
    <T> T get(String key, Supplier<T> cacheLoader, long expireTime, TimeUnit timeUnit);

    /**
     * 根据缓存键删除缓存值
     *
     * @param key 缓存键
     */
    void delete(String key);

    /**
     * 根据缓存键获取并删除缓存值
     *
     * @param key 缓存键
     * @param <T> 缓存值类型
     * @return 缓存值
     */
    <T> T getAndDelete(String key);

    /**
     * 根据缓存键获取过期时间（秒）。若key不存在，则返回-2；若key未设置过期时间，则返回-1。
     *
     * @param key 缓存键
     * @return 若key不存在，则返回-2；若key未设置过期时间，则返回-1
     */
    long getExpireTime(String key);

    /**
     * 根据缓存键设置过期时间
     *
     * @param key        缓存键
     * @param expireTime 过期时间
     * @param timeUnit   过期时间单位
     */
    void expire(String key, long expireTime, TimeUnit timeUnit);

    /**
     * 先获取值，然后自增，即 return i++;
     *
     * @param key 缓存键
     * @return 值
     */
    long getAndIncrement(String key);

    /**
     * 先获取值，然后自减，即 return i--;
     *
     * @param key 缓存键
     * @return 值
     */
    long getAndDecrement(String key);

    /**
     * 先自增，然后获取值，即 return ++i;
     *
     * @param key 缓存键
     * @return 值
     */
    long incrementAndGet(String key);

    /**
     * 先自减，然后获取值，即 return --i;
     *
     * @param key 缓存键
     * @return 值
     */
    long decrementAndGet(String key);

    /**
     * 先相加，然后获取值，即 return i + delta;
     *
     * @param key   缓存键
     * @param delta 增加值
     * @return 值
     */
    long addAndGet(String key, long delta);
}
