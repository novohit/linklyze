package com.linklyze.account.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author novo
 * @since 2022-06-27 23:14
 */
@Documented // 可以将注解里的注释加入到文档里
@Retention(RetentionPolicy.RUNTIME) // 指定注解保留到运行阶段
@Target({ElementType.TYPE}) // 指定注解应用范围
@Constraint(validatedBy = PasswordValidator.class) // 传入关联类(可以用花括号传入多个关联类)
public @interface PasswordEqual {
    String message() default "两次输入密码不相同";

    // 注解里只能使用基本类型
    int min() default 0;

    int max() default Integer.MAX_VALUE;

    // 两个模板方法 写自定义注解的规范
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // 关联类 SpringBoot的编程模型
}
