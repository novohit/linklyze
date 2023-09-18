package com.linklyze.stream.func;

import com.alibaba.fastjson2.JSON;
import com.linklyze.stream.domain.DeviceInfo;
import com.linklyze.stream.domain.Log;
import com.linklyze.stream.domain.WideInfo;
import com.linklyze.stream.mapper.DeviceInfoMapper;
import com.linklyze.stream.util.DeviceUtil;
import org.apache.flink.api.common.functions.MapFunction;

public class DeviceParseMapFunction implements MapFunction<String, WideInfo> {

    @Override
    public WideInfo map(String value) throws Exception {
        // 转成宽表
        Log log = JSON.parseObject(value, Log.class);

        DeviceInfo deviceInfo = DeviceUtil.getDeviceInfo(log.getUserAgent());
        WideInfo wideInfo = DeviceInfoMapper.INSTANCE.deviceToWide(deviceInfo);
        wideInfo.setBizId(log.getBizId());
        wideInfo.setAccountNo(log.getAccountNo());
        wideInfo.setDnu(log.getDnu());
        wideInfo.setReferer(log.getReferer());
        wideInfo.setTimestamp(log.getTimestamp());
        wideInfo.setUdid(log.getUdid());
        wideInfo.setIp(log.getIp());
        return wideInfo;
    }
}
