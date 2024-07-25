package com.maizi.common.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 * <p>
 * 该类封装了对 Redis 操作的常用方法，提供了对 Redis 中数据的设置、获取、删除和其他操作。
 * 使用 @Component 注解，使该类成为 Spring 容器中的一个 Bean，方便注入使用。
 *
 * @author maizi
 */
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 将对象序列化为 JSON 字符串并存储到 Redis 中
     *
     * @param key   Redis 键
     * @param value 要存储的值
     */
    public void set(String key, Object value) {
        try {
            // 将对象序列化为 JSON 字符串
            String jsonValue = objectMapper.writeValueAsString(value);
            // 打印序列化后的字符串，以便调试和日志记录
            log.info("【公共模块】 - 数据序列化 JSON: " + jsonValue);
            // 将序列化后的字符串存储到 Redis
            redisTemplate.opsForValue().set(key, jsonValue);
        } catch (Exception e) {
            // 捕获并记录序列化异常
            log.error("【公共模块】- 数据序列化失败:{}", e.getMessage());
        }
    }

    /**
     * 将对象存储到 Redis 中，并设置过期时间
     *
     * @param key     Redis 键
     * @param value   要存储的值
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 从 Redis 中获取值
     *
     * @param key Redis 键
     * @return Redis 中存储的值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除 Redis 中的值
     *
     * @param key Redis 键
     * @return 是否成功
     */
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 判断 Redis 中是否存在指定的键
     *
     * @param key Redis 键
     * @return 键是否存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置 Redis 中键的过期时间
     *
     * @param key     Redis 键
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 是否成功
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 从 Redis 中获取值并将其转换为指定类型的对象
     *
     * @param key   Redis 键
     * @param clazz 目标对象的类型
     * @param <T>   目标对象的类型参数
     * @return 转换后的目标对象实例
     */
    public <T> T get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }

        // 打印从 Redis 获取的值，以便调试和日志记录
        log.info("【公共模块】- Redis中获取值:" + value);
        return convertValue(value, clazz);
    }

    /**
     * 将 Redis 中存储的值转换为指定类型的对象
     *
     * @param value Redis 中存储的值
     * @param clazz 目标对象的类型
     * @param <T>   目标对象的类型参数
     * @return 转换后的目标对象实例
     */
    private <T> T convertValue(Object value, Class<T> clazz) {
        try {
            // 将 JSON 字符串转换为指定类型的对象
            return objectMapper.readValue(value.toString(), clazz);
        } catch (JsonProcessingException e) {
            // 捕获并记录转换异常
            log.error("【公共模块】- 将 Redis 中存储的值{}转换为指定类型的对象{},失败了", value, clazz);
            throw new RuntimeException("Failed to convert value to " + clazz.getSimpleName(), e);
        }
    }
}
