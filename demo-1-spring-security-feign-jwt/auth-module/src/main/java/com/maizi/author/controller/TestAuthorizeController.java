package com.maizi.author.controller;

import com.maizi.author.service.LoginService;
import com.maizi.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class TestAuthorizeController {

    @Autowired
    LoginService authorService;

    /**
     * 管理员端点，仅允许具有 ROLE_ADMIN 权限的用户访问
     *
     * @return 管理员端点的响应
     */
    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public R adminEndpoint() {
        log.info("【鉴权模块】- 管理员端点被访问");
        return R.ok("管理员端点访问成功").put("role", authorService.getRolesAndAuthorities());
    }

    /**
     * 用户端点，仅允许具有 ROLE_USER 权限的用户访问
     *
     * @return 用户端点的响应
     */
    @GetMapping("/user")
    @Secured("ROLE_USER")
    public R userEndpoint() {
        log.info("【鉴权模块】- 用户端点被访问");
        return R.ok("用户端点访问成功").put("role", authorService.getRolesAndAuthorities());
    }

    /**
     * 模拟管理员权限的端点，仅允许具有 ROLE_MODERATOR 权限的用户访问
     *
     * @return 管理员权限端点的响应
     */
    @GetMapping("/moderator")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public R moderatorEndpoint() {
        log.info("【鉴权模块】- 管理员权限端点被访问");
        return R.ok("管理员权限端点访问成功").put("role", authorService.getRolesAndAuthorities());
    }

    /**
     * 来宾端点，允许所有已认证和匿名用户访问
     *
     * @return 来宾端点的响应
     */
    @GetMapping("/guest")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public R guestEndpoint() {
        log.info("【鉴权模块】- 来宾端点被访问");
        return R.ok("来宾端点访问成功").put("role", authorService.getRolesAndAuthorities());
    }

}