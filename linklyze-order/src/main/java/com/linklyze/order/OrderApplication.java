package com.linklyze.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author novo
 * @since 2023-02-20
 */
@MapperScan("com.linklyze.order.mapper")
@ComponentScan(basePackages = {"com.linklyze.order", "com.linklyze.common"})
@SpringBootApplication
@EnableFeignClients
@EnableAsync
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
