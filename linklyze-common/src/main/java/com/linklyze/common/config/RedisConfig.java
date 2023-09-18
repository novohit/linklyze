package com.linklyze.common.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author novo
 * @since 2023-02-25 15:46
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        //设置string key和value序列器
        /**
         * GenericJackson2JsonRedisSerializer和Jackson2JsonRedisSerializer区别
         * <a href="https://blog.csdn.net/leilei1366615/article/details/122204776">https://blog.csdn.net/leilei1366615/article/details/122204776</a>
         */
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        // 如果直接使用Jackson2JsonRedisSerializer 获取存储的对象则会变为LinkedHashMap,添加ObjectMapper可解决
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置string key和value序列器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // 设置hash key和value序列器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        //设置 RedisConnection 工厂 它就是实现多种 Java Redis 客户端接入的工厂
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        //设置string key和value序列器
//        /**
//         * GenericJackson2JsonRedisSerializer和Jackson2JsonRedisSerializer区别
//         * <a href="https://blog.csdn.net/leilei1366615/article/details/122204776">https://blog.csdn.net/leilei1366615/article/details/122204776</a>
//         */
//        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
//        //设置hash key和value序列器
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
//        //设置 RedisConnection 工厂 它就是实现多种 Java Redis 客户端接入的工厂
//        redisTemplate.setConnectionFactory(connectionFactory);
//        return redisTemplate;
//    }

    @Bean(name = "lock")
    public DefaultRedisScript<Long> lockScript() {
        DefaultRedisScript<Long> limitScript = new DefaultRedisScript<>();
        // 设置脚本返回的类型
        limitScript.setResultType(Long.class);
        // 设置脚本位置 根目录从resource开始
        limitScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/lock.lua")));
        return limitScript;
    }
}
