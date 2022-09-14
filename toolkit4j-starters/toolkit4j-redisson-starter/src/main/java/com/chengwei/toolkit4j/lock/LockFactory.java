package com.chengwei.toolkit4j.lock;

import java.util.Set;
import java.util.concurrent.locks.Lock;

/**
 * 锁工厂
 *
 * @author chengwei
 * @since 2021/12/10
 */
public interface LockFactory {

    /**
     * 获取锁
     *
     * @param lockName 锁名
     * @return 锁
     */
    Lock getLock(String lockName);

    /**
     * 获取多锁
     *
     * @param lockNames 锁名集合
     * @return 锁
     */
    Lock getMultiLock(Set<String> lockNames);
}
