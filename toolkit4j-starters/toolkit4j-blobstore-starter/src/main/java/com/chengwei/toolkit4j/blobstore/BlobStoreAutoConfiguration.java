package com.chengwei.toolkit4j.blobstore;

import com.chengwei.toolkit4j.blobstore.aliyun.AliyunBlobStoreTemplate;
import com.chengwei.toolkit4j.blobstore.local.LocalBlobStoreTemplate;
import com.chengwei.toolkit4j.blobstore.minio.MinioBlobStoreTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件存储自动化配置，默认使用本地存储。
 *
 * @author chengwei
 * @since 2021/12/9
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(BlobStoreProperties.class)
public class BlobStoreAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(BlobStoreTemplate.class)
    public BlobStoreTemplate blobStoreTemplate(BlobStoreProperties blobStoreProperties) {
        switch (blobStoreProperties.getPolicy()) {
            case ALIYUN:
                return new AliyunBlobStoreTemplate(blobStoreProperties);
            case MINIO:
                return new MinioBlobStoreTemplate(blobStoreProperties);
            case LOCAL:
            default:
                return new LocalBlobStoreTemplate(blobStoreProperties);
        }
    }
}