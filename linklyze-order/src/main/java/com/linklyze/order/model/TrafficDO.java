package com.linklyze.order.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.linklyze.common.model.BaseModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author novo
 * @since 2023-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("traffic")
public class TrafficDO extends BaseModel {


    // @TableId(value = "id", type = IdType.AUTO)
    // mybatis-plus也可以设置雪花ID，type = ASSIGN_ID 但是不常用，我们直接通过sharding-jdbc的配置
    // private Long id;

    /**
     * 每天限制多少条短链
     */
    private Integer dayLimit;

    /**
     * 当天用了多少条短链
     */
    private Integer dayUsed;

    /**
     * 总次数，活码才用
     */
    private Integer totalLimit;

    /**
     * 账户
     */
    private Long accountNo;

    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 产品层级：FIRST青铜 SECOND黄金THIRD砖石
     */
    private String level;

    /**
     * 过期时间
     */
    private LocalDateTime expiredDate;

    /**
     * 插件类型
     */
    private String pluginType;

    /**
     * 商品主键
     */
    private Long productId;


}
