package com.wyu.plato.link.api.v1.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;

/**
 * @author novo
 * @since 2023-03-25
 */
@Data
public class LinkUpdateRequest {

    /**
     * 短链id
     */
    @Positive
    private Long mappingId;

    /**
     * 分组id
     */
    @Positive
    private Long groupId;

    /**
     * 短链标题
     */
    @NotBlank
    private String title;

    /**
     * 域名id
     */
    @Positive
    private Long domainId;

    /**
     * 域名 前端不需要传
     */
    private String domain;


    /**
     * 过期时间 永久则不用传
     */
    private Date expired;
}
