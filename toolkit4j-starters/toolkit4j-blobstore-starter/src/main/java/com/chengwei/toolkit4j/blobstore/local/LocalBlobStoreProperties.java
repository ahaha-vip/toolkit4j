package com.chengwei.toolkit4j.blobstore.local;

import lombok.Getter;
import lombok.Setter;

/**
 * 本地存储配置
 *
 * @author chengwei
 * @since 2021/12/9
 */
@Getter
@Setter
public class LocalBlobStoreProperties {

    /**
     * 文件存储根路径（示例：windows环境 D:/storageRootPath，linux环境 /home/storageRootPath）
     */
    private String storageRootPath;
}