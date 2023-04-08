package com.wyu.plato.visual.vo;

import lombok.Data;

/**
 * @author novo
 * @since 2023-04-08
 */
@Data
public class RegionStatsVO {

    private String country;

    private String province;

    private String city;

    private Long pv;

    private Long uv;
}
