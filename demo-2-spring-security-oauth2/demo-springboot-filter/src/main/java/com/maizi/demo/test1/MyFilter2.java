package com.maizi.demo.test1;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 1.创建自定义过滤器类
 * 首先，创建一个自定义过滤器类，实现 javax.servlet.Filter 接口：
 * 2.注册过滤器
 * 接下来，需要将这个过滤器注册到 Spring Boot 应用程序中。
 * 可以通过两种方式来注册过滤器：使用 @Component 注解或者通过 FilterRegistrationBean 在配置类中注册。
 */
public class MyFilter2 implements Filter {

    public void init(MyFilterConfig myFilterConfig) throws ServletException {
        // 初始化代码，可以留空
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        System.out.println("请求前执行2:URL: " + httpRequest.getRequestURL());

        // 继续调用下一个过滤器或目标资源
        chain.doFilter(request, response);
        System.out.println("请求后执行2");
    }

    @Override
    public void destroy() {
        // 清理代码，可以留空
    }

}
