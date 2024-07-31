package com.maizi.github;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration // 标记此类为 Spring 配置类
@EnableWebSecurity // 启用 Spring Security 的 Web 安全性支持
public class SecurityConfig {

    /**
     * 配置 Spring Security 的过滤器链。
     *
     * @param http HttpSecurity 对象，用于配置 Spring Security 的 HTTP 请求处理
     * @return 配置好的 SecurityFilterChain 实例
     * @throws Exception 可能抛出的异常
     */
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated() // 所有请求都需要认证
    //     ).oauth2Login(); // 启用 OAuth2 登录
    //
    //     // 构建并返回配置好的 SecurityFilterChain 实例
    //     return http.build();
    // }
}
