package com.linklyze.account.api.v1;

import com.linklyze.account.api.v1.request.SendCodeRequest;
import com.linklyze.account.service.NotifyService;
import com.linklyze.common.model.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 验证接口
 *
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
    @PostMapping("/send-code")
    public Response<Void> sendCode(@RequestBody @Validated SendCodeRequest sendCodeRequest) {
        //this.notifyService.testSend();
        this.notifyService.send(sendCodeRequest);
        return Response.success();
    }

    /**
     * 获取图形验证码
     *
     * @return
     */
    @GetMapping("/captcha")
    public Response<Map<String, Object>> getCaptcha() {
        Map<String, Object> map = this.notifyService.getCaptcha();
        return Response.success(map);
    }

    @GetMapping("/test")
    public Response<Void> test(@RequestParam(required = false) String name, @RequestParam(required = false) String age) {
        return Response.success();
    }
}
