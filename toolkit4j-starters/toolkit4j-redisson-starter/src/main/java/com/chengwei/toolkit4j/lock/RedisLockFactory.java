package com.chengwei.toolkit4j.lock;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

/**
 * 基于redis的锁工厂实现
 *
 * @author chengwei
 * @since 2021/12/10
 */
public class RedisLockFactory implements LockFactory {

    private final RedissonClient redissonClient;

    public RedisLockFactory(RedissonClient redissonClient) {
        Assert.notNull(redissonClient, "redissonClient不能为空");
        this.redissonClient = redissonClient;
    }

    @Override
    public Lock getLock(String lockName) {
        Assert.notEmpty(lockName, "锁名不能为空");
        return this.redissonClient.getLock(lockName);
    }

    @Override
    public Lock getMultiLock(Set<String> lockNames) {
        Assert.notEmpty(lockNames, "锁名不能为空");
        List<RLock> locks = lockNames.stream().map(redissonClient::getLock).collect(Collectors.toList());
        return this.redissonClient.getMultiLock(ArrayUtil.toArray(locks, RLock.class));
    }
}