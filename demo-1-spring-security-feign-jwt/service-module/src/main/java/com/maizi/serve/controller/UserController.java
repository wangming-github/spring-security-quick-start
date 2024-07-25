package com.maizi.serve.controller;

import com.maizi.common.utils.R;
import com.maizi.serve.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AuthorService authorService;

    /**
     * 使用 READ_PRIVILEGES 权限访问该接口
     *
     * @param name 需要在问候中使用的名称
     * @return 返回一个包含问候信息的响应对象
     */
    @PreAuthorize("hasAuthority('READ_PRIVILEGES')")
    @GetMapping("/greet/{name}")
    public R greet(@PathVariable String name) {
        log.info("【服务模块】- 调用 greet 接口，参数: name = {}", name);
        R response = R.ok().put("data", "你好, READ_PRIVILEGES: " + name + "!");
        log.info("【服务模块】- greet 接口响应: {}", response);
        response.put("role", authorService.getRolesAndAuthorities());
        return response;
    }

    /**
     * 使用 WRITE_PRIVILEGES 权限访问该接口
     *
     * @param message 需要回显的消息
     * @return 返回一个包含回显信息的响应对象
     */
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGES')")
    @PostMapping("/echo")
    public R echo(@RequestBody String message) {


        log.info("【服务模块】- 调用 echo 接口，参数: message = {}", message);
        R response = R.ok().put("data", "你好, WRITE_PRIVILEGES: " + message);
        response.put("role", authorService.getRolesAndAuthorities());
        log.info("【服务模块】- echo 接口响应: {}", response);
        return response;
    }

    /**
     * 使用 DELETE_PRIVILEGES 权限访问该接口
     *
     * @param a 第一个加数
     * @param b 第二个加数
     * @return 返回一个包含加法结果的响应对象
     */
    @PreAuthorize("hasAuthority('DELETE_PRIVILEGES')")
    @GetMapping("/sum/{a}/{b}")
    public R sum(@PathVariable int a, @PathVariable int b) {
        log.info("【服务模块】- 调用 sum 接口，参数: a = {}, b = {}", a, b);
        int result = a + b;
        R response = R.ok().put("data", "你好, DELETE_PRIVILEGES: " + result);
        log.info("【服务模块】- sum 接口响应: {}", response);
        response.put("role", authorService.getRolesAndAuthorities());
        return response;
    }

    /**
     * 使用 UPDATE_PRIVILEGES 权限访问该接口
     *
     * @param a 第一个乘数
     * @param b 第二个乘数
     * @return 返回一个包含乘法结果的响应对象
     */
    @PreAuthorize("hasAuthority('UPDATE_PRIVILEGES')")
    @GetMapping("/multiply/{a}/{b}")
    public R multiply(@PathVariable int a, @PathVariable int b) {
        log.info("【服务模块】- 调用 multiply 接口，参数: a = {}, b = {}", a, b);
        int result = a * b;
        R response = R.ok().put("data", "你好, UPDATE_PRIVILEGES: " + result);
        log.info("【服务模块】- multiply 接口响应: {}", response);
        response.put("role", authorService.getRolesAndAuthorities());
        return response;
    }

    @PreAuthorize("hasAuthority('READ_PRIVILEGES')")
    @GetMapping("/read")
    public String readEndpoint() {
        return "Read endpoint";
    }
}
