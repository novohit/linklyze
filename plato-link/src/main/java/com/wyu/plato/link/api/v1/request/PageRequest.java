package com.wyu.plato.link.api.v1.request;

import lombok.Data;

import javax.validation.constraints.Positive;

/**
 * @author novo
 * @since 2023-03-25
 */
@Data
public class PageRequest {

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

    /**
     * 分组id
     */
    @Positive
    private Long groupId;
}
