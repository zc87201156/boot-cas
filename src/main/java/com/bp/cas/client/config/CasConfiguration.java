package com.bp.cas.client.config;

import com.bp.cas.client.autoconfig.CasAutoConfigurationProperties;
import com.bp.cas.client.filter.SessionTimeOutFilter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.bp.cas.client.adapter.CasConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.Filter;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 类名称：CasConfiguration
 * 类描述：配置或自定义CAS过滤器接口适配器
 * 开发人：朱水平【Tank】
 * 创建时间：2018/6/8.17:08
 * 修改备注：
 *
 * @version 1.0.0
 */
@EnableConfigurationProperties(CasAutoConfigurationProperties.class)
@Component
public class CasConfiguration {

    @Autowired
    private CasAutoConfigurationProperties casAutoConfigurationProperties;

    private CasConfigurer casConfigurer;
    private static boolean casEnabled = true;

    public CasConfiguration() {
    }

    @Autowired(required = false)
    private void setConfigurers(Collection<CasConfigurer> configurers) {
        if (CollectionUtils.isEmpty(configurers)) {
            return;
        }
        if (configurers.size() > 1) {
            throw new IllegalStateException(configurers.size() + " [CasClientConfigurer] 实现类只能有一个");
        }
        this.casConfigurer = configurers.iterator().next();
    }

    @Bean
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>();
        listener.setEnabled(casAutoConfigurationProperties.isEnabled());
        listener.setListener(new SingleSignOutHttpSessionListener());
        listener.setOrder(5);
        return listener;
    }

    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean singleSignOutFilter = new FilterRegistrationBean();
        Map<String, String> map = Maps.newHashMap();
        map.put("casServerUrlPrefix", casAutoConfigurationProperties.getCasServerUrlPrefix());
        initFilter(singleSignOutFilter, new SingleSignOutFilter(), 10, map, casAutoConfigurationProperties.getSignOutFilters());
        if (casConfigurer != null) {
            casConfigurer.configureSingleSignOutFilter(singleSignOutFilter);
        }
        return singleSignOutFilter;
    }

    @Bean
    public FilterRegistrationBean sessionTimeOutFilter() {
        FilterRegistrationBean sessionTimeOutFilter = new FilterRegistrationBean();
        SessionTimeOutFilter filter = new SessionTimeOutFilter();
        filter.setCasAutoConfigurationProperties(casAutoConfigurationProperties);
        initFilter(sessionTimeOutFilter, filter, 15, Maps.newHashMap(), Lists.newArrayList());
        if (casConfigurer != null) {
            casConfigurer.configureSessionTimeOutFilter(sessionTimeOutFilter);
        }
        return sessionTimeOutFilter;
    }

    @Bean
    public FilterRegistrationBean authenticationFilter() {
        FilterRegistrationBean authenticationFilter = new FilterRegistrationBean();
        Map<String, String> map = Maps.newHashMap();
        map.put("casServerLoginUrl", casAutoConfigurationProperties.getCasServerLoginUrl());
        map.put("serverName", casAutoConfigurationProperties.getServerName());
        map.put("useSession", casAutoConfigurationProperties.isUseSession() + "");
        map.put("redirectAfterValidation", casAutoConfigurationProperties.isRedirectAfterValidation() + "");
        if (StringUtils.isNotEmpty(casAutoConfigurationProperties.getIgnorePattern())) {
            map.put("ignorePattern", casAutoConfigurationProperties.getIgnorePattern());
        }
        initFilter(authenticationFilter, new AuthenticationFilter(), 20, map, casAutoConfigurationProperties.getAuthFilters());
        if (casConfigurer != null) {
            casConfigurer.configureAuthenticationFilter(authenticationFilter);
        }
        return authenticationFilter;
    }

    @Bean
    public FilterRegistrationBean cas20ProxyReceivingTicketValidationFilter() {
        FilterRegistrationBean validationFilter = new FilterRegistrationBean();
        Map<String, String> map = Maps.newHashMap();
        map.put("casServerUrlPrefix", casAutoConfigurationProperties.getCasServerUrlPrefix());
        map.put("serverName", casAutoConfigurationProperties.getServerName());
        initFilter(validationFilter, new Cas20ProxyReceivingTicketValidationFilter(), 25, map, casAutoConfigurationProperties.getAuthFilters());
        if (casConfigurer != null) {
            casConfigurer.configureValidationFilter(validationFilter);
        }
        return validationFilter;
    }

    @Bean
    public FilterRegistrationBean httpServletRequestWrapperFilter() {
        FilterRegistrationBean httpServletRequestWrapperFilter = new FilterRegistrationBean();
        initFilter(httpServletRequestWrapperFilter, new HttpServletRequestWrapperFilter(), 30, Maps.newHashMap(), Lists.newArrayList());
        if (casConfigurer != null) {
            casConfigurer.configureHttpServletRequestWrapperFilter(httpServletRequestWrapperFilter);
        }
        return httpServletRequestWrapperFilter;
    }

    @Bean
    public FilterRegistrationBean assertionThreadLocalFilter() {
        FilterRegistrationBean assertionThreadLocalFilter = new FilterRegistrationBean();
        initFilter(assertionThreadLocalFilter, new AssertionThreadLocalFilter(), 35, Maps.newHashMap(), casAutoConfigurationProperties.getAssertionFilters());
        if (casConfigurer != null) {
            casConfigurer.configureAssertionThreadLocalFilter(assertionThreadLocalFilter);
        }
        return assertionThreadLocalFilter;
    }

    @Bean
    public CasAutoConfigurationProperties getSpringCasAutoconfig() {
        return new CasAutoConfigurationProperties();
    }

    private void initFilter(final FilterRegistrationBean filterRegistrationBean,
                            final Filter filter,
                            int filterOrder,
                            final Map<String, String> initParams,
                            List<String> urlPatterns) {

        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setEnabled(casEnabled);
        filterRegistrationBean.setOrder(filterOrder);
        if (initParams.size() > 0) {
            filterRegistrationBean.setInitParameters(initParams);
        }
        if (urlPatterns.size() > 0) {
            filterRegistrationBean.setUrlPatterns(urlPatterns);
        } else {
            filterRegistrationBean.addUrlPatterns("/*");
        }
    }

}
