package com.maizi.author.controller;

import com.maizi.author.feign.FeignService;
import com.maizi.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 测试 Feign 服务的控制器
 * <p>
 * 这个控制器类用于处理与 Feign 服务相关的 HTTP 请求，并将请求转发到相应的 Feign 客户端接口。
 * 主要用于测试远程服务的调用。
 * </p>
 * <p>
 * 控制器方法分为两大类：
 * 1. `admin` 角色可以访问的接口
 * 2. `user` 角色可以访问的接口
 * </p>
 */
@RestController
@RequestMapping("/feign")
public class TestFeignServiceController {

    @Autowired
    private FeignService myFeignClient; // 注入 Feign 客户端接口

    /**
     * 测试 admin 角色的问候接口
     * <p>
     * 通过 Feign 客户端调用远程服务的 `/admin/greet/{name}` 接口。
     * 该接口仅允许具有 `admin` 角色的用户访问。
     * </p>
     *
     * @param name 用户名称
     * @return 包含问候消息的响应
     */
    @GetMapping("/greet/{name}")
    public R greet(@PathVariable String name) {
        // 调用 Feign 客户端的 greet 方法，并传递 name 参数
        return myFeignClient.greet(name);
    }

    /**
     * 测试 admin 角色的回显接口
     * <p>
     * 通过 Feign 客户端调用远程服务的 `/admin/echo` 接口。
     * 该接口仅允许具有 `admin` 角色的用户访问。
     * </p>
     *
     * @param message 请求体中的消息
     * @return 回显的消息响应
     */
    @PostMapping("/echo")
    public R echo(@RequestBody String message) {
        // 调用 Feign 客户端的 echo 方法，并传递 message 请求体
        return myFeignClient.echo(message);
    }

    /**
     * 测试 admin 角色的求和接口
     * <p>
     * 通过 Feign 客户端调用远程服务的 `/admin/sum/{a}/{b}` 接口。
     * 该接口仅允许具有 `admin` 角色的用户访问。
     * </p>
     *
     * @param a 第一个整数
     * @param b 第二个整数
     * @return 两个整数的和
     */
    @GetMapping("/sum/{a}/{b}")
    public R sum(@PathVariable int a, @PathVariable int b) {
        // 调用 Feign 客户端的 sum 方法，并传递 a 和 b 参数
        return myFeignClient.sum(a, b);
    }

    /**
     * 测试 admin 角色的乘法接口
     * <p>
     * 通过 Feign 客户端调用远程服务的 `/admin/multiply/{a}/{b}` 接口。
     * 该接口仅允许具有 `admin` 角色的用户访问。
     * </p>
     *
     * @param a 第一个整数
     * @param b 第二个整数
     * @return 两个整数的乘积
     */
    @GetMapping("/multiply/{a}/{b}")
    public R multiply(@PathVariable int a, @PathVariable int b) {
        // 调用 Feign 客户端的 multiply 方法，并传递 a 和 b 参数
        return myFeignClient.multiply(a, b);
    }

    /**
     * 测试 user 角色的问候接口
     * <p>
     * 通过 Feign 客户端调用远程服务的 `/user/greet/{name}` 接口。
     * 该接口仅允许具有 `user` 角色的用户访问。
     * </p>
     *
     * @param name 用户名称
     * @return 包含问候消息的响应
     */
    @GetMapping("/user/greet/{name}")
    public R greetuser(@PathVariable String name) {
        // 调用 Feign 客户端的 greetuser 方法，并传递 name 参数
        return myFeignClient.greetuser(name);
    }

    /**
     * 测试 user 角色的回显接口
     * <p>
     * 通过 Feign 客户端调用远程服务的 `/user/echo` 接口。
     * 该接口仅允许具有 `user` 角色的用户访问。
     * </p>
     *
     * @param message 请求体中的消息
     * @return 回显的消息响应
     */
    @PostMapping("/user/echo")
    public R echouser(@RequestBody String message) {
        // 调用 Feign 客户端的 echouser 方法，并传递 message 请求体
        return myFeignClient.echouser(message);
    }

    /**
     * 测试 user 角色的求和接口
     * <p>
     * 通过 Feign 客户端调用远程服务的 `/user/sum/{a}/{b}` 接口。
     * 该接口仅允许具有 `user` 角色的用户访问。
     * </p>
     *
     * @param a 第一个整数
     * @param b 第二个整数
     * @return 两个整数的和
     */
    @GetMapping("/user/sum/{a}/{b}")
    public R sumuser(@PathVariable int a, @PathVariable int b) {
        // 调用 Feign 客户端的 sumuser 方法，并传递 a 和 b 参数
        return myFeignClient.sumuser(a, b);
    }

    /**
     * 测试 user 角色的乘法接口
     * <p>
     * 通过 Feign 客户端调用远程服务的 `/user/multiply/{a}/{b}` 接口。
     * 该接口仅允许具有 `user` 角色的用户访问。
     * </p>
     *
     * @param a 第一个整数
     * @param b 第二个整数
     * @return 两个整数的乘积
     */
    @GetMapping("/user/multiply/{a}/{b}")
    public R multiplyuser(@PathVariable int a, @PathVariable int b) {
        // 调用 Feign 客户端的 multiplyuser 方法，并传递 a 和 b 参数
        return myFeignClient.multiplyuser(a, b);
    }
}
