package com.wyu.plato.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author novo
 * @since 2023-03-10
 */
@Configuration
@Slf4j
public class SnowflakeWorkerIdConfig {

    /**
     * 动态指定sharding-jdbc的雪花算法中的word.id属性
     * 通过调用System.setProperty()修改属性值
     * wordId 10bit 1L<<10  [0, 1024)
     * {@link org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator#getWorkerId()}
     *
     */
    static {
        try {
            InetAddress host = Inet4Address.getLocalHost();
            String hostAddress = host.getHostAddress();
            // 对机器ip进行哈希取模 哈希可能为负 先取绝对值 workId极小概率会重复
            String workerId = String.valueOf(Math.abs((hostAddress.hashCode()) % 1024));
            System.setProperty("system.snowflake.worker-id", workerId);
            log.info("snowflake worker id:[{}]", workerId);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            InetAddress host = Inet4Address.getLocalHost();
            String hostName = host.getHostName();
            String hostAddress = host.getHostAddress();
            System.out.println(hostAddress); // 192.168.56.1
            System.out.println(hostName); // LAPTOP-JAIIUJU1
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
    }
}