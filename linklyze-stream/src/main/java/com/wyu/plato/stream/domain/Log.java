package com.wyu.plato.stream.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author novo
 * @since 2023-03-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log {

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

    /**
     * 访问IP
     */
    private String ip;

    /**
     * 日志事件类型
     */
    private String eventType;

    /**
     * Unique Device Identifier :设备唯一标识符/浏览器指纹
     */
    private String udid;

    /**
     * 日志数据
     */
    private String userAgent;

    private String referer;

    /**
     * 日新老访客标识
     */
    private int dnu;
}
