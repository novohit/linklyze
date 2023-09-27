package com.linklyze.account.service.strategy;

import com.linklyze.common.enums.PayType;

public interface PayStrategy {

    /**
     * 执行策略标识
     */
    default PayType mark() {
        return null;
    }

    PayResponse pay(PayRequest payRequest);

    PayResponse cancel(PayRequest payRequest);

    PayResponse refund(PayRequest payRequest);

}
