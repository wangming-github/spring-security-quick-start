package com.maizi.common.security;

import com.alibaba.fastjson.JSON;
import com.maizi.common.utils.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义spring security认证异常处理类
 *
 * @author maizi
 */
@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        R result = R.error(HttpServletResponse.SC_FORBIDDEN, "您需要登录才能访问!");
        response.getWriter().append(JSON.toJSONString(result));
    }
}
