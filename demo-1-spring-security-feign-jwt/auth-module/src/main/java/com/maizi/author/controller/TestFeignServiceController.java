package com.maizi.author.controller;

import com.maizi.author.feign.FeignService;
import com.maizi.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class TestFeignServiceController {

    @Autowired
    private FeignService myFeignClient;


    /**
     * admin角色才有可能会访问成功
     */
    @GetMapping("/greet/{name}")
    public R greet(@PathVariable String name) {

        return myFeignClient.greet(name);
    }

    @PostMapping("/echo")
    public R echo(@RequestBody String message) {
        return myFeignClient.echo(message);
    }

    @GetMapping("/sum/{a}/{b}")
    public R sum(@PathVariable int a, @PathVariable int b) {
        return myFeignClient.sum(a, b);
    }

    @GetMapping("/multiply/{a}/{b}")
    public R multiply(@PathVariable int a, @PathVariable int b) {
        return myFeignClient.multiply(a, b);
    }

    /**
     * user角色才有可能会访问成功
     */
    @GetMapping("/user/greet/{name}")
    public R greetuser(@PathVariable String name) {
        return myFeignClient.greetuser(name);
    }

    @PostMapping("/user/echo")
    public R echouser(@RequestBody String message) {
        return myFeignClient.echouser((message));
    }

    @GetMapping("/user/sum/{a}/{b}")
    public R sumuser(@PathVariable int a, @PathVariable int b) {
        return myFeignClient.sumuser(a, b);
    }

    @GetMapping("/user/multiply/{a}/{b}")
    public R multiplyuser(@PathVariable int a, @PathVariable int b) {
        return myFeignClient.multiplyuser(a, b);
    }
}
