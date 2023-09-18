package com.linklyze.link.api.v1.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author novo
 * @since 2023-03-12
 */
@Data
public class GroupUpdateRequest {

    /**
     * 分组id
     */
    @Positive
    private Long id;

    /**
     * 分组标题
     */
    @NotBlank
    private String title;
}
