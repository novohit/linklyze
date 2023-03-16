package com.wyu.plato.link.config;

import com.wyu.plato.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
                .excludePathPatterns("/**/test*");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 配置只应用于RestController注解的类 因为C端的短链跳转接口我们不需要接口前缀 这个接口我们用Controller注解
        configurer.addPathPrefix("/api", aClass -> aClass.isAnnotationPresent(RestController.class));
    }
}
