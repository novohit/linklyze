package com.wyu.plato.common.util.uuid;

import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;

/**
 * ID生成器工具类
 *
 * @author novo
 * @since 2023-02-25 16:22
 */
public class IDUtil {

    private static final SnowflakeShardingKeyGenerator shardingKeyGenerator = new SnowflakeShardingKeyGenerator();

    /**
     * 生成雪花ID
     *
     * @return
     */
    public static Long snowflakeID() {
        return Long.valueOf(shardingKeyGenerator.generateKey().toString());
    }

    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUUID() {
        return UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID() {
        return UUID.fastUUID().toString(true);
    }
}
