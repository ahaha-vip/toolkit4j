package com.chengwei.toolkit4j.blobstore;

import com.chengwei.toolkit4j.blobstore.aliyun.AliyunBlobStoreProperties;
import com.chengwei.toolkit4j.blobstore.local.LocalBlobStoreProperties;
import com.chengwei.toolkit4j.blobstore.minio.MinioBlobStoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 二进制存储属性配置
 *
 * @author chengwei
 * @since 2021/12/9
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "blobstore")
public class BlobStoreProperties {

    /**
     * 文件访问地址，一般提供互联网地址，供外界访问。
     */
    private String accessUrl;

    /**
     * 存储策略，默认使用本地存储
     */
    @NestedConfigurationProperty
    private BlobStorePolicy policy = BlobStorePolicy.LOCAL;

    /**
     * 本地存储配置
     */
    @NestedConfigurationProperty
    private LocalBlobStoreProperties local = new LocalBlobStoreProperties();

    /**
     * 阿里云存储配置
     */
    @NestedConfigurationProperty
    private AliyunBlobStoreProperties aliyun = new AliyunBlobStoreProperties();

    /**
     * minio存储配置
     */
    @NestedConfigurationProperty
    private MinioBlobStoreProperties minio = new MinioBlobStoreProperties();

    /**
     * 二进制文件存储策略
     */
    public enum BlobStorePolicy {
        /**
         * 本地存储
         */
        LOCAL,

        /**
         * 阿里云存储
         */
        ALIYUN,

        /**
         * minio
         */
        MINIO
    }
}