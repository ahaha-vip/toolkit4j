package com.chengwei.toolkit4j.core.resolver.client;

import com.chengwei.toolkit4j.core.exception.ClientException;
import com.chengwei.toolkit4j.core.exception.client.*;
import com.chengwei.toolkit4j.core.exception.server.BlobStoreException;
import com.chengwei.toolkit4j.core.resolver.AbstractApiExceptionResolver;
import com.chengwei.toolkit4j.core.resolver.ApiCode;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

/**
 * 客户端异常解析器，主要解析自定义的客户端异常和一些框架里面的客户端异常
 * 自定义异常：{@link ClientException}及其子类
 *
 * @author chengwei
 * @since 2021/12/14
 */
public abstract class ClientExceptionResolver extends AbstractApiExceptionResolver {

    @SuppressWarnings("unchecked")
    @Override
    protected void configure() {
        // 一些基础异常
        this.register("1", ClientException.class);
        this.register("2", ClientIllegalException.class);
        this.register("3", ServiceNotFoundException.class);
        this.register("4", ServiceUnavailableException.class);
        this.register("5", ServiceTimeoutException.class);

        // 认证授权相关异常
        // 未认证异常
        this.register("10", UnauthenticatedException.class, AuthenticationException.class);
        // 未授权异常
        this.register("11", PermissionDeniedException.class, UnauthorizedException.class);
        // 登录校验异常
        this.register("12", BadCredentialsException.class, CredentialsException.class);
        // 账号异常
        this.register("13", IllegalAccountException.class, AccountException.class);
        // 刷新令牌异常
        this.register("14", InvalidRefreshTokenException.class);

        // 并发相关的异常
        this.register("20", OperationConflictException.class);
        this.register("21", AccessLimitationException.class);

        // 参数校验相关异常
        this.register("30", ValidationException.class, MethodArgumentNotValidException.class, BindException.class, RequestValidationException.class);
        this.register("31", HttpMessageNotReadableException.class, MissingPathVariableException.class);
        this.register("32", HttpRequestMethodNotSupportedException.class);
        this.register("33", MaxUploadSizeExceededException.class);
        this.register("34", HttpMediaTypeNotSupportedException.class);

        // 文件不存在
        this.register("40", FileNotExistException.class);
        this.register("41", BlobStoreException.class);
    }

    @Override
    protected Class<? extends ApiCode> getApiCodeClass() {
        return ClientExceptionCode.class;
    }

    @Override
    protected String resolveMsg(Throwable throwable) {
        // javax定义的参数校验异常
        if (throwable instanceof ConstraintViolationException) {
            ConstraintViolationException violationException = (ConstraintViolationException) throwable;
            return violationException.getConstraintViolations().stream().map(item -> item.getPropertyPath() + ":" + item.getMessage()).collect(Collectors.joining(";"));
        }
        if (throwable instanceof ValidationException) {
            // 说明存在一些校验异常待细化解析
            return "请求参数有误";
        }

        // spring定义的一些参数校验异常
        if (throwable instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException argumentNotValidException = (MethodArgumentNotValidException) throwable;
            return argumentNotValidException.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        }
        if (throwable instanceof BindException) {
            BindException bindException = (BindException) throwable;
            return bindException.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        }
        if (throwable instanceof HttpMessageNotReadableException || throwable instanceof MissingPathVariableException) {
            return "请求参数有误";
        }
        if (throwable instanceof MissingServletRequestPartException) {
            return "文件上传参数有误";
        }
        if (throwable instanceof HttpRequestMethodNotSupportedException) {
            return "HTTP请求method有误";
        }
        if (throwable instanceof MaxUploadSizeExceededException) {
            return "文件大小超过限制";
        }
        if (throwable instanceof HttpMediaTypeNotSupportedException) {
            return "不支持的MediaType";
        }

        // shiro定义的一些异常
        if (throwable instanceof UnauthorizedException) {
            return "没有访问权限";
        }

        return super.resolveMsg(throwable);
    }
}