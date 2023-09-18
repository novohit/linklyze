package com.wyu.plato.stream.util;

import com.wyu.plato.stream.domain.DeviceInfo;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

public class DeviceUtil {

    /**
     * 获取deviceInfo
     *
     * @param userAgent
     * @return
     */
    public static DeviceInfo getDeviceInfo(String userAgent) {
        UserAgent agent = UserAgent.parseUserAgentString(userAgent);
        OperatingSystem operatingSystem = agent.getOperatingSystem();
        Browser browser = agent.getBrowser();
        String deviceType = operatingSystem.getDeviceType().getName();
        String os = operatingSystem.getGroup().getName();
        String deviceManufacturer = operatingSystem.getManufacturer().getName();
        String browserType = browser.getGroup().getName();
        if (userAgent.contains("Edg")) {
            browserType = "Edge";
        }
        DeviceInfo deviceInfo = DeviceInfo.builder()
                .deviceType(deviceType)
                .os(os)
                .deviceManufacturer(deviceManufacturer)
                .browserType(browserType)
                .build();
        return deviceInfo;
    }
}
