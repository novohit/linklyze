package com.wyu.plato.account.api.v1.request;

import lombok.Data;

/**
 * @author novo
 * @since 2023-02-25 22:34
 */
@Data
public class SendCodeRequest {

    /**
     * 发送业务验证码类型
     */
    private Integer type;

    private String captcha;

    private String captchaId;

    /**
     * 接收方，邮箱或手机号
     */
    private String to;
}
