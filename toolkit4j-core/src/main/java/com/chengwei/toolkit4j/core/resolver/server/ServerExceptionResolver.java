package com.chengwei.toolkit4j.core.resolver.server;

import cn.hutool.json.JSONUtil;
import com.chengwei.toolkit4j.core.exception.ServerException;
import com.chengwei.toolkit4j.core.exception.server.ServerIllegalException;
import com.chengwei.toolkit4j.core.exception.server.ServerUnknownException;
import com.chengwei.toolkit4j.core.resolver.AbstractApiExceptionResolver;
import com.chengwei.toolkit4j.core.resolver.ApiCode;
import com.chengwei.toolkit4j.core.resolver.support.ClientInfo;
import com.chengwei.toolkit4j.core.resolver.support.ClientInfoHelper;

import java.sql.SQLException;
import java.util.Optional;

/**
 * 服务端异常解析器，主要解析解析{@link ServerException}和一些框架的服务端异常。
 *
 * @author chengwei
 * @since 2021/12/14
 */
public abstract class ServerExceptionResolver extends AbstractApiExceptionResolver {

    @SuppressWarnings("unchecked")
    @Override
    protected void configure() {
        // 预定一些基础异常
        this.register("1", ServerException.class);
        this.register("2", ServerIllegalException.class);
        this.register("3", ServerUnknownException.class);

        // 由于很多框架断言会抛出这两个异常，因此暂时定义为服务端异常
        this.register("10", IllegalArgumentException.class, IllegalStateException.class);
        // 数据库异常
        this.register("11", SQLException.class);

        // 一些中间件可能会遇到的异常

        // 注册最高级别异常来兜底
        this.register("9999", Exception.class, Throwable.class);
    }

    @Override
    protected Class<? extends ApiCode> getApiCodeClass() {
        return ServerExceptionCode.class;
    }

    @Override
    protected String resolveMsg(Throwable throwable) {
        return "服务可能出现了一些问题";
    }

    @Override
    protected void loggingException(Throwable throwable) {
        // 比默认实现多记录堆栈信息
        Optional<ClientInfo> clientInfoOptional = ClientInfoHelper.tryGetClientInfo();
        if (clientInfoOptional.isPresent()) {
            ClientInfo clientInfo = clientInfoOptional.get();
            log.error("服务端异常：{}。异常信息：{}。客户端信息：{}。", throwable.getClass(), throwable.getMessage(), JSONUtil.toJsonStr(clientInfo), throwable);
        } else {
            log.error("服务端异常：{}。异常信息：{}。", throwable.getClass(), throwable.getMessage(), throwable);
        }
    }
}