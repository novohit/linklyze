package com.wyu.plato.account.service.strategy;

/**
 * @author novo
 * @since 2023-02-25 22:27
 */
public interface VerifyStrategy {
    void send(String to, String type);
}
