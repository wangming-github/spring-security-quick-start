package com.maizi.common.security;

import com.maizi.common.exception.RRException;
import com.maizi.common.o.constants.ModuleType;
import com.maizi.common.redis.JwtUtil;
import com.maizi.common.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;


/**
 * 自定义JWT过滤器，用于拦截并验证请求中的JWT。
 * <p>
 * 此过滤器在每个请求之前执行：
 * 1. 从请求头中获取JWT。
 * 2. 解析JWT获取用户名。
 * 3. 从Redis中获取用户详细信息。
 * 4. 将用户信息封装成Authentication对象并存入SecurityContextHolder。
 * </p>
 * 这样可以确保用户的每次请求都经过身份验证，并且后续的请求处理可以基于已验证的用户上下文进行。
 * </p>
 * 自定义过滤器-用户在登录后的请求中只要在请求头中添加了token字段并且值是登录成功后的响应值
 * 此过滤器将会首先进行过滤，不用经过后续的验证，直接将用户信息存放在容器中
 *
 * @author maizi
 */
@Slf4j
@Configuration
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    /**
     * 依赖注入完成后，打印初始化信息。
     */
    @PostConstruct
    public void init() {
        log.info("【公共模块】- 自定义JWT过滤器，用于拦截并验证请求中的JWT。");
    }

    @Autowired
    private JwtUtil jwtUtil;  // JWT工具类，用于处理JWT的生成和解析

    @Autowired
    private RedisUtil redisUtil;  // Redis工具类，用于与Redis进行交互


    /**
     * 过滤器的核心方法：
     * 1. 从请求头中获取JWT。
     * 2. 解析JWT获取用户名。
     * 3. 从Redis中获取用户详细信息。
     * 4. 将用户信息封装成Authentication对象并存入SecurityContextHolder。
     *
     * @param request     当前HTTP请求
     * @param response    当前HTTP响应
     * @param filterChain 过滤链
     * @throws ServletException 如果请求不能被处理
     * @throws IOException      如果在处理请求时发生I/O错误
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("【公共模块】==============>{}", request.getRequestURI());
        // 从请求头中获取JWT
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {  // 如果请求头中没有JWT，直接放行
            log.info("【公共模块】- 请求[没有]携带的token,需要经过后续过滤器校验...");
            filterChain.doFilter(request, response);
            return;
        }
        log.info("【公共模块】- 请求携带的token:{}", token);


        // 解析JWT以获取用户名
        String userName;
        try {
            userName = jwtUtil.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RRException("token解析失败");  // 如果解析JWT失败，抛出自定义异常
        }

        // 从Redis中获取用户详细信息
        SecurityUserUserDetails user = redisUtil.get(userName, SecurityUserUserDetails.class);
        if (Objects.isNull(user)) {  // 如果Redis中没有找到用户信息，抛出自定义异常
            log.info("【公共模块】- [没有]在redis中找到用户名" + userName + "的缓存信息");
            throw new RRException("登录信息过期!");
        }
        log.info("【公共模块】- 请求携带的token,解析出用户名【{}】，其缓存信息:{}", user.getUsername(), user.getAuthorities());

        // 将用户信息封装成Authentication(UsernamePasswordAuthenticationToken)对象，存入SecurityContextHolder
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();  // 获取用户的权限信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);  // 创建Authentication对象
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);  // 将Authentication对象存入SecurityContextHolder

        // 放行请求，继续执行后续过滤器
        filterChain.doFilter(request, response);
    }
}