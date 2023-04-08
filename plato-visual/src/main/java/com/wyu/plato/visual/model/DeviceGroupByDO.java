package com.wyu.plato.visual.model;

import lombok.Data;

/**
 * @author novo
 * @since 2023-04-08
 */
@Data
public class DeviceGroupByDO {

    private String deviceType;

    private String os;

    private String browserType;

    private Long pv;

    private Long uv;
}
