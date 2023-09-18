package com.wyu.plato.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigInteger;


/**
 * @author novo
 * @since 2023-03-12
 */
@Configuration
public class JacksonConfig {

    /**
     * 解决前端接收雪花id用js处理后精度丢失问题
     * 全局处理
     * 局部处理：也可以在字段上加：@JsonSerialize(using= ToStringSerializer.class)
     *
     * @param builder
     * @return
     */
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        // 全局配置序列化
        SimpleModule simpleModule = new SimpleModule();
        // 使用String来序列化Long包装类
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        // 使用String来序列化long基本类型
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        // 使用String来序列化BigInteger包装类类型
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

}
