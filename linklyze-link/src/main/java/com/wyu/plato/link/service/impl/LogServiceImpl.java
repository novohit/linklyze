package com.wyu.plato.link.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.linklyze.common.enums.LogType;
import com.linklyze.common.model.bo.LogRecord;
import com.linklyze.common.util.CommonUtil;
import com.wyu.plato.link.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    public void recordLog(HttpServletRequest request, String code, Long accountNo) {
        // ip、浏览器信息
        String ip = CommonUtil.getIpAddr(request);
        Map<String, String> headers = CommonUtil.getAllRequestHeader(request);
        LogRecord logRecord = LogRecord.builder()
                .ip(ip)
                .bizId(code)
                .accountNo(accountNo)
                .eventType(LogType.LINK.name())
                .timestamp(CommonUtil.getCurrentTimestamp())
                .build();
        if (headers.containsKey(HttpHeaders.USER_AGENT)) {
            logRecord.setUserAgent(headers.get(HttpHeaders.USER_AGENT));
        }
        if (headers.containsKey(HttpHeaders.REFERER)) {
            logRecord.setReferer(headers.get(HttpHeaders.REFERER));
        }

        // TODO mock
        Random random = new Random();
        List<String> refererList = Arrays.asList("https://www.baidu.com", "https://www.douyin.com", "https://www.google.com", "https://mp.weixin.qq.com/", "");
        logRecord.setReferer(refererList.get(random.nextInt(refererList.size())));
        List<String> ipList = Arrays.asList("192.168.56.1",
                "119.133.7.205",
                "14.29.106.1",
                "1.15.255.255",
                "118.121.204.216",
                "27.17.234.255",
                "36.149.28.255",
                "42.48.34.255",
                "27.115.83.255", // 上海
                "1.58.24.255", // 哈尔滨
                "14.17.95.255", // 东莞
                "43.247.227.255", // 无锡
                "27.150.159.255", // 厦门
                "27.212.14.255", // 山东
                "14.31.207.255", // 珠海
                "59.50.33.81");
        logRecord.setIp(ipList.get(random.nextInt(ipList.size())));
        //

        // X-Real-IP
        String md5 = CommonUtil.MD5(logRecord.getIp() + logRecord.getBizId() + logRecord.getUserAgent());
        logRecord.setUdid(md5);
        String jsonLog = JSONObject.toJSONString(logRecord);
        log.info(jsonLog);
        // 发送kafka
        this.kafkaTemplate.send(TOPIC_NAME, jsonLog);
    }
}
