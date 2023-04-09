package com.wyu.plato.stream.dwd;

import com.alibaba.fastjson2.JSON;
import com.wyu.plato.stream.constant.FlinkConstants;
import com.wyu.plato.stream.domain.Log;
import com.wyu.plato.stream.func.SetNuMapFunction;
import com.wyu.plato.stream.util.FlinkUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
