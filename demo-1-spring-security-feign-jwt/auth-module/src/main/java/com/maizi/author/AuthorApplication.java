package com.maizi.author;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * Spring Security+JWT实现项目级前端分离认证授权
 * <p>
 * Spring Security 在用户登录时验证密码。具体过程如下：
 * 1.用户登录请求：用户通过登录表单提交用户名和密码。
 * 2.认证过滤器：Spring Security 的认证过滤器（如 UsernamePasswordAuthenticationFilter）捕获登录请求，并从请求中提取用户名和密码。
 * 3.调用 UserDetailsService：认证过滤器调用 UserDetailsService 的 loadUserByUsername 方法，获取包含用户详细信息的 UserDetails 对象。
 * 4.密码验证：Spring Security 将从登录表单中提取的密码与 UserDetails 对象中的密码进行比较。默认情况下，Spring Security 使用 BCryptPasswordEncoder 进行密码匹配。
 * 5.认证成功或失败：如果密码匹配成功，认证通过；否则，认证失败。
 * <p>
 *
 * @author maizi
 */
@EnableFeignClients(basePackages = "com.maizi.author.feign")// FeignService所在包的路径
@EnableDiscoveryClient// 当前版本已无需在启动类增加注解
@SpringBootApplication
@ComponentScan(basePackages = {//
        "com.maizi.common.*",// 扫描公共模块的Bean
        "com.maizi.author.*"// 扫描当前模块的Bean
})
public class AuthorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorApplication.class, args);
    }

}
