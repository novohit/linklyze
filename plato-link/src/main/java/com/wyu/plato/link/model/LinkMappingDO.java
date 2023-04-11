package com.wyu.plato.link.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyu.plato.common.model.BaseModel;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author novo
 * @since 2023-03-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("short_link_mapping")
public class LinkMappingDO extends BaseModel {


    /**
     * 分组id
     */
    private Long groupId;

    /**
     * 短链标题
     */
    private String title;

    /**
     * 原url地址
     */
    @JsonIgnore
    private String originalUrl;

    /**
     * 短链域名
     */
    private String domain;

    /**
     * 短链码
     */
    private String code;

    /**
     * 长链的hash码 方便查找
     */
    @JsonIgnore
    private String longHash;

    /**
     * 过期时间 永久为null
     */
    private Date expired;

    /**
     * 账户唯一标识
     */
    private Long accountNo;

    /**
     * 短链状态 lock：锁定 active：可用
     */
    private String state;

    /**
     * 产品level FIRST青铜SECOND黄金THIRD钻石
     */
    private String linkLevel;

    /**
     * logo
     */
    private String logo;
}
