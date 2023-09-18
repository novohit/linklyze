package com.linklyze.visual;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.wyu.plato.visual.mapper")
@ComponentScan(basePackages = {"com.wyu.plato.visual", "com.wyu.plato.common"})
public class VisualApplication {

    public static void main(String[] args) {
        SpringApplication.run(VisualApplication.class, args);
    }
}
