package com.linklyze.account.api.v1.request;

import com.linklyze.account.annotation.EmailOrPhone;
import com.linklyze.common.enums.SendCodeType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author novo
 * @since 2023-02-25 22:34
 */
@Data
@EmailOrPhone
public class SendCodeRequest {

    /**
     * 发送业务验证码类型
     */
    private SendCodeType type;

    /**
     * 接收方，邮箱或手机号
     */
    @NotBlank
    private String to;
}
