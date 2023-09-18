package com.linklyze.link.api.v1.request;

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
     * 短链码
     * C端的partition key 要传
     */
    @NotBlank
    private String code;

//    /**
//     * 修改group_id 要传
//     */
//    private Long newGroupId;

    /**
     * 分组id
     * 暂时不允许更改，partition key的更改涉及到数据迁移
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
