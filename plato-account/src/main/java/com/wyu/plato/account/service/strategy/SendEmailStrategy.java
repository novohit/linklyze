package com.wyu.plato.account.service.strategy;

import com.wyu.plato.account.component.SmsComponent;
import com.wyu.plato.account.component.SmsProperties;
import com.wyu.plato.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author novo
 * @since 2023-02-26 11:35
 */
public class SendEmailStrategy implements VerifyStrategy{

    @Override
    public void send(String to) {
        // TODO
    }
}
