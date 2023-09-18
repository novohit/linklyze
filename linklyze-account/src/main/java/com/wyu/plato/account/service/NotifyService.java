package com.wyu.plato.account.service;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wyu.plato.account.api.v1.request.SendCodeRequest;
import com.wyu.plato.account.component.SmsComponent;
import com.wyu.plato.account.service.strategy.MapSendStrategyFactory;
import com.wyu.plato.account.service.strategy.VerifyStrategy;
import com.wyu.plato.common.enums.BizCodeEnum;
import com.wyu.plato.common.constant.CacheConstants;
import com.wyu.plato.common.enums.SendCodeType;
import com.wyu.plato.common.exception.BizException;
import com.wyu.plato.common.util.RedisCache;
import com.wyu.plato.common.util.uuid.IDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

    @Autowired
    private SmsComponent smsComponent;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    @Qualifier("captchaProducer")
    private DefaultKaptcha defaultKaptcha;


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
            throw new BizException(BizCodeEnum.CAPTCHA_ERROR);
        }
    }

    public Map<String, Object> getCaptcha() {
        // 生成图形验证码
        String captcha = this.defaultKaptcha.createText();
        BufferedImage image = this.defaultKaptcha.createImage(captcha);
        // 存入redis并设置过期时间
        String captchaId = IDUtil.simpleUUID();
        String captchaKey = CacheConstants.CAPTCHA_CODE_KEY + captchaId;

        log.info("captchaId:[{}],captcha:[{}]", captchaId, captcha);

        this.redisCache.setCacheObject(captchaKey, captcha, CacheConstants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出

        try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", os);
            Map<String, Object> map = new HashMap<>();
            String img = Base64.encodeBase64String(os.toByteArray());
            map.put("img", img);
            map.put("captcha_id", captchaId);
            return map;
        } catch (IOException e) {
            log.error("生成图像验证码 获取流出错:", e);
        }

        return null;
    }

    public boolean verify(SendCodeType type, String to, String code) {
        // key account-service:code:register:to
        String codeKey = String.format(CacheConstants.CHECK_CODE_KEY, type.name(), to);
        // value code_timestamp
        String codeCache = this.redisCache.getCacheObject(codeKey);
        log.info("get redis [{}]", codeCache);
        if (StringUtils.hasText(codeCache)) {
            String value = codeCache.split("_")[0];
            // 验证码匹配
            if (code.equalsIgnoreCase(value)) {
                this.redisCache.deleteObject(codeKey);
                return true;
            }
        }
        return false;
    }
}
