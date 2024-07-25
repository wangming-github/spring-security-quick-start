package com.maizi.common.feign;

import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign 客户端配置类
 * <p>
 * 该类用于配置 Feign 客户端的各种参数，包括错误解码器、请求选项、日志级别和请求拦截器。
 * 通过 @Configuration 注解，Spring 将其作为配置类进行管理。
 *
 * @author maizi
 */
@Slf4j
@Configuration
public class FeignConfig {

    /**
     * 提供自定义的 Feign 错误解码器 Bean
     *
     * @return ErrorDecoder 实现类 FeignErrorDecoder 的实例
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        // 返回自定义的 Feign 错误解码器，用于处理 Feign 请求中的错误
        return new FeignErrorDecoder();
    }

    /**
     * 定义 Request.Options Bean，用于配置连接和读取超时时间
     *
     * @return Request.Options 配置对象
     */
    @Bean
    public Request.Options options() {
        // 连接超时时间，单位为毫秒。在此示例中，设置为 5000 毫秒（即 5 秒）。
        // 连接超时指的是客户端尝试与服务器建立连接的最大等待时间。
        // 如果超过这个时间仍未建立连接，连接将会被中断并抛出异常。
        int connectTimeoutMillis = 5000;

        // 读取超时时间，单位为毫秒。在此示例中，设置为 10000 毫秒（即 10 秒）。
        // 读取超时指的是客户端在等待服务器响应数据的最大等待时间。
        // 如果超过这个时间仍未收到响应，读取操作将会被中断并抛出异常。
        int readTimeoutMillis = 10000;

        // 创建并返回一个新的 Request.Options 对象，将连接和读取超时时间作为参数传递。
        // Request.Options 是 Feign 客户端用来配置 HTTP 请求的选项类。
        return new Request.Options(connectTimeoutMillis, readTimeoutMillis);
    }

    /**
     * 配置 Feign 的日志级别
     *
     * @return Logger.Level 配置对象
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        /*
         * NONE: 不记录任何日志（默认）。
         * BASIC: 仅记录请求方法和 URL，响应状态码及执行时间。
         * HEADERS: 记录 BASIC 级别的基础信息，并且记录请求和响应的头信息。
         * FULL: 记录 HEADERS 级别的信息，并且记录请求和响应的正文（body）以及元数据。
         */
        // 设置 Feign 的日志级别为 BASIC，仅记录基本的请求和响应信息
        return Logger.Level.BASIC;
    }

    /**
     * 提供自定义的请求拦截器 Bean
     * <p>
     * 用于在 Feign 请求发送之前修改请求模板（例如添加 HTTP 头部）。
     * 这个拦截器将一个固定的头部（test-Key）和固定的值（test-Value）添加到每个 Feign 请求中。
     *
     * @return RequestInterceptor 实现类的实例
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        // 返回一个新的请求拦截器，添加固定的 HTTP 头部到每个请求中
        return template -> template.header("test-Key", "test-Value");
    }
}
