package com.wyu.plato.stream.dwd;

import com.wyu.plato.stream.util.FlinkUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author novo
 * @since 2023-03-27
 */
@Slf4j
public class DwdLogApp {
    public static void main(String[] args) throws Exception {
        DataStream<String> source = FlinkUtil.kafkaSource(SimpleStringSchema.class);
        //env.setParallelism(1);
        //DataStreamSource<String> dataStreamSource = env.socketTextStream("127.0.0.1", 8888);
        source.print();
        FlinkUtil.env.execute();
    }
}
