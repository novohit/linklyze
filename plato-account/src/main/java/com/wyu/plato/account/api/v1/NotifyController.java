package com.wyu.plato.account.api.v1;

import com.wyu.plato.account.api.v1.request.SendCodeRequest;
import com.wyu.plato.account.service.NotifyService;
import com.wyu.plato.common.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
     * 发送短信验证码
     *
     * @return
     */
    @GetMapping("/send-code")
    public Resp sendCode(@RequestBody @Validated SendCodeRequest sendCodeRequest) {
        //this.notifyService.testSend();
        this.notifyService.send(sendCodeRequest);
        return Resp.success();
    }

    /**
     * 获取图形验证码
     *
     * @return
     */
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
