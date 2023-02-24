package com.wyu.account.api.v1;

import com.wyu.account.service.NotifyService;
import com.wyu.common.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author novo
 * @since 2023-02-23 17:09
 */
@RestController
@RequestMapping
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

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
}
