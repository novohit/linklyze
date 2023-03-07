package com.wyu.plato.account.config;

import com.wyu.plato.account.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author novo
 * @since 2023-03-07
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

//    @Value("${server.servlet.context-path:}")
//    private String API_PREFIX;

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //  LoginInterceptor这里不能直接new
        //  因为LoginInterceptor里我们用到了容器中的AccountService
        registry.addInterceptor(loginInterceptor())
                // servlet.context-path会自动添加上去
                .excludePathPatterns("/account/*/register",
                        "/account/*/login",
                        "/notify/*/captcha",
                        "/notify/*/send-code",
                        "/**/test*");
    }
}
