package com.maizi.author.feign;

import com.maizi.common.feign.FeignConfig;
import com.maizi.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign 客户端接口，用于与服务模块进行通信。
 * <p>
 * 该接口定义了多个用于服务间调用的方法，包括处理请求体、路径参数等。
 * Feign 客户端会根据接口定义自动生成实现，简化了服务间调用的复杂性。
 *
 * @author maizi
 */
@FeignClient(name = "service-module", configuration = FeignConfig.class)
public interface FeignService {

    /*
     * ----1. 将【SpuBoundsTo】对象通过【@RequestBody】注解转为 JSON
     * ----2. 找到注册中心中【service-module】服务【/admin/greet/{name}】接口
     * ----3. 将 JSON 数据放在请求体的位置，调用【/admin/greet/{name}】接口
     * ----4. 对方服务收到请求体中的 JSON 数据，将请求体中的 JSON 转为【SpuBoundsEntity】
     * 只需要 JSON 属性名一一对应即可，双方服务无需使用同一个传输对象 TO。
     *
     */

    /**
     * admin 角色才能访问此接口。
     *
     * @param name 用户名
     * @return 返回的结果
     */
    @GetMapping("/admin/greet/{name}")
    R greet(@PathVariable("name") String name);

    /**
     * admin 角色才能访问此接口。
     *
     * @param message 要回显的消息
     * @return 返回的结果
     */
    @PostMapping("/admin/echo")
    R echo(@RequestBody String message);

    /**
     * admin 角色才能访问此接口。
     *
     * @param a 第一个加数
     * @param b 第二个加数
     * @return 返回的结果
     */
    @GetMapping("/admin/sum/{a}/{b}")
    R sum(@PathVariable("a") int a, @PathVariable("b") int b);

    /**
     * admin 角色才能访问此接口。
     *
     * @param a 第一个乘数
     * @param b 第二个乘数
     * @return 返回的结果
     */
    @GetMapping("/admin/multiply/{a}/{b}")
    R multiply(@PathVariable("a") int a, @PathVariable("b") int b);

    /**
     * user 角色才能访问此接口。
     *
     * @param name 用户名
     * @return 返回的结果
     */
    @GetMapping("/user/greet/{name}")
    R greetuser(@PathVariable("name") String name);

    /**
     * user 角色才能访问此接口。
     *
     * @param message 要回显的消息
     * @return 返回的结果
     */
    @PostMapping("/user/echo")
    R echouser(@RequestBody String message);

    /**
     * user 角色才能访问此接口。
     *
     * @param a 第一个加数
     * @param b 第二个加数
     * @return 返回的结果
     */
    @GetMapping("/user/sum/{a}/{b}")
    R sumuser(@PathVariable("a") int a, @PathVariable("b") int b);

    /**
     * user 角色才能访问此接口。
     *
     * @param a 第一个乘数
     * @param b 第二个乘数
     * @return 返回的结果
     */
    @GetMapping("/user/multiply/{a}/{b}")
    R multiplyuser(@PathVariable("a") int a, @PathVariable("b") int b);
}
