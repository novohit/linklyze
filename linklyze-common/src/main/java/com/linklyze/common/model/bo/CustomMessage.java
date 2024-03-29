package com.linklyze.common.model.bo;

import com.linklyze.common.enums.MessageEventType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * MQ自定义消息对象 要实现序列化接口
 *
 * @author novo
 * @since 2023-03-22
 */
@Data
@Builder
public class CustomMessage implements Serializable {

    /**
     * 消息队列的消息id
     */
    private String messageId;


    /**
     * 操作账号
     */
    private Long accountNo;


    /**
     * 业务id 备用
     */
    private String bizId;

    /**
     * 事件类型 create、update、delete
     */
    private MessageEventType eventType;

    /**
     * 消息体
     */
    private String content;

    /**
     * 备注
     */
    private String remark;
}
