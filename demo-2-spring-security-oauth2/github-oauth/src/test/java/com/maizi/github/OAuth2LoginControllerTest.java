package com.maizi.github;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 测试类，用于测试 OAuth2LoginController 控制器的行为。
 */
@WebMvcTest(OAuth2LoginController.class)
public class OAuth2LoginControllerTest {

    @Autowired
    private MockMvc mockMvc; // 自动注入 MockMvc 对象，用于模拟 HTTP 请求和验证响应

    /**
     * 在每个测试方法执行前初始化 MockMvc 对象
     */
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OAuth2LoginController()).build();
    }

    /**
     * 测试加法功能
     *
     * @throws Exception 可能抛出的异常
     */
    @Test
    public void testAdd() throws Exception {
        // 模拟 GET 请求，并传递参数 a 和 b，检查返回的状态码和内容
        mockMvc.perform(get("/add")//
                        .param("a", "5") // 添加请求参数 a
                        .param("b", "3")) // 添加请求参数 b
                .andExpect(status().isOk()) // 预期响应状态码为 200 OK
                .andExpect(content().string("Sum is 8")); // 预期响应内容为 "Sum is 8"
    }
}
