package com.linklyze.link.api.v1.request;

import com.linklyze.common.model.request.PageBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Positive;

/**
 * @author novo
 * @since 2023-03-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageRequest extends PageBase {

    /**
     * 分组id
     */
    @Positive
    private Long groupId;
}
