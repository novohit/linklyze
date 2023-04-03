package com.wyu.plato.stream;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class UserAgentTest {

    @Test
    public void testUserAgent() {
        //String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1";
        //String userAgent = "Mozilla/5.0 (Linux; Android 8.0.0; SM-G955U Build/R16NW) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Mobile Safari/537.36";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36";
        UserAgent agent = UserAgent.parseUserAgentString(userAgent);
        Browser browser = agent.getBrowser();
        String browserManu = browser.getManufacturer().getName();
        String browserType = browser.getBrowserType().getName();
        String browserGroup = browser.getGroup().getName();
        short browserId = browser.getId();
        String name = browser.getRenderingEngine().getName();
        String version = browser.getVersion(userAgent).getVersion();
        log.info(browser.getName()); // Chrome Mobile/Chrome 11
        log.info(browserManu); // Google Inc.
        log.info(browserType); // Browser (mobile)
        log.info(browserGroup); // Chrome
        log.info(String.valueOf(browserId));
        log.info(name);
        log.info(version);
        String manufacturer = agent.getOperatingSystem().getManufacturer().getName();
        String deviceType = agent.getOperatingSystem().getDeviceType().getName();
        String deviceGroup = agent.getOperatingSystem().getGroup().getName();
        String name1 = agent.getOperatingSystem().getName();
        log.info(manufacturer); // Microsoft Corporation
        log.info(deviceType); // Computer/Mobile
        log.info(deviceGroup); // WINDOWS/Android/IOS
        log.info(name1); // WINDOWS 10/Mac OS X (iPhone)
    }
}
