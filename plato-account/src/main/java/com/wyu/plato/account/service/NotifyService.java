package com.wyu.plato.account.service;

import com.wyu.plato.account.api.v1.request.SendCodeRequest;
import com.wyu.plato.account.component.SmsComponent;
import com.wyu.plato.account.service.strategy.MapSendStrategyFactory;
import com.wyu.plato.account.service.strategy.VerifyStrategy;
import com.wyu.plato.common.enums.BizCodeEnum;
import com.wyu.plato.common.enums.SendCodeType;
import com.wyu.plato.common.constant.CacheConstants;
import com.wyu.plato.common.exception.BizException;
import com.wyu.plato.common.util.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author novo
 * @since 2023-02-23 17:10
 */
@Service
@Slf4j
public class NotifyService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SmsComponent smsComponent;

    @Autowired
    private RedisCache redisCache;

    /**
     * @Async失效情况： TODO 异步发送方案OOM问题
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
        ResponseEntity<String> forEntity = this.restTemplate.getForEntity("https://www.baidu.com/", String.class);
        String body = forEntity.getBody();
        log.info(body);
    }

    public void send(SendCodeRequest sendCodeRequest) {
        String captcha = sendCodeRequest.getCaptcha();
        String captchaId = sendCodeRequest.getCaptchaId();
        String captchaKey = CacheConstants.CAPTCHA_CODE_KEY + captchaId;
        String captchaCache = this.redisCache.getCacheObject(captchaKey);
        // 图形验证码匹配成功
        if (StringUtils.hasText(captchaCache) && captchaCache.equalsIgnoreCase(captcha)) {
            this.redisCache.deleteObject(captchaKey);
            // 发送业务验证码
            VerifyStrategy verifyStrategy = MapSendStrategyFactory.getChargeStrategy(sendCodeRequest.getType());
            verifyStrategy.send(sendCodeRequest.getTo(), sendCodeRequest.getType().name());
        } else {
            // 图形验证码不存在或匹配失败
            throw new BizException(BizCodeEnum.CODE_CAPTCHA_ERROR);
        }
    }
}
