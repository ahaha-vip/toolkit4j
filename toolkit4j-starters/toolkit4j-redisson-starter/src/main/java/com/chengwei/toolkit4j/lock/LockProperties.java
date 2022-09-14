package com.chengwei.toolkit4j.lock;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 锁配置
 *
 * @author chengwei
 * @since 2021/12/10
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "lock")
public class LockProperties {

    /**
     * 获取锁超时时间（单位秒），默认10秒
     */
    private long tryLockExpireTime = 10;
}