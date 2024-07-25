package com.maizi.common.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;

/**
 * Redis 配置类
 * <p>
 * 该类配置了 Spring Data Redis 的 RedisTemplate，以便进行 Redis 操作。
 * 它主要做了以下几件事情：
 * <ul>
 *     <li>创建并配置 RedisTemplate 实例</li>
 *     <li>设置 RedisTemplate 的序列化器，以确保在存储和读取数据时能够正确处理对象</li>
 *     <li>配置 ObjectMapper 以支持 JSON 对象的序列化和反序列化</li>
 * </ul>
 * <p>
 * RedisTemplate 用于执行 Redis 操作。序列化器用于定义数据在 Redis 中的存储格式，
 * 这里使用了 Jackson2JsonRedisSerializer 来序列化对象数据为 JSON 格式，确保复杂对象能够被正确处理。
 * 同时，为了保证 Redis 中的键和值能够以 String 格式存储，我们使用 StringRedisSerializer。
 * </p>
 *
 * @author maizi
 */
@Slf4j
@Configuration
public class RedisConfig {

    /**
     * 依赖注入完成后，打印初始化信息。
     */
    @PostConstruct
    public void init() {
        log.info("【公共模块】- 配置了 Spring Data Redis 的 RedisTemplate 已经加入容器中...");
    }

    /**
     * 配置 RedisTemplate 实例
     * <p>
     * 此方法创建一个 RedisTemplate 实例，并配置其序列化器：
     * <ul>
     *     <li>键和哈希键使用 StringRedisSerializer 进行序列化</li>
     *     <li>值和哈希值使用 Jackson2JsonRedisSerializer 进行序列化</li>
     * </ul>
     * Jackson2JsonRedisSerializer 使用 ObjectMapper 配置了序列化和反序列化行为，
     * 以支持复杂对象和多态对象的处理。
     * </p>
     *
     * @param redisConnectionFactory RedisConnectionFactory 实例，用于连接 Redis
     * @return 配置好的 RedisTemplate 实例
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建 RedisTemplate 实例
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置 Redis 连接工厂
        template.setConnectionFactory(redisConnectionFactory);

        // 创建 Jackson2JsonRedisSerializer 实例，用于对象的 JSON 序列化和反序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        // 配置 ObjectMapper 实例
        ObjectMapper om = new ObjectMapper();
        // 设置 ObjectMapper 的可见性规则，使所有属性都可见
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 启用默认类型信息处理，以支持多态类型的序列化和反序列化
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        // 将配置好的 ObjectMapper 设置给 Jackson2JsonRedisSerializer
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 创建 StringRedisSerializer 实例，用于键的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 配置 RedisTemplate 的键序列化器
        template.setKeySerializer(stringRedisSerializer);
        // 配置 RedisTemplate 的哈希键序列化器
        template.setHashKeySerializer(stringRedisSerializer);
        // 配置 RedisTemplate 的值序列化器
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // 配置 RedisTemplate 的哈希值序列化器
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        // 初始化 RedisTemplate 实例
        template.afterPropertiesSet();
        return template;
    }
}
