package com.linklyze.account.service.strategy;

import java.util.Map;

@FunctionalInterface
public interface PayCallbackHandler {

    void handle(Map<String, String> paramMap);
}
