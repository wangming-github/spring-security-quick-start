package com.maizi.author.controller;

import com.maizi.author.service.LoginService;
import com.maizi.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 本地服务测试控制器
 * <p>
 * 该控制器用于处理各种测试请求，包括 GET 和 POST 请求。
 * 主要功能包括示例数据的返回和接收用户或管理员数据。
 * 还展示了如何通过 `LoginService` 获取当前用户的角色和权限信息。
 * </p>
 */
@Slf4j
@RestController
public class TestLocalServiceController {

    @Autowired
    private LoginService authorService; // 注入 LoginService 服务

    /**
     * 处理 GET 请求，返回数据示例
     * <p>
     * 该方法响应 GET 请求，并返回一个包含示例数据的响应对象。
     * 还包括当前用户的角色和权限信息。
     * </p>
     *
     * @return 包含数据的响应对象
     */
    @GetMapping("/get")
    public R getExample() {
        // 创建包含示例数据的响应对象
        R response = R.ok().put("data", "这是一个 GET 请求示例！");

        // 打印日志，记录 GET 请求的响应
        log.info("【鉴权模块】- GET 请求响应: {}", response);

        // 添加当前用户的角色和权限信息到响应对象中
        response.put("role", authorService.getRolesAndAuthorities());

        return response;
    }

    /**
     * 处理 POST 请求，接收数据示例
     * <p>
     * 该方法响应 POST 请求，并返回一个包含接收到数据的响应对象。
     * 还包括当前用户的角色和权限信息。
     * </p>
     *
     * @param data 请求体中的数据
     * @return 包含接收到数据的响应对象
     */
    @PostMapping("/post")
    public R postExample(@RequestBody String data) {
        // 创建包含接收到数据的响应对象
        R response = R.ok().put("data", "已接收到 POST 数据: " + data);

        // 打印日志，记录 POST 请求的响应
        log.info("【鉴权模块】- POST 请求响应: {}", response);

        // 添加当前用户的角色和权限信息到响应对象中
        response.put("role", authorService.getRolesAndAuthorities());

        return response;
    }

    /**
     * 处理 POST 请求，接收用户数据示例
     * <p>
     * 该方法响应 POST 请求，并返回一个包含接收到的用户数据的响应对象。
     * 还包括当前用户的角色和权限信息。
     * </p>
     *
     * @param data 请求体中的数据
     * @return 包含接收到用户数据的响应对象
     */
    @PostMapping("/user")
    public R userExample(@RequestBody String data) {
        // 创建包含接收到的用户数据的响应对象
        R response = R.ok().put("data", "已接收到用户数据: " + data);

        // 打印日志，记录 POST 请求的响应
        log.info("【鉴权模块】- 用户数据 POST 请求响应: {}", response);

        // 添加当前用户的角色和权限信息到响应对象中
        response.put("role", authorService.getRolesAndAuthorities());

        return response;
    }

    /**
     * 处理 POST 请求，接收管理员数据示例
     * <p>
     * 该方法响应 POST 请求，并返回一个包含接收到的管理员数据的响应对象。
     * 还包括当前用户的角色和权限信息。
     * </p>
     *
     * @param data 请求体中的数据
     * @return 包含接收到管理员数据的响应对象
     */
    @PostMapping("/admin")
    public R adminExample(@RequestBody String data) {
        // 创建包含接收到的管理员数据的响应对象
        R response = R.ok().put("data", "已接收到管理员数据: " + data);

        // 打印日志，记录 POST 请求的响应
        log.info("【鉴权模块】- 管理员数据 POST 请求响应: {}", response);

        // 添加当前用户的角色和权限信息到响应对象中
        response.put("role", authorService.getRolesAndAuthorities());

        return response;
    }
}
