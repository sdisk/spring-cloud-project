package com.hq.registry.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @program: spring-cloud-project
 * @author: Mr.Huang
 * @create: 2019-10-24 14:28
 **/
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     *  SpringBoot引入spring-boot-starter-security做安全校验会自动开启csrf安全认证，对请求都需要csrf的token
     *  但是eureka-client不会自动生成token，就会连接不到注册中心，
     *  这里对eureka开始的路径进行csrf忽略
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/eureka/**");
        super.configure(http);
    }
}
