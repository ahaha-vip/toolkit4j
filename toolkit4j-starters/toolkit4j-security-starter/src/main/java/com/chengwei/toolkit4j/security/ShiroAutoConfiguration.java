package com.chengwei.toolkit4j.security;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.chengwei.toolkit4j.security.auth.AuthenticationEventHandler;
import com.chengwei.toolkit4j.security.auth.AuthenticationTokenFilter;
import com.chengwei.toolkit4j.security.auth.ModularAuthenticationStrategy;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.servlet.Filter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;


/**
 * shiro自动化配置
 *
 * @author chengwei
 * @see <a href="http://shiro.apache.org/index.html">shiro官方文档</a>
 * @since 2021/12/10
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroAutoConfiguration {

    /**
     * springboot整合shiro的核心：
     * 1.不将ShiroFilterFactoryBean注入IOC容器，修改为直接创建SpringShiroFilter并注入IOC。
     * 2.原因为ShiroFilterFactoryBean实现了BeanPostProcessor接口，初始化较早，导致它所依赖的所有Bean都得提前初始化，因此这些Bean都不能进行一些后置的处理，比如AOP代理等。
     * 常见日志为：xxx is not eligible for getting processed by all BeanPostProcessors(for example: not eligible for auto-proxying)。
     *
     * @param shiroProperties shiro配置
     * @param securityManager 安全管理器
     * @return 过滤器
     * @throws Exception 初始化异常时抛出
     */
    @ConditionalOnProperty(prefix = "shiro", name = "enabled", havingValue = "true")
    @Bean
    public AbstractShiroFilter shiroFilterFactoryBean(ShiroProperties shiroProperties, SecurityManager securityManager) throws Exception {
        long expireTime = shiroProperties.getExpireTime();
        Assert.isTrue(expireTime >= 30, "令牌过期时间不能低于30分钟");

        // 自定义过滤器
        Map<String, Filter> filters = getFilters();

        // 过滤器链定义
        ShiroFilterChainDefinition filterChainDefinition = getFilterChainDefinition(shiroProperties.getAnons());

        // 注入IOC
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilters(filters);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinition.getFilterChainMap());
        return shiroFilterFactoryBean.getObject();
    }

    /**
     * 安全管理器，shiro核心组件，负责整合其他组件。
     *
     * @param realms    由使用者自行实现，专注于认证和授权
     * @param listeners 由使用者自行实现，监听一些事件
     * @return 安全管理器
     */
    @Bean
    public SecurityManager securityManager(@Autowired List<Realm> realms, @Autowired List<AuthenticationListener> listeners) {
        // subject存储器
        SubjectDAO subjectDAO = getSubjectDAO();

        // 自定义认证策略
        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new ModularAuthenticationStrategy());
        modularRealmAuthenticator.setAuthenticationListeners(listeners);

        // 组装安全管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(modularRealmAuthenticator);
        securityManager.setSubjectDAO(subjectDAO);
        securityManager.setRealms(CollUtil.defaultIfEmpty(realms, CollUtil.newArrayList(new SimpleAccountRealm())));
        return securityManager;
    }

    /**
     * 自定义认证监听器
     *
     * @param executor 线程池
     * @param handlers 事件处理器
     * @return 监听器
     */
    @Bean
    public AuthenticationListener authenticationListener(
            @Autowired(required = false) @Qualifier(ShiroConstants.AUTHENTICATION_EVENT_HANDLER_THREAD_POOL) Executor executor,
            @Autowired(required = false) List<AuthenticationEventHandler> handlers) {
        return new com.chengwei.toolkit4j.security.auth.AuthenticationListener(executor, handlers);
    }

    /**
     * 自定义过滤器
     *
     * @return 过滤器
     */
    private Map<String, Filter> getFilters() {
        return MapUtil.<String, Filter>builder().put(ShiroConstants.AUTHENTICATION_FILTER_NAME, new AuthenticationTokenFilter()).build();
    }

    /**
     * subject存储器，设置禁用session。
     *
     * @return subject存储器
     */
    private SubjectDAO getSubjectDAO() {
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        return subjectDAO;
    }

    /**
     * 过滤器链定义，配置请求资源路径对应的过滤器{@link DefaultFilter}枚举名，自定义过滤器同理。
     * 常见过滤器配置：
     * 1.匿名资源，即认证与否皆可访问，如：登录接口 /login/** = anon
     * 2.非匿名资源，即必须认证通过后才能访问，如：个人资料 /profile/** = authentication
     *
     * @param anonsPaths 匿名资源
     * @return 过滤器链定义
     * @see <a href= "http://shiro.apache.org/web.html#filter-chain-definitions">配置方式参考</a>
     */
    private ShiroFilterChainDefinition getFilterChainDefinition(Set<String> anonsPaths) {
        DefaultShiroFilterChainDefinition shiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();
        // 匿名资源
        anonsPaths.forEach(path -> shiroFilterChainDefinition.addPathDefinition(path, DefaultFilter.anon.name()));
        // 非匿名资源
        shiroFilterChainDefinition.addPathDefinition("/**", ShiroConstants.AUTHENTICATION_FILTER_NAME);
        return shiroFilterChainDefinition;
    }

    /**
     * 提供基于AOP的授权控制，相关注解{@link org.apache.shiro.authz.annotation}。
     *
     * @param securityManager 安全管理器
     * @return 认证授权切面
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Lazy SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}