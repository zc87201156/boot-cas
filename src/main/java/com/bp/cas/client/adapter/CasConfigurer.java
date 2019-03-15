package com.bp.cas.client.adapter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;


/**
 * 类名称：CasConfigurer
 * 类描述：配置或自定义CAS过滤器接口
 * 开发人：朱水平【Tank】
 * 创建时间：2018/6/20
 * 修改备注：
 *
 * @version 1.0.0
 */
public interface CasConfigurer {

    /**
     * 配置或自定义CAS认证过滤器
     *
     * @param authenticationFilter
     */
    void configureAuthenticationFilter(FilterRegistrationBean authenticationFilter);

    /**
     * 配置或自定义CAS验证过滤器。
     *
     * @param validationFilter
     */
    void configureValidationFilter(FilterRegistrationBean validationFilter);

    /**
     * 配置或自定义CAS http servlet包装过滤器。
     *
     * @param httpServletRequestWrapperFilter
     */
    void configureHttpServletRequestWrapperFilter(FilterRegistrationBean httpServletRequestWrapperFilter);

    /**
     * 配置或自定义CAS断言线程本地过滤器。
     *
     * @param assertionThreadLocalFilter
     */
    void configureAssertionThreadLocalFilter(FilterRegistrationBean assertionThreadLocalFilter);

    /**
     * 配置或自定义CAS单点退出过滤器。
     *
     * @param singleSignOutFilter
     */
    void configureSingleSignOutFilter(FilterRegistrationBean singleSignOutFilter);

    /**
     * 配置或自定义CAS Session失效后过滤器。
     *
     * @param sessionTimeOutFilter
     */
    void configureSessionTimeOutFilter(FilterRegistrationBean sessionTimeOutFilter);

    /**
     * 配置或自定义CAS 设置用户信息配置
     *
     * @param autoSetUserAdapterFilter
     */
    void configureAutoSetUserAdapterFilter(FilterRegistrationBean autoSetUserAdapterFilter);

}
