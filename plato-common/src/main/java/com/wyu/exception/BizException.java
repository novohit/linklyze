package com.wyu.exception;

import com.wyu.enums.BizCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;

/**
 * @author novo
 * @date 2023-02-21 13:45
 */
@Data
public class BizException extends RuntimeException {

    protected Integer code;

    protected String msg;

    protected Integer httpStatusCode = 500;

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
}
