package com.chengwei.toolkit4j.core.exception;

import com.chengwei.toolkit4j.core.api.ApiResult;
import com.chengwei.toolkit4j.core.resolver.ApiExceptionResolver;
import com.chengwei.toolkit4j.core.resolver.DefaultApiExceptionResolver;
import com.chengwei.toolkit4j.core.resolver.business.BusinessExceptionResolver;
import com.chengwei.toolkit4j.core.resolver.client.ClientExceptionResolver;
import com.chengwei.toolkit4j.core.resolver.server.ServerExceptionResolver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 抽象的异常处理器
 *
 * @author chengwei
 * @since 2021/12/15
 */
public abstract class AbstractExceptionHandler implements InitializingBean {

    /**
     * 异常解析器
     */
    protected ApiExceptionResolver apiExceptionResolver;

    /**
     * 全局异常解析器
     *
     * @param throwable 异常
     * @return 响应体
     */
    @ExceptionHandler(Throwable.class)
    public ApiResult<Object> throwable(Throwable throwable) {
        return apiExceptionResolver.resolve(throwable).orElse(null);
    }

    @Override
    public void afterPropertiesSet() {
        DefaultApiExceptionResolver apiExceptionResolver = new DefaultApiExceptionResolver();
        apiExceptionResolver.setClientExceptionResolver(getClientExceptionResolver());
        apiExceptionResolver.setBusinessExceptionResolver(getBusinessExceptionResolver());
        apiExceptionResolver.setServerExceptionResolver(getServerExceptionResolver());
        this.apiExceptionResolver = apiExceptionResolver;
    }

    /**
     * 提供一个接口供子类扩展
     *
     * @return 客户端异常解析器
     */
    protected ClientExceptionResolver getClientExceptionResolver() {
        return new ClientExceptionResolver() {
            @Override
            protected void configure() {
                super.configure();
            }
        };
    }

    /**
     * 提供一个接口供子类扩展
     *
     * @return 业务异常解析器
     */
    protected BusinessExceptionResolver getBusinessExceptionResolver() {
        return new BusinessExceptionResolver() {
            @Override
            protected void configure() {
                super.configure();
            }
        };
    }

    /**
     * 提供一个接口供子类扩展
     *
     * @return 服务端异常解析器
     */
    protected ServerExceptionResolver getServerExceptionResolver() {
        return new ServerExceptionResolver() {
            @Override
            protected void configure() {
                super.configure();
            }
        };
    }
}