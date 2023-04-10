package com.wyu.plato.stream.dws;

import com.wyu.plato.stream.constant.FlinkConstants;
import com.wyu.plato.stream.domain.DwsWideInfo;
import com.wyu.plato.stream.domain.WideInfo;
import com.wyu.plato.stream.func.CustomWideInfoSchema;
import com.wyu.plato.stream.mapper.DwsWideMapper;
import com.wyu.plato.stream.util.FlinkUtil;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple8;
import org.apache.flink.api.java.tuple.Tuple9;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.List;

public class DwsApp {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<WideInfo> source = env.addSource(FlinkUtil.kafkaConsumer(FlinkConstants.DWM_TOPIC, CustomWideInfoSchema.class));
        DataStreamSource<WideInfo> uvSource = env.addSource(FlinkUtil.kafkaConsumer(FlinkConstants.DWM_UV_TOPIC, CustomWideInfoSchema.class));
        env.enableCheckpointing(1000);
        env.setParallelism(1);
        SingleOutputStreamOperator<WideInfo> stream = source.map((MapFunction<WideInfo, WideInfo>) value -> {
            value.setPv(1);
            value.setUv(0);
            return value;
        });

        SingleOutputStreamOperator<WideInfo> uvStream = uvSource.map((MapFunction<WideInfo, WideInfo>) value -> {
            value.setPv(0);
            value.setUv(1);
            return value;
        });

        // 设置watermark策略 并指定eventime
        WatermarkStrategy<WideInfo> watermarkStrategy = WatermarkStrategy.<WideInfo>forBoundedOutOfOrderness(Duration.ofSeconds(0)) // 允许延迟10s
                .withTimestampAssigner((event, timestamp) -> event.getTimestamp());

        SingleOutputStreamOperator<DwsWideInfo> resStream = stream.union(uvStream)
                .assignTimestampsAndWatermarks(watermarkStrategy)
                // 多维度 多字段分组
                // code, referer,
                // province, city, ip
                // browser, os, deviceType
                .keyBy(new KeySelector<WideInfo, Tuple8<String, String, String, String, String, String, String, String>>() {
                    @Override
                    public Tuple8<String, String, String, String, String, String, String, String> getKey(WideInfo value) throws Exception {
                        return Tuple8.of(value.getBizId(), value.getReferer(),
                                value.getProvince(), value.getCity(), value.getIp(),
                                value.getBrowserType(), value.getOs(), value.getDeviceType());
                    }
                })
                .window(TumblingEventTimeWindows.of(Time.seconds(30))).reduce(new ReduceFunction<WideInfo>() {
                    @Override
                    public WideInfo reduce(WideInfo value1, WideInfo value2) throws Exception {
                        value2.setPv(value1.getPv() + value2.getPv());
                        value2.setUv(value1.getUv() + value2.getUv());
                        return value2;
                    }
                }, new ProcessWindowFunction<WideInfo, DwsWideInfo, Tuple8<String, String, String, String, String, String, String, String>, TimeWindow>() {
                    @Override
                    public void process(Tuple8<String, String, String, String, String, String, String, String> tuple9, ProcessWindowFunction<WideInfo, DwsWideInfo, Tuple8<String, String, String, String, String, String, String, String>, TimeWindow>.Context context, Iterable<WideInfo> elements, Collector<DwsWideInfo> out) throws Exception {
                        for (WideInfo wideInfo : elements) {
                            DwsWideInfo dswWide = DwsWideMapper.INSTANCE.wideToDws(wideInfo);

                            dswWide.setCode(wideInfo.getBizId());
                            dswWide.setStart(context.window().getStart());
                            dswWide.setEnd(context.window().getEnd());
                            out.collect(dswWide);
                        }
                    }
                });
        resStream.print();
        String sql = "insert into access_stats (id,code,referer,ip,country,province,city,isp,device_type,os,browser_type,device_manufacturer,timestamp,start,end,uv,pv,account_no) values(generateUUIDv4(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        resStream.addSink(FlinkUtil.jdbcSink(sql));
        //resStream.print();


        env.execute();
    }
}
