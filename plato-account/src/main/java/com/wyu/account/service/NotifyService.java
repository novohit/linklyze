package com.wyu.account.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author novo
 * @since 2023-02-23 17:10
 */
@Service
@Slf4j
public class NotifyService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Async失效情况：
     *
     */
    @Async
    public void testSend() {
        // 发送验证码模拟
        // 方案1
//        try {
//            TimeUnit.MICROSECONDS.sleep(400);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        // 方案2
        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://www.baidu.com/", String.class);
        String body = forEntity.getBody();
        log.info(body);
    }
}
