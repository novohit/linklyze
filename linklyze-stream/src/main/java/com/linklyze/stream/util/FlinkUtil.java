package com.linklyze.stream.util;

import com.linklyze.stream.domain.DwsWideInfo;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.jdbc.JdbcStatementBuilder;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public static <T> FlinkKafkaConsumer<T> kafkaConsumer(String topic, Class<? extends DeserializationSchema<T>> deserializationSchema) throws Exception {
        List<String> inputTopics = Arrays.asList(topic);

        // 创建consumer对接kafka源
        FlinkKafkaConsumer<T> kafkaConsumer =
                new FlinkKafkaConsumer<>(inputTopics, deserializationSchema.newInstance(), properties);
        kafkaConsumer.assignTimestampsAndWatermarks(
                WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(2)));

        return kafkaConsumer;
    }

    public static <T> FlinkKafkaProducer<T> kafkaProducer(String topic, Class<? extends SerializationSchema<T>> serializationSchema) throws Exception {
        // 创建producer 输出到kafka
        FlinkKafkaProducer<T> kafkaProducer = new FlinkKafkaProducer<T>(
                topic,          // 目标 topic
                serializationSchema.newInstance(),    // 序列化 schema
                properties); // producer 配置
        return kafkaProducer;
    }

    public static SinkFunction<DwsWideInfo> jdbcSink(String sql) {
        return JdbcSink.sink(sql, new JdbcStatementBuilder<DwsWideInfo>() {
                    @Override
                    public void accept(PreparedStatement pstmt, DwsWideInfo obj) throws SQLException {
                        pstmt.setString(1, obj.getCode());
                        pstmt.setString(2, obj.getReferer());
                        pstmt.setString(3, obj.getIp());
                        pstmt.setString(4, obj.getCountry());
                        pstmt.setString(5, obj.getProvince());
                        pstmt.setString(6, obj.getCity());
                        pstmt.setString(7, obj.getIsp());
                        pstmt.setString(8, obj.getDeviceType());
                        pstmt.setString(9, obj.getOs());
                        pstmt.setString(10, obj.getBrowserType());
                        pstmt.setString(11, obj.getDeviceManufacturer());
                        pstmt.setLong(12, obj.getTimestamp());
                        pstmt.setLong(13, obj.getStart());
                        pstmt.setLong(14, obj.getEnd());
                        pstmt.setLong(15, obj.getUv());
                        pstmt.setLong(16, obj.getPv());
                        pstmt.setLong(17, obj.getAccountNo());
                    }
                },
                new JdbcExecutionOptions.Builder() // 控制批量写入大小
                        .withBatchSize(1) // 到达5条才一次性异步写入
                        .build(),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                        .withUrl(properties.getProperty("clickhouse.servers"))
                        .withDriverName("com.clickhouse.jdbc.ClickHouseDriver")
                        .withUsername("default")
                        .build());
    }

    public static void main(String[] args) throws IOException {
        ParameterTool tool = ParameterTool.fromPropertiesFile(args[0]);
        String groupId = tool.get("group.id", "test");
        String servers = tool.getRequired("bootstrap.servers");

        System.out.println(groupId);
        System.out.println(servers);
    }
}
