package com.wyu.plato.common.model.bo;

import com.wyu.plato.common.enums.LogType;
import com.wyu.plato.common.util.CommonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author novo
 * @since 2023-03-27
 */
@Data
@NoArgsConstructor
public class LogRecord {

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
     * Unique Device Identifier :设备唯一标识符
     */
    private String udid;

    /**
     * 日志数据
     */
    private Object data;

    public LogRecord(Long accountNo, String bizId, String ip, String eventType, String udid, Object data) {
        this.accountNo = accountNo;
        this.bizId = bizId;
        this.ip = ip;
        this.eventType = eventType;
        this.udid = udid;
        this.data = data;
        this.timestamp = CommonUtil.getCurrentTimestamp();
    }
}
