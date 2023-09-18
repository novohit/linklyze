package com.linklyze.account.service.strategy;

import com.wyu.plato.common.enums.SendCodeType;
import com.wyu.plato.common.util.SpringContextUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author novo
 * @since 2023-02-25 22:31
 */
public class MapSendStrategyFactory {
    /**
     *存储策略
     */
    static Map<SendCodeType, VerifyStrategy> sendCodeStrategyMap = new HashMap<>();

    // 注册默认策略
    static {
        // TODO 获取bean
        registerChargeStrategy(SendCodeType.USER_REGISTER_PHONE, SpringContextUtil.getBean(SendPhoneStrategy.class));
        registerChargeStrategy(SendCodeType.USER_LOGIN_PHONE, new SendEmailStrategy());
    }

    /**
     * 提供注册策略接口，外部只需要调用此接口接口新增策略
     */
    public static void registerChargeStrategy(SendCodeType type, VerifyStrategy strategy) {
        sendCodeStrategyMap.put(type, strategy);
    }

    /**
     * 通过map获取策略，当增加新的策略时无需修改代码
     */
    public static VerifyStrategy getChargeStrategy(SendCodeType type) {
        // 当增加新类型时，需要修改代码，同时增加圈复杂度
        if (sendCodeStrategyMap.containsKey(type)) {
            return sendCodeStrategyMap.get(type);
        } else {
            throw new RuntimeException("未配置相应策略");
        }
    }
}
