### Spring Boot 过滤器执行顺序

#### 单个过滤器的执行顺序

当只有一个过滤器时，执行顺序非常简单。以下是过滤器 `CustomFilter` 的执行流程：

1. **初始化**：
    - `init(FilterConfig filterConfig)` 方法在过滤器被创建时调用，一般用于初始化过滤器资源。

2. **请求处理**：
    - `doFilter(ServletRequest request, ServletResponse response, FilterChain chain)` 方法在每次请求到达时调用。
    - 在 `chain.doFilter(request, response)` 之前的代码将在请求处理前执行。
    - `chain.doFilter(request, response)` 调用之后的代码将在请求处理后执行。

3. **销毁**：
    - `destroy()` 方法在过滤器被销毁时调用，一般用于清理过滤器资源。

```java
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化代码
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        System.out.println("请求前执行: URL: " + httpRequest.getRequestURL());

        // 继续调用下一个过滤器或目标资源
        chain.doFilter(request, response);

        System.out.println("请求后执行");
    }

    @Override
    public void destroy() {
        // 清理代码
    }
}
```

#### 两个过滤器的执行顺序

当有两个过滤器时，执行顺序将根据它们的注册顺序或设置的顺序参数来确定。假设我们有两个过滤器 `CustomFilter` 和 `MyFilter2`，并且它们的配置如下：

```java
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CustomFilter> loggingFilter() {
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1); // 设置执行顺序，值越小优先级越高
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<MyFilter2> secondFilter() {
        FilterRegistrationBean<MyFilter2> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MyFilter2());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(2); // 设置执行顺序，值越小优先级越高
        return registrationBean;
    }
}
```

执行流程如下：

1. **`CustomFilter` 过滤器**：
    - `init(FilterConfig filterConfig)` 方法首先被调用。
    - 在 `doFilter` 方法中，`chain.doFilter(request, response)` 之前的代码在请求处理前执行。
    - `chain.doFilter(request, response)` 调用会将请求传递给下一个过滤器 `MyFilter2`。
    - `chain.doFilter(request, response)` 调用之后的代码在请求处理后执行。

2. **`MyFilter2` 过滤器**：
    - `init(MyFilterConfig filterConfig)` 方法被调用。
    - 在 `doFilter` 方法中，`chain.doFilter(request, response)` 之前的代码在请求处理前执行。
    - `chain.doFilter(request, response)` 调用会将请求传递给目标资源或下一个过滤器（如果有）。
    - `chain.doFilter(request, response)` 调用之后的代码在请求处理后执行。

```java
public class MyFilter2 implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化代码
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        System.out.println("请求前执行2: URL: " + httpRequest.getRequestURL());

        // 继续调用下一个过滤器或目标资源
        chain.doFilter(request, response);

        System.out.println("请求后执行2");
    }

    @Override
    public void destroy() {
        // 清理代码
    }
}
```

#### 执行顺序总结

- **请求前执行**：根据 `FilterRegistrationBean` 的 `setOrder` 值，顺序较小的过滤器先执行其 `doFilter` 方法的前半部分。
- **请求后执行**：`chain.doFilter(request, response)` 调用完成后，按相反顺序执行每个过滤器的 `doFilter` 方法的后半部分。

例如：
- 请求到达时，`CustomFilter` 的前半部分先执行，然后是 `MyFilter2` 的前半部分。
- 目标资源处理请求后，`MyFilter2` 的后半部分先执行，然后是 `CustomFilter` 的后半部分。

通过设置 `FilterRegistrationBean` 的 `setOrder` 值，可以精确控制多个过滤器的执行顺序，确保符合应用的逻辑需求。