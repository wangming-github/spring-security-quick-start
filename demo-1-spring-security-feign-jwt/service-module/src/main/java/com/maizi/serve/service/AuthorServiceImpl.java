package com.maizi.serve.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

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
