package com.wyu.plato.link;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author novo
 * @since 2023-03-10
 */
@SpringBootApplication
@MapperScan("com.wyu.plato.link.mapper")
@ComponentScan(basePackages = {"com.wyu.plato.link", "com.wyu.plato.common"})
@EnableFeignClients
public class LinkApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinkApplication.class, args);
    }
}
