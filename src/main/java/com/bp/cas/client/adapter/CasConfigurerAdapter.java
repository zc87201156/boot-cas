package com.bp.cas.client.adapter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;


/**
 * 类名称：CasConfigurerAdapter
 * 类描述：配置或自定义CAS过滤器接口适配器
 * 开发人：朱水平【Tank】
 * 创建时间：2018/6/20
 * 修改备注：
 *
 * @version 1.0.0
 */
public class CasConfigurerAdapter implements CasConfigurer {
    @Override
    public void configureAuthenticationFilter(FilterRegistrationBean authenticationFilter) {
        //默认不做处理
    }

    @Override
    public void configureValidationFilter(FilterRegistrationBean validationFilter) {
        //默认不做处理
    }

    @Override
    public void configureHttpServletRequestWrapperFilter(FilterRegistrationBean httpServletRequestWrapperFilter) {
        //默认不做处理
    }

    @Override
    public void configureAssertionThreadLocalFilter(FilterRegistrationBean assertionThreadLocalFilter) {
        //默认不做处理
    }

    @Override
    public void configureSingleSignOutFilter(FilterRegistrationBean singleSignOutFilter) {
        //默认不做处理
    }

    @Override
    public void configureSessionTimeOutFilter(FilterRegistrationBean sessionTimeOutFilter) {
        //默认不做处理
    }

    @Override
    public void configureAutoSetUserAdapterFilter(FilterRegistrationBean autoSetUserAdapterFilter) {
        //默认不做处理
    }
}
