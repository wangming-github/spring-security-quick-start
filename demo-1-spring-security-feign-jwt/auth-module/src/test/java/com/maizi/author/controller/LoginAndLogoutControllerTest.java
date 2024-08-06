package com.maizi.author.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

/**
 * 测试登录和登出功能的控制器测试类。
 * <p>
 * 该类包含测试登录和登出 API 的逻辑。使用 MockMvc 模拟 HTTP 请求和响应，并验证 API 的行为。
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc // 自动配置 MockMvc，允许在测试中使用 MockMvc 对象
// 注解用于将测试类的生命周期设置为每个类一次。 这样，测试类的实例在所有测试方法之间会被共享，因此可以在非静态的 @BeforeAll 方法中进行初始化。这样做的好处是可以避免使用静态方法，同时允许你在 @BeforeAll 方法中访问非静态的测试类成员。
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginAndLogoutControllerTest {

    @Autowired
    private MockMvc mockMvc; // 注入 MockMvc 实例，用于模拟 HTTP 请求

    @Autowired
    private ObjectMapper objectMapper; // 用于将对象转换为 JSON 字符串

    private String token; // 保存从登录响应中提取的 token

    /**
     * 在每个测试方法执行前运行的设置方法。
     * <p>
     * 发送 HTTP POST 请求到 /login 端点，进行登录操作，并提取返回的 token。
     * 该 token 将用于后续的登出测试。
     *
     * @throws Exception 如果在执行 HTTP 请求或处理响应时发生异常
     */
    @BeforeAll
    void testLoginEndpoint() throws Exception {
        // 设置测试数据，创建一个登录请求的 JSON 字符串
        String requestBody = "{\"username\":\"alice\",\"password\":\"password1\"}";

        // 发送 HTTP POST 请求到 /login 端点
        MvcResult loginResult = mockMvc.perform(MockMvcRequestBuilders.post("/login").characterEncoding("UTF-8")  // 设置字符编码为 UTF-8
                        // 设置请求的内容类型为 JSON
                        .contentType(MediaType.APPLICATION_JSON)
                        // 设置请求体内容为 JSON 字符串
                        .content(requestBody))
                // 期望响应状态码为 200 OK
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 期望响应的 JSON 体中存在 "msg" 字段且值为 "认证成功!"
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("Authentication success!"))
                // 期望响应的 JSON 体中存在 "code" 字段且值为 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                // 期望响应的 JSON 体中存在 "token" 字段
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
                // 打印日志
                .andDo(result -> {
                    // 打印请求和响应信息
                    log.info("[TEST]请求和响应信息：");
                    log.info("[TEST] 响应体内容：" + new String(result.getResponse().getContentAsString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                })
                // 获取 MvcResult 对象
                .andReturn();

        // 从登录响应中提取 token
        String responseBody = loginResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        token = jsonNode.get("token").asText(); // 保存 token 供后续测试使用
    }


    // 测试 /api/admin 端点，仅允许管理员访问
    @Test
    void testAdminEndpoint() throws Exception {
        // 发送请求到 /api/admin 端点，并添加 Authorization 头以传递 token
        // 验证响应状态码为 200 OK，表示请求成功
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/admin")  // 发送 GET 请求到 /api/admin
                .header(HttpHeaders.AUTHORIZATION, token) // 添加 Authorization 头部以传递 JWT token
                .characterEncoding("UTF-8")  // 设置字符编码为 UTF-8
                .contentType(MediaType.APPLICATION_JSON)   // 设置请求内容类型为 JSON
        );
        perform.andExpect(MockMvcResultMatchers.status().isOk())// 验证响应状态码为 200 OK
                .andDo(result -> {
                    log.info("[TEST]请求和响应信息：");
                    log.info("[TEST] 响应体内容：" + new String(result.getResponse().getContentAsString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                });
    }

    // 测试 /feign/greet/{name} 端点
    @Test
    void testGreet() throws Exception {
        // 发送请求到 /feign/greet/{name} 端点，并添加 Authorization 头以传递 token
        // 验证响应状态码为 200 OK，表示请求成功
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/feign/greet/{name}", "maizi") // 发送 GET 请求到 /feign/greet/{name}
                .header(HttpHeaders.AUTHORIZATION, token) // 添加 Authorization 头部以传递 JWT token
                .characterEncoding("UTF-8")  // 设置字符编码为 UTF-8
                .contentType(MediaType.APPLICATION_JSON)   // 设置请求内容类型为 JSON
        );
        perform.andExpect(MockMvcResultMatchers.status().isOk()) // 验证响应状态码为 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("success")) //
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200)) //
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("你好, READ_PRIVILEGES: maizi!"))//
                .andDo(result -> {
                    log.info("[TEST]请求和响应信息：");
                    log.info("[TEST] 响应体内容：" + new String(result.getResponse().getContentAsString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                }); //
    }

    @Test
    void testSum() throws Exception {
        // 发送请求到 /feign/sum/{a}/{b} 端点，并添加 Authorization 头以传递 token
        // 验证响应状态码为 200 OK，表示请求成功
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/feign/sum/{a}/{b}", 2, 3) // 发送 GET 请求到 /feign/sum/{a}/{b}
                .header(HttpHeaders.AUTHORIZATION, token) // 添加 Authorization 头部以传递 JWT token
                .characterEncoding("UTF-8")  // 设置字符编码为 UTF-8
                .contentType(MediaType.APPLICATION_JSON)   // 设置请求内容类型为 JSON
        );
        perform.andExpect(MockMvcResultMatchers.status().isOk()) // 验证响应状态码为 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("success")) //
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200)) //
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("你好, DELETE_PRIVILEGES: 5")) //
                .andDo(result -> {
                    log.info("[TEST]请求和响应信息：");
                    log.info("[TEST] 响应体内容：" + new String(result.getResponse().getContentAsString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                }); //
    }

    @Test
    void testMultiply() throws Exception {
        // 发送请求到 /feign/multiply/{a}/{b} 端点，并添加 Authorization 头以传递 token
        // 验证响应状态码为 200 OK，表示请求成功
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/feign/multiply/{a}/{b}", 3, 6) // 发送 GET 请求到 /feign/multiply/{a}/{b}
                .header(HttpHeaders.AUTHORIZATION, token) // 添加 Authorization 头部以传递 JWT token
                .characterEncoding("UTF-8")  // 设置字符编码为 UTF-8
                .contentType(MediaType.APPLICATION_JSON)   // 设置请求内容类型为 JSON
        );
        perform.andExpect(MockMvcResultMatchers.status().isOk()) // 验证响应状态码为 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("success")) //
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200)) //
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("你好, UPDATE_PRIVILEGES: 18"))//
                .andDo(result -> {
                    log.info("[TEST]请求和响应信息：");
                    log.info("[TEST] 响应体内容：" + new String(result.getResponse().getContentAsString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                }); //
    }

    @Test
    void testEcho() throws Exception {
        // 发送请求到 /feign/echo 端点，并添加 Authorization 头以传递 token
        // 验证响应状态码为 200 OK，表示请求成功
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/feign/echo") // 发送 POST 请求到 /feign/echo
                .header(HttpHeaders.AUTHORIZATION, token) // 添加 Authorization 头部以传递 JWT token
                .characterEncoding("UTF-8")  // 设置字符编码为 UTF-8
                .contentType(MediaType.APPLICATION_JSON)   // 设置请求内容类型为 JSON
                .content("{\"message\":\"maizi\"}") // 请求体中的 JSON 数据
        );
        perform.andExpect(MockMvcResultMatchers.status().isOk()) // 验证响应状态码为 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("success")) //
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200)) //
                .andDo(result -> {
                    log.info("[TEST]请求和响应信息：");
                    log.info("[TEST] 响应体内容：" + new String(result.getResponse().getContentAsString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                }); //

    }

    /**
     * 测试登出功能。
     * <p>
     * 使用在 testLoginEndpoint() 方法中提取的 token 执行 POST 请求到 /logout 端点，
     * 并验证响应状态码和消息。
     *
     * @throws Exception 如果在执行 HTTP 请求或处理响应时发生异常
     */
    @Test
    void testLogout() throws Exception {
        // 确保 token 已经被设置
        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("Token is not available. Ensure that testLoginEndpoint() was run and token was obtained.");
        }

        // 使用提取的 token 执行 POST 请求到 /logout 端点，并将 token 添加到 Authorization 头中
        mockMvc.perform(MockMvcRequestBuilders.post("/logout")
                        // 添加 Authorization 头，值为提取的 token
                        .header(HttpHeaders.AUTHORIZATION, token)
                        // 设置字符编码为 UTF-8
                        .characterEncoding("UTF-8")
                        // 设置请求的内容类型为 JSON
                        .contentType(MediaType.APPLICATION_JSON))
                // 期望响应状态码为 200 OK
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 期望响应的 JSON 体中存在 "msg" 字段且值为 "注销成功!"
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("logout success!"))
                // 期望响应的 JSON 体中存在 "code" 字段且值为 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))//
                .andDo(result -> {
                    log.info("[TEST]请求和响应信息：");
                    log.info("[TEST] 响应体内容：" + new String(result.getResponse().getContentAsString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                }); //
    }
}

