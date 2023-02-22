package com.wyu.account.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author novo
 * @since 2023-02-22 21:50
 */
@Component
@Slf4j
public class SmsComponent {
    private static final String URL_PATTERN = "https://jmsms.market.alicloudapi.com/sms/send?mobile=%s&templateId=%s&value=%s";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SmsConfig smsConfig;

    public void send(String to, String templateId, String value) {
        String url = String.format(URL_PATTERN, to, templateId, value);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "APPCODE " + smsConfig.getAppCode());
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        log.info("url:[{}],body:[{}]", url, response.getBody());
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("发送短信成功");
        } else {
            log.info("发送短信失败:[{}]", response.getBody());
        }
    }
}
