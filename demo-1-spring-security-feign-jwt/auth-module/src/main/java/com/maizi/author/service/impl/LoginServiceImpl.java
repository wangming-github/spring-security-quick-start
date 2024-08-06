package com.maizi.author.service.impl;

import com.maizi.author.service.LoginService;
import com.maizi.common.o.dto.UserDTO;
import com.maizi.common.redis.JwtUtil;
import com.maizi.common.redis.RedisUtil;
import com.maizi.common.security.SecurityUserUserDetails;
import com.maizi.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录服务实现类
 * <p>
 * 处理用户登录、登出操作，并提供获取用户角色和权限的方法。
 * <p>
 * 登录流程：
 * 1. 创建 UsernamePasswordAuthenticationToken 对象
 * 2. 使用 AuthenticationManager 进行用户认证
 * 3. 认证成功后生成 JWT Token，并将用户信息存入 Redis
 * 4. 返回 JWT Token 给前端
 * <p>
 * 登出流程：
 * 1. 从 SecurityContextHolder 获取当前认证信息
 * 2. 从 Redis 中删除用户信息
 * 3. 返回登出成功的响应
 * <p>
 * 获取用户角色和权限：
 * 1. 从 SecurityContextHolder 获取当前认证信息
 * 2. 提取用户角色和权限
 * 3. 返回角色和权限信息
 *
 * @author maizi
 */
@Slf4j
@Service
@Order(9)
public class LoginServiceImpl implements LoginService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public R login(UserDTO userDTO) {
        log.info("【鉴权模块】- 【1】根据登录的用户名密码信息创建 UsernamePasswordAuthenticationToken 对象");
        // 使用 authenticationManager 进行用户认证
        UsernamePasswordAuthenticationToken loginAuthenticate = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        log.info("【鉴权模块】- 【2】使用 AuthenticationManager 验证登录信息 UsernamePasswordAuthenticationToken 对象");

        // 进行用户认证
        Authentication authenticate = authenticationManager.authenticate(loginAuthenticate);
        // 认证结果成功
        if (authenticate.isAuthenticated()) {
            log.info("【鉴权模块】- 登录成功，获取登录的用户信息");
            SecurityUserUserDetails user = (SecurityUserUserDetails) authenticate.getPrincipal();
            redisUtil.set(user.getUsername(), user);
            log.info("【鉴权模块】- ↘将登录用户信息存放进 Redis, K:{} V:{}", user.getUsername(), user);
            String token = jwtUtil.generateToken(user.getUsername());
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("code", HttpServletResponse.SC_OK);
            tokenMap.put("token", token);
            tokenMap.put("msg", "Authentication success!");
            log.info("【鉴权模块】- ↘根据用户名生成 Token，返回前端。");
            return R.ok(tokenMap);
        } else {
            log.info("【鉴权模块】- 登录失败");
            return R.error(HttpServletResponse.SC_FORBIDDEN, "Authentication failed!");
        }
    }

    @Override
    public R logout() {
        // 从 SecurityContextHolder 中获取 Authorization 信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取容器中当前登录的用户信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 获取用户名
        String username = userDetails.getUsername();
        log.info("【鉴权模块】- 登出用户：" + username);
        redisUtil.delete(username);
        Map<String, Object> result = new HashMap<>();
        result.put("code", HttpServletResponse.SC_OK);
        result.put("msg", "logout success!");
        return R.ok(result);
    }

    /**
     * 获取当前 SecurityContextHolder 中认证身份信息
     *
     * @return 用户的角色和权限信息
     */
    @Override
    public Map<String, Object> getRolesAndAuthorities() {
        // 从 SecurityContextHolder 获取身份验证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 获取用户的角色和权限集合
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> roles = authorities.stream().filter(authority -> authority.getAuthority().startsWith("ROLE_")).map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        Set<String> permissions = authorities.stream().filter(authority -> !authority.getAuthority().startsWith("ROLE_")).map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        // 将角色和权限集合返回给前端
        Map<String, Object> response = new HashMap<>();
        response.put("roles", roles);
        response.put("permissions", permissions);
        return response;
    }
}
