package com.linklyze.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author novo
 * @since 2023-09-21
 */
@Configuration
@ConditionalOnClass(DispatcherServlet.class) // equal @ConditionalOnWebApplication
@Slf4j
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 鉴权转移到 gateway web层拦截所有
        registry.addInterceptor(loginInterceptor());
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 配置只应用于RestController注解的类 因为C端的短链跳转接口我们不需要接口前缀 这个接口我们用Controller注解
        configurer.addPathPrefix("/api", aClass -> aClass.isAnnotationPresent(RestController.class));
    }
}
