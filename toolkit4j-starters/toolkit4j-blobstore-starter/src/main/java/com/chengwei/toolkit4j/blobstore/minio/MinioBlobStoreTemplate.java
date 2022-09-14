package com.chengwei.toolkit4j.blobstore.minio;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.net.url.UrlBuilder;
import com.chengwei.toolkit4j.blobstore.AbstractBlobStoreTemplate;
import com.chengwei.toolkit4j.blobstore.BlobStoreProperties;
import com.chengwei.toolkit4j.core.exception.client.FileNotExistException;
import com.chengwei.toolkit4j.core.exception.server.BlobStoreException;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;

import java.io.InputStream;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * minio存储实现
 *
 * @author chengwei
 * @since 2021/12/9
 */
public class MinioBlobStoreTemplate extends AbstractBlobStoreTemplate {

    /**
     * minio客户端
     */
    private MinioClient minioClient;

    /**
     * 桶名
     */
    private String bucket;

    public MinioBlobStoreTemplate(BlobStoreProperties blobStoreProperties) {
        super(blobStoreProperties);
    }

    @Override
    protected void doUpload(String blobKey, InputStream inputStream) throws Exception {
        String contentType = this.getContentType(blobKey);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucket)
                .object(blobKey)
                .stream(inputStream, inputStream.available(), -1)
                .contentType(contentType)
                .build();
        minioClient.putObject(args);
    }

    @Override
    protected InputStream doDownload(String blobKey) throws Exception {
        try {
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(blobKey)
                    .build();
            return minioClient.getObject(args);
        } catch (ErrorResponseException e) {
            throw new FileNotExistException(e);
        }
    }

    @Override
    protected void doDelete(String blobKey) throws Exception {
        RemoveObjectArgs args = RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(blobKey)
                .build();
        minioClient.removeObject(args);
    }

    @Override
    protected String doGeneratePresignedUrl(String blobKey, int expireMinutes) throws Exception {
        GetPresignedObjectUrlArgs objectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .bucket(bucket)
                .object(blobKey)
                .method(Method.GET)
                .expiry(expireMinutes, TimeUnit.MINUTES)
                .build();
        String presignedObjectUrl = minioClient.getPresignedObjectUrl(objectUrlArgs);
        String presignedStr = UrlBuilder.of(presignedObjectUrl).getQueryStr();
        return blobKey + "?" + presignedStr;
    }

    @Override
    protected void initialize(BlobStoreProperties blobStoreProperties) {
        MinioBlobStoreProperties minio = blobStoreProperties.getMinio();
        Assert.notNull(minio, "minio配置不能为空");
        Assert.notEmpty(minio.getEndpoint(), "minio服务端地址不能为空");
        Assert.notEmpty(minio.getAccessKey(), "accessKey不能为空");
        Assert.notEmpty(minio.getSecretKey(), "secretKey不能为空");
        Assert.notEmpty(minio.getBucket(), "bucket不能为空");

        this.bucket = minio.getBucket();
        this.minioClient = MinioClient.builder()
                .endpoint(minio.getEndpoint())
                .credentials(minio.getAccessKey(), minio.getSecretKey())
                .build();
        this.minioClient.setTimeout(Duration.ofSeconds(minio.getConnectTimeout()).toMillis(),
                Duration.ofSeconds(minio.getReadTimeout()).toMillis(),
                Duration.ofSeconds(minio.getWriteTimeout()).toMillis());

        this.initBucket();
    }

    /**
     * 初始化桶
     */
    private void initBucket() {
        try {
            // 检查是否存在
            BucketExistsArgs existsArgs = BucketExistsArgs.builder()
                    .bucket(bucket)
                    .build();
            boolean exists = minioClient.bucketExists(existsArgs);

            // 不存在即创建
            if (!exists) {
                MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
                        .bucket(bucket)
                        .build();
                minioClient.makeBucket(makeBucketArgs);
            }
        } catch (Exception e) {
            log.error("初始化桶失败", e);
            throw new BlobStoreException(e);
        }
    }

    @Override
    protected void shutdown(BlobStoreProperties blobStoreProperties) {
        // noop
    }
}