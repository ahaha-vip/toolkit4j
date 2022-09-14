package com.chengwei.toolkit4j.core.exception;

import com.chengwei.toolkit4j.core.resolver.business.BusinessExceptionResolver;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常基类，主要增加扩展性。默认异常信息直接抛出客户端，也可自定义。
 * 业务系统自行定义业务中的异常（如有必要），然后扩展{@link BusinessExceptionResolver}配置映射策略。
 *
 * @author chengwei
 * @since 2021/12/15
 */
@Getter
@Setter
public abstract class BusinessException extends TemplatedException {

    /**
     * 业务异常数据
     */
    protected Object data;

    public BusinessException() {
    }

    public BusinessException(String message, Object... args) {
        super(message, args);
    }

    public BusinessException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message, Object... args) {
        super(cause, enableSuppression, writableStackTrace, message, args);
    }
}