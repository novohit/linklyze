package com.linklyze.account.annotation;

import com.linklyze.account.api.v1.request.SendCodeRequest;
import com.linklyze.common.util.CheckUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author novo
 * @since 2023-02-26 11:58
 */
public class EmailOrPhoneValidator implements ConstraintValidator<EmailOrPhone, SendCodeRequest> {
    private int min;

    private int max;

    @Override
    public void initialize(EmailOrPhone constraintAnnotation) {
        // 获取注解中的参数
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(SendCodeRequest sendCodeRequest, ConstraintValidatorContext constraintValidatorContext) {
        // TODO 可以写得更加严谨 加上判空
        switch (sendCodeRequest.getType()) {
            case USER_REGISTER_PHONE:
            case USER_LOGIN_PHONE:
                return CheckUtil.isPhone(sendCodeRequest.getTo());
            case USER_REGISTER_EMAIl:
            case USER_LOGIN_EMAIl:
                return CheckUtil.isEmail(sendCodeRequest.getTo());
            default:
                // TODO
                return false;
        }
    }
}
