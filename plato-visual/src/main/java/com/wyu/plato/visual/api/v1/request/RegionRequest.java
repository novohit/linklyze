package com.wyu.plato.visual.api.v1.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author novo
 * @since 2023-04-08
 */
@Data
public class RegionRequest {

    /**
     * 短链码
     */
    @NotBlank
    private String code;

    /**
     * 开始区间(毫秒级)
     */
    @NotNull
    private Date start;

    /**
     * 结束区间(毫秒级)
     */
    @NotNull
    private Date end;
}
