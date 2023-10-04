package com.linklyze.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linklyze.common.enums.MessageEventType;
import com.linklyze.common.model.bo.CustomMessage;
import com.linklyze.common.util.JsonUtil;
import com.linklyze.order.mapper.TrafficMapper;
import com.linklyze.order.model.ProductOrderDO;
import com.linklyze.order.model.TrafficDO;
import com.linklyze.order.model.TrafficPackageDO;
import com.linklyze.order.service.ProductOrderService;
import com.linklyze.order.service.TrafficService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author novo
 * @since 2023-02-21
 */
@Service
@Slf4j
public class TrafficServiceImpl extends ServiceImpl<TrafficMapper, TrafficDO> implements TrafficService {


    private final ProductOrderService productOrderService;

    private final TrafficMapper trafficMapper;

    public TrafficServiceImpl(ProductOrderService productOrderService, TrafficMapper trafficMapper) {
        this.productOrderService = productOrderService;
        this.trafficMapper = trafficMapper;
    }

    @Override
    public void changeTraffic(CustomMessage message) {
        Long accountNo = message.getAccountNo();
        if (MessageEventType.ORDER_PAID.equals(message.getEventType())) {
            // 支付成功 流量包充值
            String outTradeNo = message.getBizId();
            // 获取下单商品快照
            ProductOrderDO orderDO = productOrderService.findByOutTradeNo(accountNo, outTradeNo);
            String snapshotJson = orderDO.getProductSnapshot();
            TrafficPackageDO snapshot = JsonUtil.json2Obj(snapshotJson, TrafficPackageDO.class);

            TrafficDO trafficDO = TrafficDO.builder()
                    .accountNo(accountNo)
                    .outTradeNo(outTradeNo)
                    .level(snapshot.getLevel())
                    .dayLimit(snapshot.getDayTimes() * orderDO.getBuyNum())
                    .dayUsed(0)
                    .totalLimit(snapshot.getTotalTimes())
                    .pluginType(snapshot.getPluginType())
                    .productId(snapshot.getId())
                    .expiredDate(LocalDateTime.now().plusDays(snapshot.getValidDay()))
                    .build();

            int rows = trafficMapper.insert(trafficDO);
            if (rows > 0) {
                log.info(
                        "流量包充值成功，订单号：{}，账号：{}",
                        outTradeNo,
                        accountNo
                );
            }

        } else {

        }
    }
}
