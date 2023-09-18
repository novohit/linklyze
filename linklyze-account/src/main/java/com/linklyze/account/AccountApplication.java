package com.linklyze.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author novo
 * @date 2023-02-20 16:48
 */
@MapperScan("com.wyu.plato.account.mapper")
// @ComponentScan解决跨模块bean的注入问题 注意使用@ComponentScan还需要将本模块也写进行 因为覆盖了@SpringBootApplication原来的@ComponentScan
@ComponentScan(basePackages = {"com.linklyze.account", "com.linklyze.common"})
@SpringBootApplication
@EnableFeignClients
@EnableAsync
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
