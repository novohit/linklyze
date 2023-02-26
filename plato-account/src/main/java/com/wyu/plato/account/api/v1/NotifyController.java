package com.wyu.plato.account.api.v1;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.gson.Gson;
import com.wyu.plato.account.api.v1.request.SendCodeRequest;
import com.wyu.plato.account.component.SmsComponent;
import com.wyu.plato.account.service.NotifyService;
import com.wyu.plato.common.constant.CacheConstants;
import com.wyu.plato.common.util.RedisCache;
import com.wyu.plato.common.util.Resp;
import com.wyu.plato.common.util.uuid.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author novo
 * @since 2023-02-23 17:09
 */
@RestController
@RequestMapping("/v1")
@Slf4j
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

    @Autowired
    @Qualifier("captchaProducer")
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SmsComponent smsComponent;

    /**
     * 验证码发送压测接口
     *
     * @return
     */
    @GetMapping("/send-code")
    public Resp sendCode(@RequestBody @Validated SendCodeRequest sendCodeRequest) {
        //this.notifyService.testSend();
        this.notifyService.send(sendCodeRequest);
        return Resp.success();
    }

    @GetMapping("/captcha")
    public Resp getCaptcha() {
        // 生成图形验证码
        String captcha = this.defaultKaptcha.createText();
        BufferedImage image = this.defaultKaptcha.createImage(captcha);
        // 存入redis并设置过期时间
        String captchaId = IdUtils.simpleUUID();
        String captchaKey = CacheConstants.CAPTCHA_CODE_KEY + captchaId;

        if (log.isDebugEnabled()) {
            log.debug("captchaId:[{}],captcha:[{}]", captchaId, captcha);
        }

        this.redisCache.setCacheObject(captchaKey, captcha, CacheConstants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            log.error("获取流出错:", e);
            return Resp.error(e.getMessage());
        }

        String img = Base64.encodeBase64String(os.toByteArray());
        Map<Object, Object> map = new HashMap<>();
        map.put("img", img);
        map.put("captchaId", captchaId);
        return Resp.success(map);
    }

    @GetMapping("/test")
    public Resp test(@RequestParam(required = false) String name, @RequestParam(required = false) String age) {
        return Resp.success();
    }
}
