package com.chengwei.toolkit4j.blobstore;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import com.chengwei.toolkit4j.core.exception.client.ClientIllegalException;
import com.chengwei.toolkit4j.core.exception.client.FileNotExistException;
import com.chengwei.toolkit4j.core.exception.server.BlobStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 文件存储抽象实现
 *
 * @author chengwei
 * @since 2021/12/9
 */
public abstract class AbstractBlobStoreTemplate implements BlobStoreTemplate {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final BlobStoreProperties blobStoreProperties;

    /**
     * 分享文件的过期时间，默认30分钟。
     */
    private static final int EXPIRE_MINUTES = 30;

    /**
     * 是否已启动
     */
    private final AtomicBoolean isStarted = new AtomicBoolean(false);

    protected AbstractBlobStoreTemplate(BlobStoreProperties blobStoreProperties) {
        Assert.notNull(blobStoreProperties, "二进制存储属性配置不能为空");
        this.blobStoreProperties = blobStoreProperties;
    }

    @Override
    public void upload(String blobKey, InputStream inputStream) throws BlobStoreException {
        // 参数校验
        Assert.notEmpty(blobKey, () -> new ClientIllegalException("文件键不能为空"));
        Assert.notEmpty(FileUtil.getPrefix(blobKey), () -> new ClientIllegalException("文件名不能为空"));
        Assert.notEmpty(FileUtil.getSuffix(blobKey), () -> new ClientIllegalException("扩展名不能为空"));
        Assert.notNull(inputStream, () -> new BlobStoreException("文件输入流不能为空"));

        // 文件上传
        try {
            this.doUpload(blobKey, inputStream);
        } catch (Exception e) {
            log.error("文件[{}]上传失败", blobKey, e);
            throw new BlobStoreException("文件上传失败");
        }
    }

    @Override
    public InputStream download(String blobKey) throws BlobStoreException {
        // 参数校验
        Assert.notEmpty(blobKey, () -> new ClientIllegalException("文件键不能为空"));

        // 文件下载
        try {
            return this.doDownload(blobKey);
        } catch (FileNotExistException e) {
            log.error("文件[{}]不存在", blobKey);
            throw new FileNotExistException("文件不存在");
        } catch (Exception e) {
            log.error("文件[{}]下载失败", blobKey, e);
            throw new BlobStoreException("文件下载失败");
        }
    }

    @Override
    public void delete(String blobKey) throws BlobStoreException {
        // 参数校验
        Assert.notEmpty(blobKey, () -> new ClientIllegalException("文件键不能为空"));

        // 文件删除
        try {
            this.doDelete(blobKey);
        } catch (Exception e) {
            log.error("文件[{}]删除失败", blobKey, e);
            throw new BlobStoreException("文件删除失败");
        }
    }

    @Override
    public String generatePresignedUrl(String blobKey) {
        return this.generatePresignedUrl(blobKey, EXPIRE_MINUTES);
    }

    @Override
    public String generatePresignedUrl(String blobKey, int expireMinutes) {
        // 参数校验
        Assert.notEmpty(blobKey, () -> new ClientIllegalException("文件键不能为空"));

        // 生成文件签名地址
        try {
            return this.doGeneratePresignedUrl(blobKey, expireMinutes);
        } catch (Exception e) {
            log.error("生成文件[{}]签名地址失败", blobKey, e);
            throw new BlobStoreException("生成文件签名地址失败");
        }
    }

    /**
     * 生成文件签名地址
     *
     * @param blobKey       文件键
     * @param expireMinutes 过期时间（分钟）
     * @return 文件签名地址
     * @throws Exception 签名发生的异常
     */
    protected abstract String doGeneratePresignedUrl(String blobKey, int expireMinutes) throws Exception;

    /**
     * 上传
     *
     * @param blobKey     存储键
     * @param inputStream 输入流
     * @throws Exception 上传过程发生的异常
     */
    protected abstract void doUpload(String blobKey, InputStream inputStream) throws Exception;

    /**
     * 下载
     *
     * @param blobKey 存储键
     * @return 存储键
     * @throws Exception 下载过程发生的异常
     */
    protected abstract InputStream doDownload(String blobKey) throws Exception;

    /**
     * 删除
     *
     * @param blobKey 存储键
     * @throws Exception 删除过程发生的异常
     */
    protected abstract void doDelete(String blobKey) throws Exception;

    @Override
    public void afterPropertiesSet() {
        if (isStarted.compareAndSet(false, true)) {
            log.info("初始化文件存储资源，存储策略：{}", blobStoreProperties.getPolicy());
            this.initialize(blobStoreProperties);
        }
    }

    @Override
    public void destroy() {
        if (isStarted.compareAndSet(true, false)) {
            log.info("释放文件存储资源");
            this.shutdown(blobStoreProperties);
        }
    }

    /**
     * 根据扩展名获取ContentType
     *
     * @param blobKey 存储键
     * @return ContentType
     */
    protected String getContentType(String blobKey) {
        String mimeType = FileUtil.getMimeType(blobKey);
        return Optional.ofNullable(mimeType).orElse("application/octet-stream");
    }

    /**
     * 服务启动时调用，初始化资源。
     *
     * @param blobStoreProperties 配置信息
     */
    protected abstract void initialize(BlobStoreProperties blobStoreProperties);

    /**
     * 服务关闭时调用，释放资源。
     *
     * @param blobStoreProperties 配置信息
     */
    protected abstract void shutdown(BlobStoreProperties blobStoreProperties);
}