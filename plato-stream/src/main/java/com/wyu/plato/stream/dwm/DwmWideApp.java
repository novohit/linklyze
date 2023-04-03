package com.wyu.plato.stream.dwm;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.util.BeanUtils;
import com.wyu.plato.stream.constant.FlinkConstants;
import com.wyu.plato.stream.domain.DeviceInfo;
import com.wyu.plato.stream.domain.LogRecord;
import com.wyu.plato.stream.domain.WideInfo;
import com.wyu.plato.stream.mapper.DeviceInfoMapper;
import com.wyu.plato.stream.util.DeviceUtil;
import com.wyu.plato.stream.util.FlinkUtil;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.util.BeanUtil;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class DwmWideApp {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source = env.addSource(FlinkUtil.kafkaConsumer(FlinkConstants.DWD_TOPIC, SimpleStringSchema.class));
        env.enableCheckpointing(1000);
        env.setParallelism(1);

        SingleOutputStreamOperator<WideInfo> stream = source.map(new MapFunction<String, WideInfo>() {
            @Override
            public WideInfo map(String value) throws Exception {
                // 转成宽表
                LogRecord logRecord = JSON.parseObject(value, LogRecord.class);

                DeviceInfo deviceInfo = DeviceUtil.getDeviceInfo(logRecord.getUserAgent());
                WideInfo wideInfo = DeviceInfoMapper.INSTANCE.deviceToWide(deviceInfo);
                wideInfo.setBizId(logRecord.getBizId());
                wideInfo.setAccountNo(logRecord.getAccountNo());
                wideInfo.setDnu(logRecord.getDnu());
                wideInfo.setReferer(logRecord.getReferer());
                wideInfo.setTimestamp(logRecord.getTimestamp());
                wideInfo.setUdid(logRecord.getUdid());
                wideInfo.setIp(logRecord.getIp());
                return wideInfo;
            }
        });
        stream.print();

        env.execute();
    }
}
