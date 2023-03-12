package com.wyu.plato.link;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author novo
 * @since 2023-03-10
 */
@SpringBootApplication
@MapperScan("com.wyu.plato.link.mapper")
public class LinkApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinkApplication.class, args);
    }
}
