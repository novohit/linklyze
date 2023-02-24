package com.wyu.account.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author novo
 * @since 2023-02-22 17:12
 */
@ConfigurationProperties(prefix = "sms")
@Data
public class SmsProperties {
    private String appCode;

    private String templateId;
}
