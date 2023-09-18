package com.wyu.plato.link.vo;

import com.wyu.plato.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author novo
 * @since 2023-04-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LinkVO extends BaseModel {
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
