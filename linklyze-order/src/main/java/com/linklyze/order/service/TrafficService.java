package com.linklyze.order.service;

import com.linklyze.common.model.bo.CustomMessage;
import com.linklyze.order.model.TrafficDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 * @author novo
 * @since 2023-02-21
 */
public interface TrafficService extends IService<TrafficDO> {

    void changeTraffic(CustomMessage customMessage);
}
