package com.wyu.plato.account.service.strategy;

import com.wyu.plato.common.enums.SendCodeType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author novo
 * @since 2023-02-25 22:31
 */
public class MapCodeStrategyFactory {
    /**
     *存储策略
     */
    static Map<SendCodeType, SendCodeStrategy> sendCodeStrategyMap = new HashMap<>();

    // 注册默认策略
    static {
        registerChargeStrategy(SendCodeType.USER_REGISTER_PHONE, new SendPhoneStrategy());
        registerChargeStrategy(SendCodeType.USER_LOGIN_PHONE, new SendPhoneStrategy());
    }

    /**
     * 提供注册策略接口，外部只需要调用此接口接口新增策略
     */
    public static void registerChargeStrategy(SendCodeType type, SendCodeStrategy strategy) {
        sendCodeStrategyMap.put(type, strategy);
    }

    /**
     * 通过map获取策略，当增加新的策略时无需修改代码
     */
    public static SendCodeStrategy getChargeStrategy(SendCodeType type) throws Exception {
        // 当增加新类型时，需要修改代码，同时增加圈复杂度
        if (sendCodeStrategyMap.containsKey(type)) {
            return sendCodeStrategyMap.get(type);
        } else {
            throw new Exception("未配置相应策略");
        }
    }
}
