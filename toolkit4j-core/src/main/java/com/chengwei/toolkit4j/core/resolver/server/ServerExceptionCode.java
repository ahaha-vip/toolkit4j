package com.chengwei.toolkit4j.core.resolver.server;


import com.chengwei.toolkit4j.core.resolver.AbstractApiCode;

/**
 * 服务端异常码
 *
 * @author chengwei
 * @since 2021/12/14
 */
public class ServerExceptionCode extends AbstractApiCode {

    /**
     * 服务端响应码前缀
     */
    public static final String SERVER_CODE_PREFIX = "S";

    public ServerExceptionCode(String code) {
        super(code);
    }

    @Override
    protected String getCodePrefix() {
        return SERVER_CODE_PREFIX;
    }
}