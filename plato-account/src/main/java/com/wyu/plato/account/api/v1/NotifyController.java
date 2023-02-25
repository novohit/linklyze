package com.wyu.plato.account.api.v1;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wyu.plato.account.service.NotifyService;
import com.wyu.plato.common.util.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author novo
 * @since 2023-02-23 17:09
 */
@RestController
@RequestMapping
@Slf4j
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

    @Autowired
    @Qualifier("captchaProducer")
    private DefaultKaptcha defaultKaptcha;

    /**
     * 验证码发送压测接口
     *
     * @return
     */
    @GetMapping("/send-code")
    public Resp sendCode() {
        notifyService.testSend();
        return Resp.success();
    }

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response) {
        String text = defaultKaptcha.createText();
        // TODO 存入redis并设置过期时间
        BufferedImage image = defaultKaptcha.createImage(text);
        try {
            ImageIO.write(image, "jpg", response.getOutputStream());
        } catch (IOException e) {
            log.error("获取流出错:", e);
        }
    }
}
