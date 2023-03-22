package com.wyu.plato.link.api.v1.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;

/**
 * @author novo
 * @since 2023-03-22
 */
@Data
public class LinkCreateRequest {

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
     * 原生url
     */
    @NotBlank
    private String originalUrl;

    /**
     * 域名id
     */
    @Positive
    private Long domainId;


    /**
     * 过期时间
     */
    private Date expired;
}
