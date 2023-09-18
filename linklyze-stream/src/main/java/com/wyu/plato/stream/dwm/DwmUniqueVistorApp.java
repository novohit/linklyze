package com.wyu.plato.stream.dwm;

import com.wyu.plato.stream.constant.FlinkConstants;
import com.wyu.plato.stream.domain.WideInfo;
import com.wyu.plato.stream.func.CustomWideInfoSchema;
import com.wyu.plato.stream.func.UVFilterFunction;
import com.wyu.plato.stream.util.FlinkUtil;
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
