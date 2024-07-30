package com.maizi.demo.test;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFilterConfig {

    /**
     * 以上示例中，过滤器将拦截所有以 /api/ 开头的请求，并在控制台中打印请求的 URL。
     * <p>
     * 运行应用程序
     * 完成以上步骤后，启动 Spring Boot 应用程序。访问 /api/ 开头的 URL 时，控制台会输出相应的请求 URL，表示过滤器已生效。
     * <p>
     * 这样就完成了在 Spring Boot 中创建和注册一个自定义过滤器的基本步骤。
     */
    @Bean
    public FilterRegistrationBean<MyFilter> loggingFilter() {
        FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new MyFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1); // 设置执行顺序，值越小优先级越高
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<MyFilter2> loggingFilter2() {
        FilterRegistrationBean<MyFilter2> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new MyFilter2());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(2); // 设置执行顺序，值越小优先级越高
        return registrationBean;
    }
}
