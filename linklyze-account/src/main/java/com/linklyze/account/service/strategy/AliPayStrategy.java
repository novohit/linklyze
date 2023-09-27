package com.linklyze.account.service.strategy;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.linklyze.common.config.AliPayProperties;
import com.linklyze.common.enums.PayType;
import com.linklyze.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AliPayStrategy implements PayStrategy {

    private final AliPayProperties aliPayProperties;

    public AliPayStrategy(AliPayProperties aliPayProperties) {
        this.aliPayProperties = aliPayProperties;
    }

    @Override
    public PayType mark() {
        return PayType.ALI_PAY;
    }

    @Override
    public PayResponse pay(PayRequest payRequest) {
        try {
            AlipayConfig alipayConfig = new AlipayConfig();
            BeanUtils.copyProperties(aliPayProperties, alipayConfig);
            AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            // SDK已经封装掉了公共参数，这里只需要传入业务参数
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            model.setSubject(payRequest.getTitle());
            model.setBody(payRequest.getDescription());
            model.setTimeoutExpress("60m");
            model.setOutTradeNo(payRequest.getOrderOutTradeNo());
            model.setTotalAmount(payRequest.getActualPayAmount().toString());
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            request.setNotifyUrl(aliPayProperties.getNotifyUrl());
            request.setBizModel(model);
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
            log.info("发起支付宝支付，订单号：{}，账户号：{}，订单详情：{}，订单金额：{} \n调用支付返回：\n\n{}\n",
                    payRequest.getOrderOutTradeNo(),
                    payRequest.getAccountNo(),
                    payRequest.getDescription(),
                    payRequest.getActualPayAmount(),
                    JSON.toJSONString(response));
            if (!response.isSuccess()) {
                throw new BizException("调用支付宝发起支付异常");
            }
            return new PayResponse(response.getBody());
        } catch (AlipayApiException ex) {
            throw new BizException("调用支付宝支付异常");
        }
    }

    @Override
    public PayResponse cancel(PayRequest payRequest) {
        return null;
    }

    @Override
    public PayResponse refund(PayRequest payRequest) {
        return null;
    }
}
