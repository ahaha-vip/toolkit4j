package com.chengwei.toolkit4j.lock;

import cn.hutool.core.lang.Assert;
import com.chengwei.toolkit4j.core.exception.client.OperationConflictException;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

/**
 * 锁模板的默认实现
 *
 * @author chengwei
 * @since 2021/12/10
 */
public class DefaultLockTemplate implements LockTemplate {

    /**
     * 锁工厂
     */
    private final LockFactory lockFactory;

    /**
     * 获取锁超时时间，单位秒
     */
    private final long tryLockExpireTime;

    /**
     * 获取锁超时时间单位，默认秒
     */
    private final TimeUnit expireTimeUnit = TimeUnit.SECONDS;

    public DefaultLockTemplate(LockFactory lockFactory, long tryLockExpireTime) {
        Assert.notNull(lockFactory, "锁工厂不能为空");
        Assert.isTrue(tryLockExpireTime > 0, "获取锁超时时间必须为正数");
        this.lockFactory = lockFactory;
        this.tryLockExpireTime = tryLockExpireTime;
    }

    @Override
    public <T> T execute(String lockName, Supplier<T> operation) {
        Assert.notEmpty(lockName, "锁名不能为空");
        Assert.notNull(operation, "加锁操作不能为空");
        Lock lock = this.lockFactory.getLock(lockName);
        return this.tryLockOperation(lock, operation);
    }

    @Override
    public <T> T execute(Set<String> lockNames, Supplier<T> operation) {
        Assert.notEmpty(lockNames, "锁名不能为空");
        Assert.notNull(operation, "加锁操作不能为空");
        Lock lock = this.lockFactory.getMultiLock(lockNames);
        return this.tryLockOperation(lock, operation);
    }

    private <T> T tryLockOperation(Lock lock, Supplier<T> operation) {
        try {
            boolean success = lock.tryLock(tryLockExpireTime, expireTimeUnit);
            if (!success) {
                throw new OperationConflictException("获取锁资源失败");
            }
        } catch (InterruptedException e) {
            throw new OperationConflictException("获取锁资源失败");
        }
        try {
            return operation.get();
        } finally {
            lock.unlock();
        }
    }
}