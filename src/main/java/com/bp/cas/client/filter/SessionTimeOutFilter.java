package com.bp.cas.client.filter;

import com.alibaba.fastjson.JSONObject;
import com.bp.cas.client.autoconfig.CasAutoConfigurationProperties;
import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.util.AbstractCasFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * 类名称：SessionTimeOutFilter
 * 类描述：配置sessionTimeOut拦截
 * 开发人：朱水平【Tank】
 * 创建时间：2018/6/20.17:08
 * 修改备注：
 *
 * @version 1.0.0
 */
public class SessionTimeOutFilter implements Filter {

    private CasAutoConfigurationProperties casAutoConfigurationProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String urls = casAutoConfigurationProperties.getSessionTimeOutIgnoreUrls();
        if (StringUtils.isNoneBlank(urls) && !Arrays.asList(urls.split(",")).contains(request.getRequestURI())) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            if (request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION) == null) {
                ajaxRequest(response);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private void ajaxRequest(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setHeader("Session-Status", "Session-Out");   //在响应头设置session状态
        servletResponse.setHeader("Redirect-Url", casAutoConfigurationProperties.getServerName());//在响应头设置跳转URL
        servletResponse.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = servletResponse.getWriter()) {
            JSONObject jo = new JSONObject();//TODO 可修改这里
            jo.put("code", 301);
            jo.put("msg", "登录超时,请重新登录");
            jo.put("data", casAutoConfigurationProperties.getServerName());
            out.println(jo);
        }
    }

    public void setCasAutoConfigurationProperties(CasAutoConfigurationProperties casAutoConfigurationProperties) {
        this.casAutoConfigurationProperties = casAutoConfigurationProperties;
    }
}
