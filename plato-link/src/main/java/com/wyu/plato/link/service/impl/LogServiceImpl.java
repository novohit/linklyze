package com.wyu.plato.link.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wyu.plato.common.LocalUserThreadHolder;
import com.wyu.plato.common.enums.LogType;
import com.wyu.plato.common.model.bo.LocalUser;
import com.wyu.plato.common.model.bo.LogRecord;
import com.wyu.plato.common.util.CommonUtil;
import com.wyu.plato.link.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author novo
 * @since 2023-03-27
 */
@Service
@Slf4j
public class LogServiceImpl implements LogService {

    private static final String TOPIC_NAME = "ods_link_visit_topic";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void recordLog(HttpServletRequest request, String code) {
        // ip、浏览器信息
        String ip = CommonUtil.getIpAddr(request);
        Map<String, String> headers = CommonUtil.getAllRequestHeader(request);
        Map<String, String> map = new HashMap<>();
        if (headers.containsKey(HttpHeaders.USER_AGENT)) {
            map.put(HttpHeaders.USER_AGENT, headers.get(HttpHeaders.USER_AGENT));
        }
        if (headers.containsKey(HttpHeaders.REFERER)) {
            map.put(HttpHeaders.REFERER, headers.get(HttpHeaders.REFERER));
        }
        LogRecord logRecord = new LogRecord(null, code, ip, LogType.LINK.name(), null, map);
        String jsonLog = JSONObject.toJSONString(logRecord);
        log.info(jsonLog);
        // 发送kafka
        this.kafkaTemplate.send(TOPIC_NAME, jsonLog);
    }
}
