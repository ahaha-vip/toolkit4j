package com.chengwei.toolkit4j.core.resolver;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import com.chengwei.toolkit4j.core.api.ApiResult;
import com.chengwei.toolkit4j.core.resolver.support.ClientInfo;
import com.chengwei.toolkit4j.core.resolver.support.ClientInfoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 一个抽象的异常解析器
 *
 * @author chengwei
 * @since 2021/12/14
 */
public abstract class AbstractApiExceptionResolver implements ApiExceptionResolver {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 注册管理
     */
    private final Map<String, Optional<ApiCode>> registry = new HashMap<>();

    public AbstractApiExceptionResolver() {
        this.configure();
    }

    /**
     * 提供一个钩子函数由子类来实现，供其完成一些初始化的配置。
     */
    protected abstract void configure();

    /**
     * 一个公共方法，用来注册响应码及对应的异常类
     *
     * @param code    响应码，不超过4位
     * @param classes 异常类
     */
    @SuppressWarnings("unchecked")
    protected void register(String code, Class<? extends Throwable>... classes) {
        Assert.notEmpty(code, "接口响应码不能为空");
        Assert.notEmpty(classes, "异常类不能为空");

        // 获取子类的类型，然后通过反射创建对象
        Class<? extends ApiCode> apiCodeClass = getApiCodeClass();
        ApiCode apiCode = ReflectUtil.newInstance(apiCodeClass, code);

        // 注册响应码与异常类
        register(apiCode, classes);
    }

    /**
     * 获取{@link ApiCode}的子类名
     *
     * @return 类名
     */
    protected abstract Class<? extends ApiCode> getApiCodeClass();

    /**
     * 一个公共方法，用来注册响应码及对应的异常类
     *
     * @param apiCode 接口响应码
     * @param classes 异常类
     */
    @SuppressWarnings("unchecked")
    private void register(ApiCode apiCode, Class<? extends Throwable>... classes) {
        // 注册异常的类名与响应码的映射关系
        Map<String, Optional<ApiCode>> registeredData = Arrays.stream(classes)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Class::getName, object -> Optional.of(apiCode)));
        registry.putAll(registeredData);
    }

    @Override
    public Optional<ApiResult<Object>> resolve(Throwable throwable) {
        // 如果异常为空，则返回空
        if (ObjectUtil.isNull(throwable)) {
            return Optional.empty();
        }

        // 解析异常码
        ApiCode apiCode = resolveCode(throwable);

        // 如果依然未找到，则返回空
        if (ObjectUtil.isNull(apiCode)) {
            return Optional.empty();
        }

        // 获取异常信息
        String msg = resolveMsg(throwable);

        // 异常数据
        Object data = resolveData(throwable);

        // 记录异常日志
        loggingException(throwable);

        // 组装响应体
        ApiResult<Object> apiResult = new ApiResult<>();
        apiResult.setCode(apiCode.getCode());
        apiResult.setMsg(msg);
        apiResult.setData(data);
        return Optional.of(apiResult);
    }

    /**
     * 解析异常码
     *
     * @param throwable 异常
     * @return 异常码
     */
    protected ApiCode resolveCode(Throwable throwable) {
        Class<? extends Throwable> clazz = throwable.getClass();

        // 通过类名解析
        return registry.computeIfAbsent(clazz.getName(), s -> {
            // 若未找到，则通过父级类名寻找
            return getParentApiCode(clazz);
        }).orElse(null);
    }

    /**
     * 解析异常信息。默认直接返回异常信息，对于特别的异常由子类自行解析。
     *
     * @param throwable 异常
     * @return 异常信息
     */
    protected String resolveMsg(Throwable throwable) {
        return throwable.getMessage();
    }

    /**
     * 解析异常数据，默认返回空。
     *
     * @param throwable 异常
     * @return 异常数据
     */
    protected Object resolveData(Throwable throwable) {
        return null;
    }

    /**
     * 通过父级类名寻找注册的响应码
     *
     * @param clazz 异常类
     * @return 响应码
     */
    @SuppressWarnings("unchecked")
    private Optional<ApiCode> getParentApiCode(Class<? extends Throwable> clazz) {
        Class<? extends Throwable> superclass = (Class<? extends Throwable>) clazz.getSuperclass();
        if (ObjectUtil.equals(superclass, Object.class)) {
            return Optional.empty();
        }
        return registry.computeIfAbsent(superclass.getName(), s -> getParentApiCode(superclass));
    }

    /**
     * 记录异常日志，默认记录客户端信息和异常信息，而不记录堆栈信息。
     *
     * @param throwable 异常
     */
    protected void loggingException(Throwable throwable) {
        Optional<ClientInfo> optional = ClientInfoHelper.tryGetClientInfo();
        if (optional.isPresent()) {
            ClientInfo clientInfo = optional.get();
            log.error("异常类型：{}。异常信息：{}。客户端信息：{}。",
                    ClassUtil.getClassName(throwable, true),
                    throwable.getMessage(),
                    JSONUtil.toJsonStr(clientInfo));
        } else {
            log.error("异常类型：{}。异常信息：{}。",
                    ClassUtil.getClassName(throwable, true),
                    throwable.getMessage());
        }
    }
}