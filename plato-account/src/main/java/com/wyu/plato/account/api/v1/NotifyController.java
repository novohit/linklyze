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
@RequestMapping("/notify/v1")
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

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
        Map<String, Object> map = this.notifyService.getCaptcha();
        return Resp.success(map);
    }

    @GetMapping("/test")
    public Resp test(@RequestParam(required = false) String name, @RequestParam(required = false) String age) {
        return Resp.success();
    }
}
