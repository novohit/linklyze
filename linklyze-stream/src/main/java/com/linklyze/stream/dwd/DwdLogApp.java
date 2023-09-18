package com.linklyze.stream.dwd;

import com.alibaba.fastjson2.JSON;
import com.linklyze.stream.constant.FlinkConstants;
import com.linklyze.stream.domain.Log;
import com.linklyze.stream.func.SetNuMapFunction;
import com.linklyze.stream.util.FlinkUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Objects;

/**
 * @author novo
 * @since 2023-03-27
 */
@Slf4j
public class DwdLogApp {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> source = env.addSource(FlinkUtil.kafkaConsumer(FlinkConstants.ODS_TOPIC, SimpleStringSchema.class));
        //DataStream<String> source = env.socketTextStream("localhost", 8888);
        env.enableCheckpointing(1000);
        env.setParallelism(1);

        SingleOutputStreamOperator<String> stream = source.map(new MapFunction<String, Log>() {
                    @Override
                    public Log map(String value) throws Exception {
                        // json ==> Obj
                        // try catch防止脏数据
                        try {
                            Log log = JSON.parseObject(value, Log.class);
                            return log;
                        } catch (Exception e) {
                            return null;
                        }
                    }
                })
                .filter(Objects::nonNull)
                .keyBy(Log::getUdid)
                .map(new SetNuMapFunction()); // 新老访客确认
        stream.print();
        stream.addSink(FlinkUtil.kafkaProducer(FlinkConstants.DWD_TOPIC, SimpleStringSchema.class));

        env.execute();
    }
}
