package com.chengwei.toolkit4j.lock;


import com.chengwei.toolkit4j.core.exception.client.OperationConflictException;

import java.util.Set;
import java.util.function.Supplier;

/**
 * 锁接口
 *
 * @author chengwei
 * @since 2021/12/10
 */
public interface LockTemplate {

    /**
     * 基于{@code lockName}获取锁，并加锁，{@code operation}执行完成后释放锁
     *
     * @param <T>       返回值类型泛型
     * @param lockName  锁名
     * @param operation 操作
     * @return 操作的返回值
     * @throws OperationConflictException 获取锁失败
     */
    @SuppressWarnings("all")
    <T> T execute(String lockName, Supplier<T> operation) throws OperationConflictException;

    /**
     * 基于{@code lockName}获取锁，并加锁，{@code operation}执行完成后释放锁
     *
     * @param lockName  锁名
     * @param operation 操作
     * @throws OperationConflictException 获取锁失败
     */
    default void execute(String lockName, Runnable operation) throws OperationConflictException {
        this.execute(lockName, () -> {
            operation.run();
            return null;
        });
    }

    /**
     * 基于{@code lockNames}获取锁，并加锁，{@code operation}执行完成后释放锁
     *
     * @param lockNames 锁名集合
     * @param operation 操作
     * @param <T>       返回值类型泛型
     * @return 操作的返回值
     * @throws OperationConflictException 获取锁失败
     */
    @SuppressWarnings("all")
    <T> T execute(Set<String> lockNames, Supplier<T> operation) throws OperationConflictException;

    /**
     * 基于{@code lockNames}获取锁，并加锁，{@code operation}执行完成后释放锁
     *
     * @param lockNames 锁名集合
     * @param operation 操作
     * @throws OperationConflictException 获取锁失败
     */
    default void execute(Set<String> lockNames, Runnable operation) throws OperationConflictException {
        this.execute(lockNames, () -> {
            operation.run();
            return null;
        });
    }
}