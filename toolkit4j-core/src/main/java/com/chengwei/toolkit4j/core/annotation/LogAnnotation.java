package com.chengwei.toolkit4j.core.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author chengwei
 * @since 2022/3/1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    /**
     * 是否记录请求参数，默认记录。
     *
     * @return true-记录；false-不记录
     */
    boolean logRequestData() default true;

    /**
     * 是否记录响应参数，默认不记录。
     *
     * @return true-记录；false-不记录
     */
    boolean logResponseData() default false;
}