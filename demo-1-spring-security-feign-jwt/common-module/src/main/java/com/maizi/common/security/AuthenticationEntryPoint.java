package com.maizi.common.security;

import com.alibaba.fastjson.JSON;
import com.maizi.common.utils.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义 Spring Security 认证异常处理类
 * <p>
 * 该类实现了 Spring Security 的 AuthenticationEntryPoint 接口，用于处理认证异常。
 * 当用户尝试访问需要认证的资源，但未提供有效的认证信息时，Spring Security 会调用这个处理器，
 * 生成相应的错误响应。
 *
 * @author maizi
 */
@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    /**
     * 处理认证异常
     * <p>
     * 当用户尝试访问需要认证的资源但未认证时，会触发此方法。
     * 该方法设置响应的头部和内容类型，并返回一个包含错误信息的 JSON 响应。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param e        认证异常
     * @throws IOException 如果写入响应时发生 I/O 错误
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        // 设置允许跨域的请求来源
        response.setHeader("Access-Control-Allow-Origin", "*");

        // 设置响应的字符编码为 UTF-8
        response.setCharacterEncoding("UTF-8");

        // 设置响应的内容类型为 JSON 格式
        response.setContentType("application/json;charset=UTF-8");

        // 创建包含错误信息的 R 对象
        R result = R.error(HttpServletResponse.SC_FORBIDDEN, "您需要登录才能访问!");

        // 将 R 对象转换为 JSON 字符串并写入响应
        response.getWriter().append(JSON.toJSONString(result));
    }
}
