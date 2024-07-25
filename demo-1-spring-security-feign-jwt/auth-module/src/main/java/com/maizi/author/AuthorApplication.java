package com.maizi.author;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类，Spring Security + JWT 实现项目级前端分离认证授权。
 * <p>
 * 该类配置了 Spring Boot 应用程序，并启用了 Feign 客户端、服务发现和组件扫描。
 * </p>
 * <p>
 * Spring Security 的认证过程如下：
 * 1. 用户登录请求：用户通过登录表单提交用户名和密码。
 * 2. 认证过滤器：Spring Security 的认证过滤器（如 UsernamePasswordAuthenticationFilter）捕获登录请求，并从请求中提取用户名和密码。
 * 3. 调用 UserDetailsService：认证过滤器调用 UserDetailsService 的 loadUserByUsername 方法，获取包含用户详细信息的 UserDetails 对象。
 * 4. 密码验证：Spring Security 将从登录表单中提取的密码与 UserDetails 对象中的密码进行比较。默认情况下，Spring Security 使用 BCryptPasswordEncoder 进行密码匹配。
 * 5. 认证成功或失败：如果密码匹配成功，认证通过；否则，认证失败。
 * </p>
 *
 * @author maizi
 */
@EnableFeignClients(basePackages = "com.maizi.author.feign") // 启用 Feign 客户端，指定 Feign 服务的包路径
@EnableDiscoveryClient // 启用服务发现客户端
@SpringBootApplication // 标记这是一个 Spring Boot 应用程序
@ComponentScan(basePackages = {//
        "com.maizi.common.*", // 扫描公共模块中的 Bean
        "com.maizi.author.*" // 扫描当前模块中的 Bean
})
public class AuthorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorApplication.class, args); // 启动 Spring Boot 应用程序
    }

}
