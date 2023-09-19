package com.linklyze.account.api.v1.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author novo
 * @since 2023-03-01
 */
@Data
public class LoginRequest {

    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String phone;

    private String password;

    @NotBlank
    private String captcha;

    @NotBlank
    private String captchaId;
}
