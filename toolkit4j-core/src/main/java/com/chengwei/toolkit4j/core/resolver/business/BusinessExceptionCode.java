package com.chengwei.toolkit4j.core.resolver.business;


import com.chengwei.toolkit4j.core.resolver.AbstractApiCode;

/**
 * 业务异常码
 *
 * @author chengwei
 * @since 2021/12/15
 */
public class BusinessExceptionCode extends AbstractApiCode {

    /**
     * 客户端响应码前缀
     */
    public static final String BUSINESS_CODE_PREFIX = "B";

    public BusinessExceptionCode(String code) {
        super(code);
    }

    @Override
    protected String getCodePrefix() {
        return BUSINESS_CODE_PREFIX;
    }
}