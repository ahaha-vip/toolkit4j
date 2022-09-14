package com.chengwei.toolkit4j.core.resolver;

import cn.hutool.core.lang.Assert;
import com.chengwei.toolkit4j.core.api.ApiResult;
import com.chengwei.toolkit4j.core.resolver.business.BusinessExceptionResolver;
import com.chengwei.toolkit4j.core.resolver.client.ClientExceptionResolver;
import com.chengwei.toolkit4j.core.resolver.server.ServerExceptionResolver;

import java.util.Optional;

/**
 * 默认的异常解析器实现。通过组合其他各种解析器来构建最终的接口响应体。
 *
 * @author chengwei
 * @since 2021/12/15
 */
public class DefaultApiExceptionResolver implements ApiExceptionResolver {

    /**
     * 客户端异常解析器
     */
    private ApiExceptionResolver clientExceptionResolver = new ClientExceptionResolver() {
    };

    /**
     * 业务异常解析器
     */
    private ApiExceptionResolver businessExceptionResolver = new BusinessExceptionResolver() {
    };

    /**
     * 服务端异常解析器
     */
    private ApiExceptionResolver serverExceptionResolver = new ServerExceptionResolver() {
    };

    /**
     * 提供一个方法供扩展的客户端异常解析器
     *
     * @param clientExceptionResolver 客户端异常解析器
     */
    @SuppressWarnings("unused")
    public void setClientExceptionResolver(ClientExceptionResolver clientExceptionResolver) {
        Assert.notNull(clientExceptionResolver, "客户端异常解析器不能为空");
        this.clientExceptionResolver = clientExceptionResolver;
    }

    /**
     * 提供一个方法供自定义业务异常解析器
     *
     * @param businessExceptionResolver 业务异常解析器
     */
    @SuppressWarnings("unused")
    public void setBusinessExceptionResolver(BusinessExceptionResolver businessExceptionResolver) {
        Assert.notNull(businessExceptionResolver, "业务异常解析器不能为空");
        this.businessExceptionResolver = businessExceptionResolver;
    }

    /**
     * 提供一个方法供扩展的服务端异常解析器
     *
     * @param serverExceptionResolver 服务端异常解析器
     */
    @SuppressWarnings("unused")
    public void setServerExceptionResolver(ServerExceptionResolver serverExceptionResolver) {
        Assert.notNull(serverExceptionResolver, "服务端异常解析器不能为空");
        this.serverExceptionResolver = serverExceptionResolver;
    }

    @Override
    public Optional<ApiResult<Object>> resolve(Throwable throwable) {
        // 首先由客户端解析器处理
        Optional<ApiResult<Object>> clientApiResult = clientExceptionResolver.resolve(throwable);
        if (clientApiResult.isPresent()) {
            return clientApiResult;
        }

        // 若解析为空，则由业务解析器处理
        Optional<ApiResult<Object>> serverApiResult = businessExceptionResolver.resolve(throwable);
        if (serverApiResult.isPresent()) {
            return serverApiResult;
        }

        // 若均未解析到，则由服务端解析器兜底
        return serverExceptionResolver.resolve(throwable);
    }
}