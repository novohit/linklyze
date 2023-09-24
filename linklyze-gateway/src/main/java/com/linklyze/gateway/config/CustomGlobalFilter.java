package com.linklyze.gateway.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linklyze.common.enums.BizCodeEnum;
import com.linklyze.common.util.TokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author novo
 * @since 2023-03-12
 */
@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public final static String AUTHORIZATION_HEADER = "Authorization";

    public final static String BEARER = "Bearer";


    /**
     * Gateway全局过滤器和过滤器工厂中Default Filters区别：
     * GatewayFilter通过配置定义，处理逻辑是固定的，不能处理复杂的业务逻辑
     * 而GlobalFilter的逻辑可以通过代码实现
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Gateway CustomGlobalFilter...");
        String uri = exchange.getRequest().getURI().getPath();
        log.info("uri:[{}]", uri);
        List<String> excludePaths = this.getExcludePath();
        for (String pattern : excludePaths) {
            // log.info("pattern:[{}]", pattern);
            if (pathMatcher.match(pattern, uri)) {
                return chain.filter(exchange);
            }
        }
        String authorization = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
        // token为空
        if (!StringUtils.hasText(authorization)) {
            log.info("token为空");
            return error(exchange);
        }
        // token格式不正确
        if (!authorization.startsWith(BEARER)) {
            log.info("token格式错误");
            return error(exchange);

        }
        String[] tokens = authorization.split(" ");
        // 避免数组越界
        if (tokens.length != 2) {
            log.info("token格式错误");
            return error(exchange);
        }
        // 校验
        Claims claims = TokenUtil.verifyToken(tokens[1]);
        if (claims == null) {
            log.info("token不合法");
            return error(exchange);
        }

        HashMap map = claims.get("account", HashMap.class);
        String json = JSON.toJSONString(map);
        // 传递用户信息至下游
        exchange.mutate()
                .request(builder -> builder.header("user", json));
        return chain.filter(exchange);

    }

    private Mono<Void> error(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        JSONObject message = new JSONObject();
        message.put("code", BizCodeEnum.ACCOUNT_UNLOGIN.getCode());
        message.put("msg", BizCodeEnum.ACCOUNT_UNLOGIN.getMessage());
        byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        return response.writeWith(Mono.just(buffer));
    }

    private List<String> getExcludePath() {
        List<String> exclude = Stream.of(
                        "/account/*/register",
                        "/account/*/login",
                        "/notify/*/captcha",
                        "/notify/*/send-code",
                        "/**/test*"
                )
                .map(uri -> "/api" + uri)
                .collect(Collectors.toList());
        exclude.add("/*");
        return exclude;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
