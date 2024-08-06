package com.maizi.author.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
public class RedisConnectionTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    // @Test
    public void testRedisConnection() {
        // 打印 Redis 连接工厂信息
        log.info("Redis 连接工厂: {}", redisConnectionFactory);

        // 测试 Redis 连接是否成功
        assertNotNull(redisConnectionFactory, "Redis 连接工厂不应为空");

        // 打印测试数据设置和获取过程
        String key = "test-key";
        String valueToSet = "test-value";
        stringRedisTemplate.opsForValue().set(key, valueToSet);
        log.info("设置键: '{}' 的值为: '{}'", key, valueToSet);

        String value = stringRedisTemplate.opsForValue().get(key);
        log.info("获取键 '{}' 的值: '{}'", key, value);

        // 验证存取的数据
        assertEquals(valueToSet, value, "从 Redis 获取的值应为 'test-value'");
    }
}
