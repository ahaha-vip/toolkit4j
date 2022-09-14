package com.chengwei.toolkit4j.blobstore.aliyun;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.*;
import com.chengwei.toolkit4j.blobstore.AbstractBlobStoreTemplate;
import com.chengwei.toolkit4j.blobstore.BlobStoreProperties;
import com.chengwei.toolkit4j.core.exception.client.FileNotExistException;

import java.io.InputStream;
import java.net.URL;

/**
 * 阿里云存储
 *
 * @author chengwei
 * @since 2021/12/9
 */
public class AliyunBlobStoreTemplate extends AbstractBlobStoreTemplate {

    /**
     * 当文件不存在时，阿里云封装的响应标识
     */
    private static final String NO_SUCH_KEY = "NoSuchKey";

    /**
     * 客户端
     */
    private OSS ossClient;

    /**
     * 桶名
     */
    private String bucket;

    public AliyunBlobStoreTemplate(BlobStoreProperties blobStoreProperties) {
        super(blobStoreProperties);
    }

    @Override
    protected void doUpload(String blobKey, InputStream inputStream) {
        ossClient.putObject(bucket, blobKey, inputStream);
    }

    @Override
    protected InputStream doDownload(String blobKey) {
        try {
            return ossClient.getObject(bucket, blobKey).getObjectContent();
        } catch (OSSException e) {
            String errorCode = e.getErrorCode();
            if (StrUtil.equals(errorCode, NO_SUCH_KEY)) {
                throw new FileNotExistException(e);
            }
            throw e;
        }
    }

    @Override
    protected void doDelete(String blobKey) {
        ossClient.deleteObject(bucket, blobKey);
    }

    @Override
    protected String doGeneratePresignedUrl(String blobKey, int expireMinutes) {
        DateTime expireTime = DateUtil.offsetMinute(DateUtil.date(), expireMinutes);
        URL url = ossClient.generatePresignedUrl(bucket, blobKey, expireTime, HttpMethod.GET);
        return blobKey + "?" + url.getQuery();
    }

    @Override
    protected void initialize(BlobStoreProperties blobStoreProperties) {
        // 参数校验
        AliyunBlobStoreProperties aliyun = blobStoreProperties.getAliyun();
        Assert.notNull(aliyun, "阿里云存储配置不能为空");
        String endpoint = aliyun.getEndpoint();
        String accessKey = aliyun.getAccessKey();
        String secretKey = aliyun.getSecretKey();
        String bucket = aliyun.getBucket();
        Assert.notEmpty(endpoint, "阿里云存储服务端地址不能为空");
        Assert.notEmpty(accessKey, "accessKey不能为空");
        Assert.notEmpty(secretKey, "secretKey不能为空");
        Assert.notEmpty(bucket, "bucket不能为空");

        // 客户端
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setVerifySSLEnable(false);
        this.ossClient = new OSSClientBuilder().build(endpoint, accessKey, secretKey, clientBuilderConfiguration);
        this.bucket = bucket;

        // 初始化桶
        this.initBucket();
    }

    /**
     * 初始化桶
     */
    private void initBucket() {
        // 检查是否存在
        boolean exists = ossClient.doesBucketExist(this.bucket);

        // 不存在即创建
        if (!exists) {
            ossClient.createBucket(bucket);
        }
    }

    @Override
    protected void shutdown(BlobStoreProperties blobStoreProperties) {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }
}