package com.wyu.plato.stream.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DwsWideInfo {
    // ==========================

    /**
     * 业务ID
     */
    private String code;


    private String referer;


    private String ip;

    // 地理位置===================================
    private String country;

    private String province;

    private String city;

    private String isp;

    /**
     * 设备类型 Computer/Mobile
     */
    private String deviceType;

    /**
     * 操作系统 WINDOWS/Android/IOS
     */
    private String os;

    /**
     * 浏览器类型 Chrome
     */
    private String browserType;

    /**
     * 设备厂商
     */
    private String deviceManufacturer;

    // =========================
    private long timestamp;

    private long start;

    private long end;

    private long uv;

    private long pv;
}
