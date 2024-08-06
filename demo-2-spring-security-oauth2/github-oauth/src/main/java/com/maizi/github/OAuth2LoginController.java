/*
 * Copyright 2002-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maizi.github;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制器类，处理OAuth2登录相关的请求。
 * 该类展示了如何在Spring Boot中使用OAuth2进行用户身份验证和授权。
 * <p>
 * 作者：Joe Grandja
 * 作者：Rob Winch
 */
@Controller
public class OAuth2LoginController {

    /**
     * 处理根路径请求，并将用户信息显示到页面。
     *
     * @param model            用于将数据传递到视图
     * @param authorizedClient 已授权的OAuth2客户端
     * @param oauth2User       当前已认证的OAuth2用户
     * @return 返回视图名称
     */
    @GetMapping("/")
    public String index(Model model, //
                        @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient, //
                        @AuthenticationPrincipal OAuth2User oauth2User) {//

        // 将OAuth2用户的名称添加到模型中
        model.addAttribute("userName", oauth2User.getName());

        // 将OAuth2客户端的名称添加到模型中
        model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());

        // 将OAuth2用户的属性添加到模型中
        model.addAttribute("userAttributes", oauth2User.getAttributes());

        // 返回视图名称 "index"
        return "index";
    }

    /**
     * 处理加法请求，并返回结果。
     *
     * @param a 加数一
     * @param b 加数二
     * @return 返回加法结果的字符串
     */
    @GetMapping("/add")
    @ResponseBody
    public String add(@RequestParam("a") int a, @RequestParam("b") int b) {
        int sum = a + b; // 计算加法
        return "Sum is " + sum; // 返回结果
    }

}
