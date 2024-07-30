package com.maizi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Bean:passwordEncoder");
        return new BCryptPasswordEncoder(); // 使用 BCrypt 加密密码
    }
}
