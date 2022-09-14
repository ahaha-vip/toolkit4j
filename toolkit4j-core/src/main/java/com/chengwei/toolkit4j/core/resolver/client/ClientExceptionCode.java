package com.chengwei.toolkit4j.core.resolver.client;


import com.chengwei.toolkit4j.core.resolver.AbstractApiCode;

/**
 * 客户端异常码
 *
 * @author chengwei
 * @since 2021/12/14
 */
public class ClientExceptionCode extends AbstractApiCode {

    /**
     * 客户端响应码前缀
     */
    public static final String CLIENT_CODE_PREFIX = "C";

    public ClientExceptionCode(String code) {
        super(code);
    }

    @Override
    protected String getCodePrefix() {
        return CLIENT_CODE_PREFIX;
    }
}