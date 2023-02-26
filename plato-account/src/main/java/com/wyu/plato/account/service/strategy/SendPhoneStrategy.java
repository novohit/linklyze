package com.wyu.plato.account.service.strategy;

import com.wyu.plato.account.component.SmsComponent;
import com.wyu.plato.account.component.SmsProperties;
import com.wyu.plato.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author novo
 * @since 2023-02-25 22:28
 */
@Component
public class SendPhoneStrategy implements VerifyStrategy {

    @Autowired
    private SmsComponent smsComponent;

    @Autowired
    private SmsProperties smsProperties;

    @Override
    public void send(String to) {
        String code = CommonUtil.getRandomCode(6);
        this.smsComponent.send(to, this.smsProperties.getTemplateId(), code);
    }
}
