package com.chengwei.toolkit4j.blobstore.local;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.chengwei.toolkit4j.blobstore.AbstractBlobStoreTemplate;
import com.chengwei.toolkit4j.blobstore.BlobStoreProperties;
import com.chengwei.toolkit4j.core.exception.client.FileNotExistException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 本地存储
 *
 * @author chengwei
 * @since 2021/12/9
 */
public class LocalBlobStoreTemplate extends AbstractBlobStoreTemplate {

    /**
     * 文件根目录
     */
    private String storageRootPath;

    public LocalBlobStoreTemplate(BlobStoreProperties blobStoreProperties) {
        super(blobStoreProperties);
    }

    @Override
    protected void doUpload(String blobKey, InputStream inputStream) {
        String fullPath = this.getFullPath(blobKey);
        File file = FileUtil.touch(fullPath);
        FileUtil.writeFromStream(inputStream, file);
    }

    @Override
    protected InputStream doDownload(String blobKey) {
        String fullPath = this.getFullPath(blobKey);
        File file = FileUtil.newFile(fullPath);
        try {
            return FileUtil.getInputStream(file);
        } catch (IORuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException) {
                throw new FileNotExistException(cause);
            }
            throw e;
        }
    }

    @Override
    protected void doDelete(String blobKey) {
        String fullPath = this.getFullPath(blobKey);
        FileUtil.del(fullPath);
    }

    @Override
    protected String doGeneratePresignedUrl(String blobKey, int expireMinutes) {
        return blobKey;
    }

    /**
     * 获取文件绝对路径
     *
     * @param blobKey 存储键
     * @return 文件绝对路径
     */
    private String getFullPath(String blobKey) {
        return StrUtil.concat(true, storageRootPath, blobKey);
    }

    @Override
    protected void initialize(BlobStoreProperties blobStoreProperties) {
        LocalBlobStoreProperties local = blobStoreProperties.getLocal();
        Assert.notNull(local, "本地存储配置不能为空");

        String storageRootPath = local.getStorageRootPath();
        Assert.notEmpty(storageRootPath, "文件根目录不能为空");

        this.storageRootPath = StrUtil.appendIfMissing(storageRootPath, "/");

        this.initStorageRootPath();
    }

    /**
     * 初始化根目录
     */
    private void initStorageRootPath() {
        boolean exist = FileUtil.exist(storageRootPath);
        if (!exist) {
            FileUtil.mkdir(storageRootPath);
        }
    }

    @Override
    protected void shutdown(BlobStoreProperties blobStoreProperties) {
        // noop
    }
}