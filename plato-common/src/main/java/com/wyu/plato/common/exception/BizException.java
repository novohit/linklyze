package com.wyu.plato.common.exception;

import com.wyu.plato.common.enums.BizCodeEnum;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author novo
 * @date 2023-02-21 13:45
 */
@Data
public class BizException extends RuntimeException {

    protected Integer code;

    protected String msg;

    protected Integer httpStatusCode = 200;

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(BizCodeEnum bizCodeEnum) {
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
    }

    public BizException(BizCodeEnum bizCodeEnum, HttpStatus httpStatus) {
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
        this.httpStatusCode = httpStatus.value();
    }
}
