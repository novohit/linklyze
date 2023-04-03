package com.wyu.plato.stream.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WideInfo {
    // ==========================
    /**
     * 账户ID
     */
    private Long accountNo;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 产生时间戳
     */
    private Long timestamp;


    private String referer;


    /**
     * 日新老访客标识
     */
    private int dnu;

    private String ip;


    // 设备相关================================
    private String udid;

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
}
