package com.bp.cas.client.autoconfig;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * 类名称：CasAutoConfigurationProperties
 * 类描述：配置项
 * 开发人：朱水平【Tank】
 * 创建时间：2018/6/20.17:08
 * 修改备注：
 *
 * @version 1.0.0
 */
@RefreshScope
@ConfigurationProperties(prefix = "cas")
@Data
public class CasAutoConfigurationProperties {
    /**
     * 默认启用
     */
    private boolean enabled = true;
    private String serverName;
    private String ignorePattern;
    private List<String> validateFilters = Lists.newArrayList();
    private List<String> signOutFilters = Lists.newArrayList();
    private List<String> authFilters = Lists.newArrayList();
    private List<String> assertionFilters = Lists.newArrayList();
    private List<String> requestWrapperFilters = Lists.newArrayList();
    private boolean useSession = true;
    private boolean redirectAfterValidation = true;

    private String sessionTimeOutIgnoreUrls;
    /**
     * cas server 前缀
     * 例如:http://www.exmple.com:8443/
     */
    private String casServerUrlPrefix;
    /**
     * cas server 登录地址
     * http://www.exmple.com:8443/cas/login
     */
    private String casServerLoginUrl;

}
