package com.chengwei.toolkit4j.ratelimit;

import cn.hutool.core.lang.Assert;
import com.chengwei.toolkit4j.core.exception.client.AccessLimitationException;
import com.chengwei.toolkit4j.core.exception.server.ServerIllegalException;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 默认的限流实现
 *
 * @author chengwei
 * @since 2022/1/21
 */
public class DefaultRateLimitTemplate implements RateLimitTemplate {

    /**
     * 限流时间间隔
     */
    public static final long DEFAULT_RATE_INTERVAL = 1L;

    /**
     * 底层使用redisson限流
     */
    private final RedissonClient redissonClient;

    public DefaultRateLimitTemplate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public <T> T execute(String rateLimitKey, Supplier<T> operation, long limitTimes, TimeUnit rateIntervalTimeUnit) throws AccessLimitationException {
        return this.execute(rateLimitKey, operation, limitTimes, DEFAULT_RATE_INTERVAL, rateIntervalTimeUnit);
    }

    @Override
    public <T> T execute(String rateLimitKey, Supplier<T> operation, long limitTimes, long rateInterval, TimeUnit rateIntervalTimeUnit) throws AccessLimitationException {
        // 参数校验
        Assert.notEmpty(rateLimitKey, () -> new ServerIllegalException("限流键不能为空"));
        Assert.notNull(operation, () -> new ServerIllegalException("限流操作不能为空"));
        Assert.isTrue(limitTimes > 0, "限流限制次数必须为正数");
        Assert.isTrue(rateInterval > 0, "限流间隔时间必须为正数");
        RateIntervalUnit rateIntervalUnit = convertTimeUnit(rateIntervalTimeUnit);

        // 根据限流键获取一个限流对象
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(rateLimitKey);
        rateLimiter.trySetRate(RateType.OVERALL, limitTimes, rateInterval, rateIntervalUnit);
        try {
            // 获取许可失败则抛出异常
            boolean tryAcquire = rateLimiter.tryAcquire();
            Assert.isTrue(tryAcquire, () -> new AccessLimitationException("操作过于频繁，请稍后再试!"));

            // 获取许可成功，执行操作
            return operation.get();
        } finally {
            // 刷新过期时间
            rateLimiter.expire(rateInterval, rateIntervalTimeUnit);
        }
    }

    /**
     * 时间单位转换
     *
     * @param timeUnit 标准库时间单位
     * @return redisson时间单位
     */
    private RateIntervalUnit convertTimeUnit(TimeUnit timeUnit) {
        switch (timeUnit) {
            case SECONDS:
                return RateIntervalUnit.SECONDS;
            case MINUTES:
                return RateIntervalUnit.MINUTES;
            case HOURS:
                return RateIntervalUnit.HOURS;
            case DAYS:
                return RateIntervalUnit.DAYS;
            default:
                throw new ServerIllegalException("不支持的限流单位");
        }
    }
}