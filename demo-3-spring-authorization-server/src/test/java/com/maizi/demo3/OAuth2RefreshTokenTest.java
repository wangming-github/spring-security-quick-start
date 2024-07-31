package com.maizi.demo3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class OAuth2RefreshTokenTest {


    private String authorizationCode = "H7LAKRWv8LfunVQjTHMz-mB0kmprko1cV5-RsjDB49sM3pl3z7MD6sdfrlqr68VOGsbA-InB-qHeHuhhzYI3HEIg9Hho31HemDxYLb3ZztuREiJQRxPb0RkLV145libV";
    private String accessToken;
    private String refreshToken;


    // 注入 MockMvc，用于模拟 HTTP 请求
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    @WithMockUser(username = "user1", password = "123")
    public void testAuthorizationCode() {
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/oauth2/authorize")
                            .param("response_type", "code")
                            .param("client_id", "messaging-client")
                            .param("redirect_uri", "https://www.baidu.com")
                            .param("scope", "openid profile message.read message.write"))
                    .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.redirectedUrl("https://www.baidu.com"))
                    .andExpect(MockMvcResultMatchers.header().exists("Location"))
                    .andExpect(MockMvcResultMatchers.header().string("Location", "https://www.baidu.com?code=someCode"))
                    .andReturn();

            // 打印响应内容
            System.out.println("Response Content: " + result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();  // 打印异常堆栈，帮助调试
        }
    }


}
