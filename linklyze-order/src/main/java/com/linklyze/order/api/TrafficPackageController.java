package com.linklyze.order.api;


import com.linklyze.order.model.TrafficPackageDO;
import com.linklyze.order.service.TrafficPackageService;
import com.linklyze.common.model.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author novo
 * @since 2023-09-18
 */
@RestController
@RequestMapping("/traffic-package/v1")
@Slf4j
public class TrafficPackageController {

    private final TrafficPackageService trafficPackageService;

    public TrafficPackageController(TrafficPackageService trafficPackageService) {
        this.trafficPackageService = trafficPackageService;
    }

    /**
     * 流量包列表
     *
     * @return
     */
    @GetMapping("/list")
    public Response<List<TrafficPackageDO>> list() {
        List<TrafficPackageDO> list = this.trafficPackageService.list();
        return Response.success(list);
    }

    /**
     * 流量包详情
     *
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public Response<TrafficPackageDO> detail(@PathVariable("id") Long id) {
        TrafficPackageDO detail = this.trafficPackageService.getById(id);
        return Response.success(detail);
    }
}
