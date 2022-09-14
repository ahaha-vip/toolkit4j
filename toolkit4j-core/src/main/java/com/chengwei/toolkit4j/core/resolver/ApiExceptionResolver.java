package com.chengwei.toolkit4j.core.resolver;


import com.chengwei.toolkit4j.core.api.ApiResult;

import java.util.Optional;

/**
 * 接口异常解析器。核心目的是解析异常，构建接口响应体。
 * <p>
 * 接口响应体：{@link ApiResult}
 *
 * @author chengwei
 * @since 2021/12/14
 */
public interface ApiExceptionResolver {

    /**
     * 解析异常并转换为{@link ApiResult}
     *
     * @param throwable 异常
     * @return 响应体
     */
    Optional<ApiResult<Object>> resolve(Throwable throwable);
}