
## 项目使用
1. pom.xml 配置 

    ```bash
     <dependency>
          <groupId>com.wf.cas</groupId>
          <artifactId>wf-boot-cas</artifactId>
          <version>1.0.0.RELEASE</version>
     </dependency>
    ```
1. application.properties or application.yml中配置

## 主要配置项

- 必须项
    - properties
     
     ```properties
     cas.server-name=http://dev.riskmonitor-admin.beeplay123.com 你的项目地址
     ```
     -  yaml
     
     ```yaml
      cas:
        server-name: http://dev.riskmonitor-admin.beeplay123.com 你的项目地址
    ```
- 可选项

    ```bash
    cas.sign-out-filters (默认值:/*)
    cas.auth-filters (默认值:/*)
    cas.validate-filters (默认值:/*)
    cas.request-wrapper-filters (默认值:/*)
    cas.assertion-filters (默认值:/*)
    cas.ignore-pattern 要忽略的资源,使用正则匹配
    cas.use-session (默认值:true)
    cas.redirect-after-validation (默认值:true)
    ```
    


## 如有疑问请联系  Author 朱水平【Tank】
 