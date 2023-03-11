package com.wyu.link;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author novo
 * @since 2023-03-10
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LinkApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinkApplication.class, args);
    }
}
