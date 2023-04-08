package com.wyu.plato.visual.vo;

import lombok.Data;

/**
 * @author novo
 * @since 2023-04-08
 */
@Data
public class BrowserStats {
    private String browser;

    private Long pv;

    private Long uv;

    private Double pvRatio;

    private Double uvRatio;
}
