package com.maizi.demo.test1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;


// @RunWith(SpringJUnit4ClassRunner.class)
// @SpringBootTest// 用于加载整个 Spring Boot 应用上下文，通常用于集成测试。
// @AutoConfigureMockMvc//它通常与 @SpringBootTest 一起使用，以便在加载整个 Spring 上下文的情况下进行 Web 层测试。
// @ExtendWith(SpringExtension.class)// 启用 Spring 的支持
@WebMvcTest(MyController.class) // 用于只加载 Web 层的上下文，适用于测试 Controller 层。
public class MyControllerTest {

    @Autowired
    private MockMvc mockMvc; // 注入 MockMvc 用于模拟 HTTP 请求

    /**
     * 在每个测试方法执行前初始化 MockMvc 对象
     */
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MyController()).build();
    }

    @Test
    public void testAddition() {
        int sum = 1 + 1;
        assertEquals(2, sum);
    }


    @Test
    public void hello() throws Exception {
        // 使用 MockMvc 模拟一个 GET 请求到 /api/hello 路径
        mockMvc.perform(MockMvcRequestBuilders.get("/api/hello"))
                // 期望返回的 HTTP 状态码是 200 (OK)
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 期望返回的内容是 "Hello, World!"
                .andExpect(MockMvcResultMatchers.content().string("Hello, World!"));
    }

    @Test
    public void greet() throws Exception {
        // 使用 MockMvc 模拟一个 GET 请求到 /api/greet 路径
        mockMvc.perform(MockMvcRequestBuilders.get("/api/greet"))
                // 期望返回的 HTTP 状态码是 200 (OK)
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 期望返回的内容是字符串 "Greetings from Spring Boot!"
                .andExpect(MockMvcResultMatchers.content().string("Greetings from Spring Boot!"));
    }
}
