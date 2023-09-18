package com.wyu.plato.visual.api.v1.request;

import com.linklyze.common.model.request.PageBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author novo
 * @since 2023-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageRequest extends PageBase {

    /**
     * 短链码
     */
    @NotBlank
    private String code;
}
