package com.chengwei.toolkit4j.core.resolver;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 接口响应码基类
 *
 * @author chengwei
 * @since 2021/12/14
 */
public abstract class AbstractApiCode implements ApiCode {

    /**
     * 状态码长度
     */
    private static final int CODE_LENGTH = 4;

    /**
     * 填充字符
     */
    private static final char PAD_CHAR = '0';

    /**
     * 响应码
     */
    private final String code;

    public AbstractApiCode(String code) {
        Assert.notEmpty(code, "状态码不能为空");
        Assert.state(code.length() <= CODE_LENGTH, "状态码不能超过{}位", CODE_LENGTH);

        this.code = getCodePrefix() + StrUtil.padPre(code, CODE_LENGTH, PAD_CHAR);
    }

    /**
     * 获取响应码前缀
     *
     * @return 响应码前缀
     */
    protected abstract String getCodePrefix();

    @Override
    public String getCode() {
        return code;
    }
}