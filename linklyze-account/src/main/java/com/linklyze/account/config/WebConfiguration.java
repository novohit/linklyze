package com.linklyze.account.config;

import com.linklyze.common.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author novo
 * @since 2023-03-07
 */
@Configuration
@Slf4j
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
        if ("true".equals(System.getenv("UN_AUTH"))) {
            log.info("调试模式 关闭鉴权 >>>>>>");
            return;
        }
        registry.addInterceptor(loginInterceptor())
                // servlet.context-path会自动添加上去
                .excludePathPatterns("/account/*/register",
                        "/account/*/login",
                        "/notify/*/captcha",
                        "/notify/*/send-code",
                        "/**/test*");
    }
}
