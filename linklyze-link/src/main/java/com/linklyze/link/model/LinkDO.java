package com.linklyze.link.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linklyze.common.enums.LinkLevelType;
import com.linklyze.common.enums.LinkStateEnum;
import com.linklyze.common.model.BaseModel;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author novo
 * @since 2023-03-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("short_link")
public class LinkDO extends BaseModel {


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
    private LinkStateEnum state;

    /**
     * 产品level FIRST青铜SECOND黄金THIRD钻石
     */
    private LinkLevelType linkLevel;

    /**
     * logo
     */
    private String logo;
}
