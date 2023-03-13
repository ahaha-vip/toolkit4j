package com.chengwei.toolkit4j.core.resolver.support;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 获取客户端信息的帮助类
 *
 * @author chengwei
 * @since 2021/12/15
 */
public class ClientInfoHelper {

    /**
     * 尝试获取客户端信息
     *
     * @return 客户端信息
     */
    public static Optional<ClientInfo> tryGetClientInfo() {
        try {
            // 尝试从线程上下文获取请求对象
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = Optional.ofNullable(requestAttributes)
                    .map(ServletRequestAttributes::getRequest)
                    .orElseThrow(() -> new IllegalStateException("获取请求失败"));

            // 解析客户端信息
            String requestPath = getRequestPath(request);
            String httpMethod = request.getMethod();
            String clientIp = ServletUtil.getClientIP(request);
            ClientInfo clientInfo = ClientInfo.builder()
                    .requestPath(requestPath)
                    .httpMethod(httpMethod)
                    .clientIp(clientIp)
                    .build();
            tryFillingLoginInfo(clientInfo);
            return Optional.of(clientInfo);
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    /**
     * 获取请求路径
     *
     * @param request 请求
     * @return 请求路径
     */
    public static String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();
        String pathInfo = request.getPathInfo();

        if (pathInfo != null) {
            url = StrUtil.isNotEmpty(url) ? url + pathInfo : pathInfo;
        }

        return url;
    }

    /**
     * 补充登录信息
     *
     * @param clientInfo 客户端信息
     */
    private static void tryFillingLoginInfo(ClientInfo clientInfo) {
        try {
            Subject subject = SecurityUtils.getSubject();
            boolean authenticated = subject.isAuthenticated();
            if (!authenticated) {
                return;
            }

            clientInfo.setAuthenticated(true);
            clientInfo.setLoginPrincipal(subject.getPrincipal().toString());
        } catch (Exception ignored) {
            // noop
        }
    }
}