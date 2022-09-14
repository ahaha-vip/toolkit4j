package com.chengwei.toolkit4j.ratelimit;


import com.chengwei.toolkit4j.core.exception.client.AccessLimitationException;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 限流接口
 *
 * @author chengwei
 * @since 2022/1/21
 */
public interface RateLimitTemplate {

    /**
     * 限流操作
     *
     * @param rateLimitKey         限流键
     * @param operation            操作
     * @param limitTimes           单位时间(1 * rateIntervalTimeUnit)内请求限制次数
     * @param rateIntervalTimeUnit 限流间隔时间单位，支持时、分、秒、天
     * @param <T>                  返回值类型泛型
     * @return 操作完成后的返回值
     * @throws AccessLimitationException 当超过限流次数时抛出
     */
    <T> T execute(String rateLimitKey, Supplier<T> operation, long limitTimes, TimeUnit rateIntervalTimeUnit) throws AccessLimitationException;

    /**
     * 限流操作
     *
     * @param rateLimitKey         限流键
     * @param operation            操作
     * @param limitTimes           (rateInterval * rateIntervalTimeUnit)时间内请求限制次数
     * @param rateInterval         限流间隔时间
     * @param rateIntervalTimeUnit 限流间隔时间单位，支持时、分、秒、天
     * @param <T>                  返回值类型泛型
     * @return 操作完成后的返回值
     * @throws AccessLimitationException 当超过限流次数时抛出
     */
    <T> T execute(String rateLimitKey, Supplier<T> operation, long limitTimes, long rateInterval, TimeUnit rateIntervalTimeUnit) throws AccessLimitationException;

    /**
     * 限流操作
     *
     * @param rateLimitKey         限流键
     * @param operation            操作
     * @param limitTimes           单位时间(1 * rateIntervalTimeUnit)内请求限制次数
     * @param rateIntervalTimeUnit 限流间隔时间单位，支持时、分、秒、天
     * @throws AccessLimitationException 当超过限流次数时抛出
     */
    default void execute(String rateLimitKey, Runnable operation, long limitTimes, TimeUnit rateIntervalTimeUnit) throws AccessLimitationException {
        this.execute(rateLimitKey, () -> {
            operation.run();
            return null;
        }, limitTimes, rateIntervalTimeUnit);
    }

    /**
     * 限流操作
     *
     * @param rateLimitKey         限流键
     * @param operation            操作
     * @param limitTimes           (rateInterval * rateIntervalTimeUnit)时间内请求限制次数
     * @param rateInterval         限流间隔时间
     * @param rateIntervalTimeUnit 限流间隔时间单位，支持时、分、秒、天
     * @throws AccessLimitationException 当超过限流次数时抛出
     */
    default void execute(String rateLimitKey, Runnable operation, long limitTimes, long rateInterval, TimeUnit rateIntervalTimeUnit) throws AccessLimitationException {
        this.execute(rateLimitKey, () -> {
            operation.run();
            return null;
        }, limitTimes, rateInterval, rateIntervalTimeUnit);
    }
}
