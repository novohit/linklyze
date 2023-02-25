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

    ;


    public static SendCodeType toType(Integer code) {
        return Stream.of(values())
                .filter(sendCodeType-> Objects.equals(sendCodeType.name(), code))
                .findAny()
                .orElse(null);
    }
}
