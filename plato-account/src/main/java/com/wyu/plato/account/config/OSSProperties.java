package com.wyu.plato.account.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author novo
 * @since 2023-02-27 18:38
 */
@ConfigurationProperties(prefix = "aliyun.oss")
@Component
@Data
public class OSSProperties {

    private String endPoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;
}
