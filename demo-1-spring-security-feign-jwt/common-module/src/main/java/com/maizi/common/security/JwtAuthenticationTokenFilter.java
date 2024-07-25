package com.maizi.common.security;

import com.maizi.common.exception.RRException;
import com.maizi.common.redis.JwtUtil;
import com.maizi.common.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * 自定义 JWT 过滤器，用于拦截并验证请求中的 JWT。
 * <p>
 * 此过滤器在每个请求之前执行：
 * 1. 从请求头中获取 JWT。
 * 2. 解析 JWT 获取用户名。
 * 3. 从 Redis 中获取用户详细信息。
 * 4. 将用户信息封装成 Authentication 对象并存入 SecurityContextHolder。
 * </p>
 * <p>
 * 这样可以确保用户的每次请求都经过身份验证，并且后续的请求处理可以基于已验证的用户上下文进行。
 * 自定义过滤器-用户在登录后的请求中只要在请求头中添加了 token 字段并且值是登录成功后的响应值
 * 此过滤器将会首先进行过滤，不用经过后续的验证，直接将用户信息存放在容器中。
 * </p>
 *
 * @author maizi
 */
@Slf4j
@Configuration
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;  // JWT 工具类，用于处理 JWT 的生成和解析

    @Autowired
    private RedisUtil redisUtil;  // Redis 工具类，用于与 Redis 进行交互

    /**
     * 依赖注入完成后，打印初始化信息。
     */
    @PostConstruct
    public void init() {
        log.info("【公共模块】- 自定义 JWT 过滤器，用于拦截并验证请求中的 JWT。");
    }

    /**
     * 过滤器的核心方法：
     * 1. 从请求头中获取 JWT。
     * 2. 解析 JWT 获取用户名。
     * 3. 从 Redis 中获取用户详细信息。
     * 4. 将用户信息封装成 Authentication 对象并存入 SecurityContextHolder。
     *
     * @param request     当前 HTTP 请求
     * @param response    当前 HTTP 响应
     * @param filterChain 过滤链
     * @throws ServletException 如果请求不能被处理
     * @throws IOException      如果在处理请求时发生 I/O 错误
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("【公共模块】==============>{}", request.getRequestURI());

        // 从请求头中获取 JWT
        String token = request.getHeader("Authorization");

        // 如果请求头中没有 JWT，直接放行
        if (!StringUtils.hasText(token)) {
            log.info("【公共模块】- 请求[没有]携带的 token, 需要经过后续过滤器校验...");
            filterChain.doFilter(request, response);
            return;
        }
        log.info("【公共模块】- 请求携带的 token:{}", token);

        // 解析 JWT 以获取用户名
        String userName;
        try {
            userName = jwtUtil.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RRException("token 解析失败");  // 如果解析 JWT 失败，抛出自定义异常
        }

        // 从 Redis 中获取用户详细信息
        SecurityUserUserDetails user = redisUtil.get(userName, SecurityUserUserDetails.class);

        // 如果 Redis 中没有找到用户信息，抛出自定义异常
        if (Objects.isNull(user)) {
            log.info("【公共模块】- [没有]在 Redis 中找到用户名" + userName + "的缓存信息");
            throw new RRException("登录信息过期!");
        }
        log.info("【公共模块】- 请求携带的 token, 解析出用户名【{}】，其缓存信息:{}", user.getUsername(), user.getAuthorities());

        // 将用户信息封装成 Authentication(UsernamePasswordAuthenticationToken) 对象，存入 SecurityContextHolder
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();  // 获取用户的权限信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);  // 创建 Authentication 对象
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);  // 将 Authentication 对象存入 SecurityContextHolder

        // 放行请求，继续执行后续过滤器
        filterChain.doFilter(request, response);
    }
}
