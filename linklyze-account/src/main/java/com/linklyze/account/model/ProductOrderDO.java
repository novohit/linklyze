package com.linklyze.account.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import com.linklyze.common.model.BaseModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author novo
 * @since 2023-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("product_order")
@Builder
public class ProductOrderDO extends BaseModel {


    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品标题
     */
    private String productTitle;

    /**
     * 商品单价
     */
    private BigDecimal productAmount;

    /**
     * 商品快照
     */
    private String productSnapshot;

    /**
     * 购买数量
     */
    private Integer buyNum;

    /**
     * 订单唯⼀标识
     */
    private String outTradeNo;

    /**
     * NEW未支付订单,PAY已经支付订单,CANCEL超时取消订单
     */
    private String state;

    /**
     * 订单完成时间
     */
    private LocalDateTime payTime;

    /**
     * 订单总⾦额
     */
    private BigDecimal totalAmount;

    /**
     * 订单实际⽀付价格
     */
    private BigDecimal payAmount;

    /**
     * 支付类型，微信-银行卡-支付宝
     */
    private String payType;

    /**
     * 账号昵称
     */
    private String nickname;

    /**
     * ⽤户id
     */
    private Long accountNo;

    /**
     * 发票类型：0->不开发票；1->电子发票；2->纸质发票
     */
    private String billType;

    /**
     * 发票抬头
     */
    private String billHeader;

    /**
     * 发票内容
     */
    private String billContent;

    /**
     * 发票收票人电话
     */
    private String billReceiverPhone;

    /**
     * 发票收票人邮箱
     */
    private String billReceiverEmail;


}
