package com.wyu.plato.link.api.v1.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author novo
 * @since 2023-03-25
 */
@Data
public class LinkDeleteRequest {

    /**
     * 短链id
     */
    @Positive
    private Long mappingId;

    /**
     * 短链码
     */
    @NotBlank
    private String code;

    /**
     * 分组id
     * 因为B端的partition key是account_no和group_id 所以group_id要传
     */
    @Positive
    private Long groupId;

}
