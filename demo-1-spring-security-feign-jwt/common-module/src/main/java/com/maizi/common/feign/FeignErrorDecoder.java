package com.maizi.common.feign;

import com.maizi.common.exception.RRException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author maizi
 */
@Slf4j
@Configuration
public class FeignErrorDecoder implements ErrorDecoder {

    @PostConstruct
    public void init() {
        log.info("【公共模块】- 配置FeignErrorDecoder:实现 ErrorDecoder 来处理 Feign 请求中的错误，解析响应状态码和内容，生成具体的异常。");
    }

    @Override
    public Exception decode(String methodKey, Response response) {

        int status = response.status();

        // 从响应中提取错误信息
        String errorMessage;
        try {
            if (response.body() != null) {
                errorMessage = response.body().asReader().toString();
            } else {
                errorMessage = "响应体为空";
            }
        } catch (IOException e) {
            errorMessage = "读取响应体失败: " + e.getMessage();
        }

        // 根据响应状态码生成适当的异常
        switch (status) {
            case 400:
                log.error("【公共模块】- ====>服务调用失败，状态码 400：{}", errorMessage);
                throw new RRException("feign远程调用,请求错误!", 400);
            case 403:
                log.error("【公共模块】- ====>服务调用失败，状态码 403：{}", errorMessage);
                throw new RRException("feign远程调用,权限不足!", 403);
            case 404:
                log.error("【公共模块】- ====>服务调用失败，状态码 404：{}", errorMessage);
                throw new RRException("feign远程调用,资源未找到!");
            case 500:
                log.error("【公共模块】- ====>服务调用失败，状态码 500：{}", errorMessage);
                throw new RRException("feign远程调用,内部服务器错误!");
            default:
                log.error("【公共模块】- ====>feign远程调用失败，未知状态码：{}", errorMessage);
                return new Exception("通用错误: " + errorMessage);
        }
    }
}
