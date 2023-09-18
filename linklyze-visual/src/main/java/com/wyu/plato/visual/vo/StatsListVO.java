package com.wyu.plato.visual.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author novo
 * @since 2023-04-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsListVO {

    /**
     * 浏览器统计
     */
    private List<BrowserStats> browserStats;

    /**
     * 操作系统统计
     */
    private List<OsStats> osStats;

    /**
     * 设备类型统计
     */
    private List<DeviceStats> deviceStats;


    /**
     * 总浏览量
     */
    private Long pvSum;

    /**
     * 总访客数
     */
    private Long uvSum;
}
