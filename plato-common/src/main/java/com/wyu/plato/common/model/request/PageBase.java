package com.wyu.plato.common.model.request;

import lombok.Data;

import javax.validation.constraints.Positive;

/**
 * @author novo
 * @since 2023-04-07
 */
@Data
public class PageBase {
    /**
     * 页码
     */
    @Positive
    private Integer page;

    /**
     * 条数
     */
    @Positive
    private Integer size;
}
