package com.wyu.plato.account;

import com.wyu.plato.account.component.SmsComponent;
import com.wyu.plato.account.config.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author novo
 * @since 2023-02-22 22:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SmsTest {

    @Autowired
    private SmsComponent smsComponent;

    @Autowired
    private SmsProperties smsProperties;

    @Test
    public void testSendSms() throws InterruptedException {
        String phone = "13427417680";
        String templateId = smsProperties.getTemplateId();
        String code = "1234";
        smsComponent.send(phone, templateId, code);
    }
}
