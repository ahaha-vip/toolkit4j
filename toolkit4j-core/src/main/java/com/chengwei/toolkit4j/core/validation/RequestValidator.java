package com.chengwei.toolkit4j.core.validation;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.chengwei.toolkit4j.core.exception.client.RequestValidationException;

import java.util.Date;

/**
 * 请求参数校验
 *
 * @author chengwei
 * @since 2022/3/30
 */
public interface RequestValidator {

    /**
     * 请求参数校验
     *
     * @throws RequestValidationException 校验不通过时抛出
     */
    void validate() throws RequestValidationException;

    /**
     * 校验手机号
     *
     * @param mobile 手机号（中国）
     */
    default void validateMobile(String mobile) {
        boolean isMobile = Validator.isMobile(mobile);
        Assert.isTrue(isMobile, () -> new RequestValidationException("无效的手机号"));
    }

    /**
     * 校验身份证号
     *
     * @param idCardNo 身份证号，支持港澳台
     */
    default void validateIdCardNo(String idCardNo) {
        boolean isIdCardNo = Validator.isCitizenId(idCardNo);
        Assert.isTrue(isIdCardNo, () -> new RequestValidationException("无效的身份证号"));
    }

    /**
     * 校验时间区间
     *
     * @param beginTime 起始时间
     * @param endTime   结束时间
     */
    default void validateDateRange(Date beginTime, Date endTime) {
        int dateCompare = DateUtil.compare(beginTime, endTime);
        Assert.isTrue(dateCompare < 0, () -> new RequestValidationException("起始时间必须在结束时间之前"));
    }

    /**
     * 校验有效期区间
     * - 生效日期必须小于过期日期
     * - 生效日期必须小于当前日期
     * - 过期日期必须大于当前日期
     *
     * @param effectiveDate  生效日期
     * @param expirationDate 过期日期
     */
    default void validateEffectiveDateRange(Date effectiveDate, Date expirationDate) {
        int dateCompare = DateUtil.compare(effectiveDate, expirationDate);
        Assert.isTrue(dateCompare < 0, () -> new RequestValidationException("生效日期必须在过期日期之前"));

        int effectiveCompare = DateUtil.compare(effectiveDate, DateUtil.date());
        Assert.isTrue(effectiveCompare < 0, () -> new RequestValidationException("证件暂未生效"));

        int expirationCompare = DateUtil.compare(DateUtil.date(), expirationDate);
        Assert.isTrue(expirationCompare < 0, () -> new RequestValidationException("证件已过期"));
    }

    /**
     * 文件路径格式
     */
    String FILE_PATH_FORMAT = "yyyy/MM/dd/";

    /**
     * 校验文件路径的有效性，只做简单的正则校验。
     *
     * @param filePath 文件路径
     */
    default void validateFilePath(String filePath) {
        if (StrUtil.isNotEmpty(filePath)) {
            try {
                // 校验文件路径前缀
                String filePathPrefix = StrUtil.sub(filePath, 0, FILE_PATH_FORMAT.length());
                DateUtil.parse(filePathPrefix, FILE_PATH_FORMAT);

                // 校验文件名
                String fileName = StrUtil.removePrefix(filePath, filePathPrefix);
                Assert.notEmpty(fileName);

                // 校验文件扩展名
                String fileSuffix = FileUtil.getSuffix(fileName);
                Assert.notEmpty(fileSuffix);
            } catch (Exception e) {
                throw new RequestValidationException("无效的文件路径");
            }
        }
    }
}
