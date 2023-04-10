package com.wyu.plato.visual.vo;

import lombok.Data;

/**
 * @author novo
 * @since 2023-04-08
 */
@Data
public class RegionStatsVO {

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 浏览量
     */
    private Long pv;

    /**
     * 访客数
     */
    private Long uv;
}
