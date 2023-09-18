package com.wyu.plato.visual.vo;

import lombok.Data;

/**
 * @author novo
 * @since 2023-04-08
 */
@Data
public class BrowserStats {

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 浏览量
     */
    private Long pv;

    /**
     * 访客数
     */
    private Long uv;

    /**
     * 浏览量占比
     */
    private Double pvRatio;

    /**
     * 访客数占比
     */
    private Double uvRatio;
}
