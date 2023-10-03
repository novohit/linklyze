package com.linklyze.order.api.request;

import com.linklyze.common.model.request.PageBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author novo
 * @since 2023-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductOrderPageRequest extends PageBase {

    private String state;
}
