package com.maizi.author.controller;

import com.maizi.author.service.LoginService;
import com.maizi.common.o.dto.UserDTO;
import com.maizi.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 * <p>
 * 该控制器处理用户的登录和登出请求。
 *
 * @author maizi
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * 处理用户登录请求，并返回 JWT Token。
     * <p>
     * 该方法从请求体中获取用户名和密码，然后调用 LoginService 进行认证，
     * 成功后返回 JWT Token，供后续请求进行身份验证。
     *
     * @param userDTO 包含用户名和密码的请求体
     * @return 返回一个包含 JWT Token 的响应，用于后续身份验证
     */
    @PostMapping("/login")
    public R login(@RequestBody UserDTO userDTO) {
        return loginService.login(userDTO);
    }

    /**
     * 处理用户登出请求。
     * <p>
     * 该方法调用 LoginService 的 logout 方法，处理用户登出操作。
     *
     * @return 返回登出成功的响应
     */
    @PostMapping("/logout")
    public R logoutPost() {
        return loginService.logout();
    }
}
