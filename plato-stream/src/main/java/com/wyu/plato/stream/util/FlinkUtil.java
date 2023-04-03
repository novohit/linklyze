package com.wyu.plato.stream.util;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author novo
 * @since 2023-04-02
 */
public class FlinkUtil {
    private static final Logger logger = LoggerFactory.getLogger(FlinkUtil.class);

    public static StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    private static final Properties properties = new Properties();

    static {
        InputStream inputStream = FlinkUtil.class.getClassLoader().getResourceAsStream("flink.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("加载配置文件失败");
            throw new RuntimeException(e);
        }
        logger.info("加载文件成功");
    }

    public static <T> DataStream<T> kafkaSource(Class<? extends DeserializationSchema<T>> deserializationSchema) throws Exception {
        List<String> inputTopics = Arrays.asList(properties.getProperty("kafka.input.topics").split(","));
        env.enableCheckpointing(5000);
        env.setParallelism(1);

        // 创建consumer对接kafka源
        FlinkKafkaConsumer<T> myConsumer =
                new FlinkKafkaConsumer<>(inputTopics, deserializationSchema.newInstance(), properties);
        myConsumer.assignTimestampsAndWatermarks(
                WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(2)));

        DataStream<T> source = env.addSource(myConsumer);
        return source;
    }

    public static <T> FlinkKafkaProducer<T> kafkaSink(Class<? extends SerializationSchema<T>> serializationSchema) throws Exception {
        // 创建producer 输出到kafka
        FlinkKafkaProducer<T> kafkaProducer = new FlinkKafkaProducer<T>(
                properties.getProperty("kafka.output.topics"),          // 目标 topic
                serializationSchema.newInstance(),    // 序列化 schema
                properties); // producer 配置
        return kafkaProducer;
    }

    public static void main(String[] args) throws IOException {
        ParameterTool tool = ParameterTool.fromPropertiesFile(args[0]);
        String groupId = tool.get("group.id", "test");
        String servers = tool.getRequired("bootstrap.servers");

        System.out.println(groupId);
        System.out.println(servers);
    }
}
