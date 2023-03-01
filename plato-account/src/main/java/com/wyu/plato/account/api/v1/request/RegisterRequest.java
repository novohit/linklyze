package com.wyu.plato.account.api.v1.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author novo
 * @since 2023-02-28 22:08
 */
@Data
public class RegisterRequest {

    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String phone;

    private String password;

    /**
     * 短信验证码
     */
    @NotBlank
    private String code;
}
