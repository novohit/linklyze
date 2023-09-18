package com.linklyze.stream.dwm;

import com.linklyze.stream.constant.FlinkConstants;
import com.linklyze.stream.func.CustomWideInfoSchema;
import com.linklyze.stream.domain.WideInfo;
import com.linklyze.stream.func.UVFilterFunction;
import com.linklyze.stream.util.FlinkUtil;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class DwmUniqueVistorApp {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<WideInfo> source = env.addSource(FlinkUtil.kafkaConsumer(FlinkConstants.DWM_TOPIC, CustomWideInfoSchema.class));
        env.enableCheckpointing(1000);
        env.setParallelism(1);

        SingleOutputStreamOperator<WideInfo> stream = source
                .keyBy(WideInfo::getUdid)
                .filter(new UVFilterFunction());
        stream.print();
        stream.addSink(FlinkUtil.kafkaProducer(FlinkConstants.DWM_UV_TOPIC, CustomWideInfoSchema.class));

        env.execute();
    }
}
