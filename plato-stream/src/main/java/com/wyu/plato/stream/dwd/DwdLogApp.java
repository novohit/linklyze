package com.wyu.plato.stream.dwd;

import com.alibaba.fastjson2.JSON;
import com.wyu.plato.stream.domain.LogRecord;
import com.wyu.plato.stream.func.SetNuMapFunction;
import com.wyu.plato.stream.util.FlinkUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Objects;

/**
 * @author novo
 * @since 2023-03-27
 */
@Slf4j
public class DwdLogApp {
    public static void main(String[] args) throws Exception {
        DataStream<String> source = FlinkUtil.kafkaSource(SimpleStringSchema.class);
        source.map(new MapFunction<String, LogRecord>() {
                    @Override
                    public LogRecord map(String value) throws Exception {
                        // json ==> Obj
                        // try catch防止脏数据
                        try {
                            LogRecord logRecord = JSON.parseObject(value, LogRecord.class);
                            return logRecord;
                        } catch (Exception e) {
                            return null;
                        }
                    }
                })
                .filter(Objects::nonNull)
                .keyBy(LogRecord::getUdid)
                .map(new SetNuMapFunction()) // 新老访客确认
                .print();

        FlinkUtil.env.execute();
    }
}
