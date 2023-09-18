package com.linklyze.stream.dwm;

import com.linklyze.stream.constant.FlinkConstants;
import com.linklyze.stream.func.DeviceParseMapFunction;
import com.linklyze.stream.func.GaodeIPLocationFunction;
import com.linklyze.stream.util.FlinkUtil;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class DwmWideApp {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source = env.addSource(FlinkUtil.kafkaConsumer(FlinkConstants.DWD_TOPIC, SimpleStringSchema.class));
        env.enableCheckpointing(1000);
        env.setParallelism(1);

        SingleOutputStreamOperator<String> stream = source
                .map(new DeviceParseMapFunction()) // 设备详细信息解析
                .map(new GaodeIPLocationFunction()); // 地理位置解析
        stream.print();
        stream.addSink(FlinkUtil.kafkaProducer(FlinkConstants.DWM_TOPIC, SimpleStringSchema.class));

        env.execute();
    }
}
