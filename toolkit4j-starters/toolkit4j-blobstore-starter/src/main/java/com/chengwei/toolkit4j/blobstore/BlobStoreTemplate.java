package com.chengwei.toolkit4j.blobstore;

import com.chengwei.toolkit4j.core.exception.server.BlobStoreException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.InputStream;

/**
 * 文件存储接口，定义一些常用方法，同时实现了两个生命周期接口用来初始化和销毁资源。
 *
 * @author chengwei
 * @since 2021/12/9
 */
public interface BlobStoreTemplate extends InitializingBean, DisposableBean {

    /**
     * 上传文件
     *
     * @param blobKey     文件键
     * @param inputStream 输入流
     * @throws BlobStoreException 上传文件失败
     */
    void upload(String blobKey, InputStream inputStream) throws BlobStoreException;

    /**
     * 下载文件
     *
     * @param blobKey 文件键
     * @return 输入流
     * @throws BlobStoreException 下载文件失败
     */
    InputStream download(String blobKey) throws BlobStoreException;

    /**
     * 删除文件
     *
     * @param blobKey 文件键
     * @throws BlobStoreException 删除文件失败
     */
    void delete(String blobKey) throws BlobStoreException;

    /**
     * 生成一个带有签名的地址用来访问私有读权限的文件，并设置一定的过期时间。
     *
     * @param blobKey 文件键
     * @return 文件签名地址（blobKey + "?" + presignedStr）
     */
    String generatePresignedUrl(String blobKey);
}