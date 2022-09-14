package com.chengwei.toolkit4j.blobstore.aliyun;

import lombok.Getter;
import lombok.Setter;

/**
 * 阿里云存储配置
 *
 * @author chengwei
 * @since 2021/12/9
 */
@Getter
@Setter
public class AliyunBlobStoreProperties {

    /**
     * OSS服务端地址
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
     * 桶名称，默认为 blobstore。规范：<a href="https://www.alibabacloud.com/help/zh/doc-detail/31827.htm">...</a>
     */
    private String bucket = "blobstore";
}