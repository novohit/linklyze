package com.linklyze.order.api.request;

import com.linklyze.common.enums.PayType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlaceOrderRequest {

    /**
     * 防重令牌
     */
    private String token;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 购买数量
     */
    private Integer buyNum;


    /**
     * 订单总⾦额 TODO 暂时没用
     */
    private BigDecimal totalAmount;

    /**
     * 订单实际⽀付价格
     */
    private BigDecimal actualPayAmount;

    /**
     * 支付类型，微信-银行卡-支付宝
     */
    private PayType payType;

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
