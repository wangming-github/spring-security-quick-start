package com.maizi.controller;

import com.maizi.util.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/public/login/gitee")
public class GiteeLoginConfigController {

    // 应用id
    @Value("${login.gitee.clientId}")
    private String giteeClientId;

    // 重定向地址
    @Value("${login.gitee.redirectUri}")
    private String giteeCallbackEndpoint;

    @GetMapping("/config")
    public R getA() {
        HashMap<String, Object> config = new HashMap<>();
        config.put("clientId", giteeClientId);
        config.put("redirectUri", giteeCallbackEndpoint);
        return R.ok(config);
    }

}
