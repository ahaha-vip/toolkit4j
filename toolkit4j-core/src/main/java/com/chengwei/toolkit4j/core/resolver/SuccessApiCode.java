package com.chengwei.toolkit4j.core.resolver;

/**
 * 成功响应码，“00000”
 *
 * @author chengwei
 * @since 2021/12/14
 */
public class SuccessApiCode extends AbstractApiCode {

    /**
     * 成功响应码前缀
     */
    public static final String SUCCESS_CODE_PREFIX = "0";

    /**
     * 成功响应码
     */
    private static final String SUCCESS_CODE = "0000";

    public SuccessApiCode() {
        super(SUCCESS_CODE);
    }

    @Override
    protected String getCodePrefix() {
        return SUCCESS_CODE_PREFIX;
    }
}