package com.maizi.common.security;

import com.alibaba.fastjson.JSON;
import com.maizi.common.utils.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 自定义spring security授权异常处理类
 *
 * @author maizi
 */
@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // 设置响应的内容类型和字符编码
        response.setContentType("text/plain;charset=UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 设置状态码为403 Forbidden
        R result = R.error(HttpServletResponse.SC_FORBIDDEN, "您没有权限访问!");
        response.getWriter().append(JSON.toJSONString(result));
    }
}