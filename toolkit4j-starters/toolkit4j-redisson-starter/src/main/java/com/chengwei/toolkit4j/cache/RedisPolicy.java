package com.chengwei.toolkit4j.cache;

/**
 * redis部署策略
 *
 * @author chengwei
 * @since 2021/12/9
 */
public enum RedisPolicy {

    /**
     * 单节点
     */
    STANDALONE,

    /**
     * 集群
     */
    CLUSTER,

    /**
     * 主从
     */
    MASTER_SLAVE,

    /**
     * 哨兵
     */
    SENTINEL
}