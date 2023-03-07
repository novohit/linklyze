package com.wyu.plato.account.service.strategy;

import com.wyu.plato.account.component.SmsComponent;
import com.wyu.plato.account.config.SmsProperties;
import com.wyu.plato.common.constant.CacheConstants;
import com.wyu.plato.common.enums.BizCodeEnum;
import com.wyu.plato.common.exception.BizException;
import com.wyu.plato.common.util.CommonUtil;
import com.wyu.plato.common.util.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author novo
 * @since 2023-02-25 22:28
 */
@Component
@Slf4j
public class SendPhoneStrategy implements VerifyStrategy {

    @Autowired
    private SmsComponent smsComponent;

    @Autowired
    private SmsProperties smsProperties;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void send(String to, String type) {
        // key account-service:code:register:to
        String codeKey = String.format(CacheConstants.CHECK_CODE_KEY, type, to);
        // value code_timestamp
        String codeCache = this.redisCache.getCacheObject(codeKey);

        if (StringUtils.hasText(codeCache)) {
            // 验证码防刷 判断是否在60s内重复发送
            long createTime = Long.parseLong(codeCache.split("_")[1]);
            long ttl = CommonUtil.getCurrentTimestamp() - createTime;
            if (ttl < CacheConstants.CHECK_CODE_REPEAT) {
                throw new BizException(BizCodeEnum.CODE_LIMITED);
            }
        }

        String code = CommonUtil.getRandomCode(6);
        String value = code + "_" + CommonUtil.getCurrentTimestamp();
        // 存入redis并发送验证码
        this.redisCache.setCacheObject(codeKey, value, CacheConstants.CHECK_CODE_EXPIRATION, TimeUnit.MINUTES);
        log.info("set redis [{}]", value);
        this.smsComponent.send(to, this.smsProperties.getTemplateId(), code);
    }
}
