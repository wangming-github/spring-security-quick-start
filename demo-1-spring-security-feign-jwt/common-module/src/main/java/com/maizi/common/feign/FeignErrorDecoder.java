package com.maizi.common.feign;

import com.maizi.common.exception.RRException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * 自定义 Feign 错误解码器
 * <p>
 * 这个类实现了 Feign 的 ErrorDecoder 接口，用于处理 Feign 请求中的错误。
 * 它根据响应的状态码和内容生成适当的异常，并记录错误信息。
 * 通过 @Configuration 注解，该类被 Spring 管理并作为 Bean 注入。
 *
 * @author maizi
 */
@Slf4j
@Configuration
public class FeignErrorDecoder implements ErrorDecoder {

    /**
     * 初始化方法
     * <p>
     * 使用 @PostConstruct 注解标记的 init() 方法将在 Spring 容器初始化后调用。
     * 该方法用于记录 FeignErrorDecoder 的配置状态，帮助调试和跟踪。
     */
    @PostConstruct
    public void init() {
        log.info("【公共模块】- 配置FeignErrorDecoder: 实现 ErrorDecoder 来处理 Feign 请求中的错误，解析响应状态码和内容，生成具体的异常。");
    }

    /**
     * 解码 Feign 错误
     *
     * @param methodKey 请求方法的标识符
     * @param response  Feign 请求的响应对象
     * @return 适当的异常对象，根据响应状态码生成不同的异常
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        // 获取响应状态码
        int status = response.status();

        // 从响应中提取错误信息
        String errorMessage;
        try {
            if (response.body() != null) {
                // 使用响应体的 Reader 读取错误信息
                errorMessage = response.body().asReader().toString();
            } else {
                // 响应体为空的情况下的默认错误信息
                errorMessage = "响应体为空";
            }
        } catch (IOException e) {
            // 读取响应体失败时的错误信息
            errorMessage = "读取响应体失败: " + e.getMessage();
        }

        // 根据响应状态码生成适当的异常
        switch (status) {
            case 400:
                // 处理状态码为 400 的错误（错误请求）
                log.error("【公共模块】- ====>服务调用失败，状态码 400：{}", errorMessage);
                throw new RRException("feign远程调用,请求错误!", 400);
            case 403:
                // 处理状态码为 403 的错误（权限不足）
                log.error("【公共模块】- ====>服务调用失败，状态码 403：{}", errorMessage);
                throw new RRException("feign远程调用,权限不足!", 403);
            case 404:
                // 处理状态码为 404 的错误（资源未找到）
                log.error("【公共模块】- ====>服务调用失败，状态码 404：{}", errorMessage);
                throw new RRException("feign远程调用,资源未找到!");
            case 500:
                // 处理状态码为 500 的错误（服务器内部错误）
                log.error("【公共模块】- ====>服务调用失败，状态码 500：{}", errorMessage);
                throw new RRException("feign远程调用,内部服务器错误!");
            default:
                // 处理其他状态码的错误
                log.error("【公共模块】- ====>feign远程调用失败，未知状态码：{}", errorMessage);
                return new Exception("通用错误: " + errorMessage);
        }
    }
}
