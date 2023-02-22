package com.wyu.account.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author novo
 * @since 2023-02-22 17:12
 */
@ConfigurationProperties(prefix = "sms")
@Configuration
@Data
public class SmsConfig {
    private String appCode;

    private String templateId;
}
