package com.chengwei.toolkit4j.core.resolver.business;


import com.chengwei.toolkit4j.core.exception.BusinessException;
import com.chengwei.toolkit4j.core.exception.business.ThirdPartyException;
import com.chengwei.toolkit4j.core.resolver.AbstractApiExceptionResolver;
import com.chengwei.toolkit4j.core.resolver.ApiCode;

/**
 * 业务异常解析器，一个抽象类。业务系统可扩展此解析器，并定义自己的状态码和异常的映射关系。
 *
 * @author chengwei
 * @since 2021/12/15
 */
public abstract class BusinessExceptionResolver extends AbstractApiExceptionResolver {

    @SuppressWarnings("unchecked")
    @Override
    protected void configure() {
        this.register("1", BusinessException.class);
        this.register("2", ThirdPartyException.class);
    }

    @Override
    protected Class<? extends ApiCode> getApiCodeClass() {
        return BusinessExceptionCode.class;
    }

    @Override
    protected String resolveMsg(Throwable throwable) {
        return super.resolveMsg(throwable);
    }

    @Override
    protected Object resolveData(Throwable throwable) {
        BusinessException businessException = (BusinessException) throwable;
        return businessException.getData();
    }
}