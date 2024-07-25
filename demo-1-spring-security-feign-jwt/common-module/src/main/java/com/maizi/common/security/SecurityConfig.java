package com.maizi.common.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 配置 Spring Security 的安全设置。
 * <p>
 * 该配置类继承自 WebSecurityConfigurerAdapter，并提供了一些自定义配置来增强应用程序的安全性：
 * 1. 配置自定义的 JWT 认证过滤器。
 * 2. 配置自定义的异常处理器。
 * 3. 配置密码编码器，使用 BCrypt 加密。
 * 4. 配置 HTTP 安全策略，禁止 CSRF，设置无状态会话，指定访问权限。
 * </p>
 *
 * @author maizi
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(//
        prePostEnabled = true, // 启用 @PreAuthorize 和 @PostAuthorize 注解，用于方法级别的权限控制。
        securedEnabled = true)// 启用 @Secured 注解，用于方法级别的角色权限控制。
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter; // 注入自定义 JWT 认证过滤器

    @Autowired
    private AccessDeniedHandler accessDeniedHandler; // 注入自定义的访问拒绝处理器

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint; // 注入自定义的认证入口点

    /**
     * 配置密码编码器，使用 BCrypt 加密算法替代已过时的 withDefaultPasswordEncoder() 方法。
     *
     * @return BCryptPasswordEncoder 实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("【公共模块】- Bean:passwordEncoder");
        return new BCryptPasswordEncoder(); // 使用 BCrypt 加密密码
    }

    /**
     * 公开 AuthenticationManager Bean，用于支持认证管理功能。
     *
     * @return AuthenticationManager 实例
     * @throws Exception 如果创建 AuthenticationManager 失败
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        log.info("【公共模块】- Bean:authenticationManagerBean");
        return super.authenticationManagerBean(); // 暴露 AuthenticationManager Bean
    }


    /**
     * 配置 HTTP 安全策略。
     *
     * @param http HttpSecurity 对象，用于配置安全策略
     * @throws Exception 如果配置失败
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("【公共模块】- 配置 HTTP 安全策略。");

        // 禁用 CSRF 保护，适用于前后端分离的场景
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 设置无状态会话管理策略，不使用 Session 存储安全上下文
        http.authorizeRequests() // 配置请求的访问权限
                // authorization-module
                .antMatchers("/login").anonymous() // 允许匿名访问 /login 接口
                .antMatchers("/api/admin").hasRole("ADMIN") // 只有具有 ADMIN 角色的用户可以访问 /api/admin
                .antMatchers("/api/user").hasRole("USER") // 只有具有 USER 角色的用户可以访问 /api/user
                .antMatchers("/api/moderator").hasRole("MODERATOR") // 只有具有 MODERATOR 角色的用户可以访问 /api/moderator
                // service-module ADMIN
                .antMatchers("/admin/**").hasRole("ADMIN") // 只有 ADMIN 角色可以访问 /admin/** 路径
                .antMatchers("/admin/greet/**").hasAuthority("READ_PRIVILEGES") // 对应的权限配置：只有拥有 READ_PRIVILEGES 权限的用户才能访问 /admin/greet
                .antMatchers("/admin/echo").hasAuthority("WRITE_PRIVILEGES") // 对应的权限配置：只有拥有 WRITE_PRIVILEGES 权限的用户才能访问 /admin/echo
                .antMatchers("/admin/sum/**").hasAuthority("DELETE_PRIVILEGES") // 对应的权限配置：只有拥有 DELETE_PRIVILEGES 权限的用户才能访问 /admin/sum
                .antMatchers("/admin/multiply/**").hasAuthority("UPDATE_PRIVILEGES") // 对应的权限配置：只有拥有 UPDATE_PRIVILEGES 权限的用户才能访问 /admin/multiply
                // service-module USER
                .antMatchers("/user/**").hasRole("USER")//
                .antMatchers("/user/greet/**").hasAuthority("READ_PRIVILEGES")//
                .antMatchers("/user/echo").hasAuthority("WRITE_PRIVILEGES")//
                .antMatchers("/user/sum/**").hasAnyAuthority("DELETE_PRIVILEGES")//
                .antMatchers("/user/multiply/**").hasAnyAuthority("UPDATE_PRIVILEGES") // 对应的权限配置：只有拥有 UPDATE_PRIVILEGES 权限的用户才能访问 /admin/multiply
                .anyRequest().authenticated(); // 其他所有请求必须经过认证

        http.logout().disable(); // 禁用默认注销接口，避免 Spring Security 修改 /logout 行为
        http.formLogin().disable();// 禁用 Spring Security 默认的表单登录功能。
        // 将自定义 JWT 认证过滤器添加到 UsernamePasswordAuthenticationFilter 之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 配置自定义异常处理器
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler) // 设置自定义访问拒绝处理器
                .authenticationEntryPoint(authenticationEntryPoint); // 设置自定义认证入口点

    }
}
