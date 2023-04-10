package com.wyu.plato.visual.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "access_stats")
public class DwsWideInfo {

    private String id;
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
    @JsonIgnore
    private long timestamp;

    private long start;

    private long end;

    private long uv;

    private long pv;
}
