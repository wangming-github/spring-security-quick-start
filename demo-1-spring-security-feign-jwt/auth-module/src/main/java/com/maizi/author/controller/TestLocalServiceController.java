package com.maizi.author.controller;

import com.maizi.author.service.LoginService;
import com.maizi.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestLocalServiceController {

    @Autowired
    LoginService authorService;

    /**
     * 处理 GET 请求，返回数据示例
     *
     * @return 包含数据的响应对象
     */

    @GetMapping("/get")
    public R getExample() {
        R response = R.ok().put("data", "这是一个 GET 请求示例！");
        log.info("【鉴权模块】- GET 请求响应: {}", response);
        response.put("role", authorService.getRolesAndAuthorities());
        return response;
    }

    /**
     * 处理 POST 请求，接收数据示例
     *
     * @param data 请求体中的数据
     * @return 包含接收到数据的响应对象
     */
    @PostMapping("/post")
    public R postExample(@RequestBody String data) {
        R response = R.ok().put("data", "已接收到 POST 数据: " + data);
        log.info("【鉴权模块】- POST 请求响应: {}", response);
        response.put("role", authorService.getRolesAndAuthorities());
        return response;
    }

    /**
     * 处理 POST 请求，用户数据示例
     *
     * @param data 请求体中的数据
     * @return 包含接收到用户数据的响应对象
     */
    @PostMapping("/user")
    public R userExample(@RequestBody String data) {
        R response = R.ok().put("data", "已接收到用户数据: " + data);
        log.info("【鉴权模块】- 用户数据 POST 请求响应: {}", response);
        response.put("role", authorService.getRolesAndAuthorities());
        return response;
    }

    /**
     * 处理 POST 请求，管理员数据示例
     *
     * @param data 请求体中的数据
     * @return 包含接收到管理员数据的响应对象
     */
    @PostMapping("/admin")
    public R adminExample(@RequestBody String data) {
        R response = R.ok().put("data", "已接收到管理员数据: " + data);
        log.info("【鉴权模块】- 管理员数据 POST 请求响应: {}", response);
        response.put("role", authorService.getRolesAndAuthorities());
        return response;
    }
}
