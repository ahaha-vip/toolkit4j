package com.chengwei.toolkit4j.security.auth;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.chengwei.toolkit4j.core.api.ApiResult;
import com.chengwei.toolkit4j.core.exception.client.UnauthenticatedException;
import com.chengwei.toolkit4j.core.resolver.ApiExceptionResolver;
import com.chengwei.toolkit4j.core.resolver.DefaultApiExceptionResolver;
import com.chengwei.toolkit4j.security.ShiroConstants;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Optional;


/**
 * 基于token的认证过滤器
 *
 * @author chengwei
 * @since 2021/12/10
 */
public class AuthenticationTokenFilter extends AuthenticatingFilter {

    private final ApiExceptionResolver apiExceptionResolver = new DefaultApiExceptionResolver();

    /**
     * 尝试从请求头中获取令牌后认证，认证通过则放行，否则直接响应客户端异常信息。
     * {@link AccessControlFilter#onAccessDenied(ServletRequest, ServletResponse, Object)}
     *
     * @param request  请求
     * @param response 响应
     * @return true-认证通过，false-认证失败
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        try {
            return executeLogin(request, response);
        } catch (Throwable t) {
            // 将异常拆包后由异常处理器解析
            Throwable throwable = Optional.ofNullable(t.getCause()).orElse(t);
            ApiResult<Object> apiResult = apiExceptionResolver.resolve(throwable).orElse(null);

            // 响应客户端
            response.setCharacterEncoding(CharsetUtil.defaultCharsetName());
            ServletUtil.write((HttpServletResponse) response, JSONUtil.toJsonStr(apiResult), ContentType.JSON.getValue());
            return false;
        }
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws AuthenticationException {
        Subject subject = getSubject(request, response);
        AuthenticationToken authenticationToken = createToken(request, response);
        subject.login(authenticationToken);
        return true;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        // 解析请求头中的token
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String accessToken = ServletUtil.getHeader(httpServletRequest, ShiroConstants.AUTHENTICATION_REQUEST_HEADER, Charset.defaultCharset());

        // 组装认证凭据
        return Optional.ofNullable(accessToken)
                .map(HeaderAuthenticationToken::new)
                .orElseThrow(() -> new UnauthenticatedException("认证令牌为空"));
    }
}