package com.linklyze.order.service.strategy;

import java.util.Map;

@FunctionalInterface
public interface PayCallbackHandler {

    PayCallBackResponse handle(Map<String, String> paramMap);
}
