package com.linklyze.account.api.v1.request;

import com.linklyze.account.annotation.PasswordEqual;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author novo
 * @since 2023-02-28 22:08
 */
@Data
@PasswordEqual
public class RegisterRequest {

    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String phone;

    @Length(min = 6, max = 12)
    private String password;

    @Length(min = 6, max = 12)
    private String rePassword;

    /**
     * 短信验证码
     */
    @NotBlank
    private String code;
}
