package com.chengwei.toolkit4j.core.resolver;

/**
 * 接口响应码。默认由1位标识符和4位状态码拼接而成，总共5位。
 *
 * @author chengwei
 * @since 2021/12/14
 */
public interface ApiCode {

    /**
     * 获取接口响应码
     *
     * @return 响应码
     */
    String getCode();
}
