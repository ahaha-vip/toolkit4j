package com.chengwei.toolkit4j.blobstore.minio;

import lombok.Getter;
import lombok.Setter;

/**
 * minio配置
 *
 * @author chengwei
 * @since 2021/12/9
 */
@Getter
@Setter
public class MinioBlobStoreProperties {

    /**
     * 服务端地址
     */
    private String endpoint;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 连接超时时间（秒）
     */
    private Long connectTimeout = 10L;

    /**
     * 写超时时间（秒）
     */
    private Long writeTimeout = 60L;

    /**
     * 读超时时间（秒）
     */
    private Long readTimeout = 30L;

    /**
     * 桶名称，默认为 blobstore
     */
    private String bucket = "blobstore";
}