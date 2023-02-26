package com.wyu.plato.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author novo
 * @since 2023-02-25 22:29
 */
@Getter
@AllArgsConstructor
public enum SendCodeType {

    USER_REGISTER_PHONE,

    USER_LOGIN_PHONE,

    USER_REGISTER_EMAIl,

    USER_LOGIN_EMAIl,

    ;


    public static SendCodeType toType(Integer code) {
        return Stream.of(values())
                .filter(sendCodeType-> Objects.equals(sendCodeType.ordinal(), code))
                .findAny()
                .orElse(null);
    }
}
