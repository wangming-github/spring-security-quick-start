package com.maizi.common.security;

import com.alibaba.fastjson.JSON;
import com.maizi.common.utils.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义的访问拒绝处理器
 * <p>
 * 该类实现了 Spring Security 的 AccessDeniedHandler 接口，用于处理用户访问被拒绝的情况。
 * 当用户没有权限访问某个资源时，Spring Security 会调用这个处理器来生成相应的错误响应。
 *
 * @author maizi
 */
@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    /**
     * 处理访问拒绝异常
     * <p>
     * 当用户尝试访问未授权的资源时，会触发此方法。它设置响应的内容类型为 JSON，
     * 并返回一个包含错误信息的 JSON 响应。
     *
     * @param request               请求对象
     * @param response              响应对象
     * @param accessDeniedException 访问拒绝异常
     * @throws IOException 如果写入响应时发生 I/O 错误
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // 设置响应的内容类型和字符编码为 JSON 格式
        response.setContentType("application/json;charset=UTF-8");
        // 设置响应状态码为 403 Forbidden，表示用户没有权限访问请求的资源
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // 创建包含错误信息的 R 对象
        R result = R.error(HttpServletResponse.SC_FORBIDDEN, "您没有权限访问!");
        // 将 R 对象转换为 JSON 字符串并写入响应
        response.getWriter().append(JSON.toJSONString(result));
    }
}
