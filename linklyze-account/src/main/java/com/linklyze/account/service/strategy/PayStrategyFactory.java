package com.linklyze.account.service.strategy;

import com.linklyze.common.enums.PayType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PayStrategyFactory implements InitializingBean {

    private final Map<PayType, PayStrategy> payStrategyMap = new HashMap<>();

    private final ApplicationContext applicationContext;

    public PayStrategyFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public PayStrategy chooseStrategy(PayType payType) {
        return payStrategyMap.get(payType);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 从 IOC 容器中获取 PayStrategy 类型的 Bean 对象
        Map<String, PayStrategy> beans = applicationContext.getBeansOfType(PayStrategy.class);
        beans.forEach((beanName, strategy) -> payStrategyMap.put(strategy.mark(), strategy));
    }
}
