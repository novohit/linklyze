package com.wyu.plato.link.strategy;

import com.wyu.plato.common.enums.BizCodeEnum;
import com.wyu.plato.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author novo
 * @since 2023-03-15
 */
@Slf4j
public class CustomDBPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    /**
     * @param availableTargetNames 数据源集合 在分库时值为所有分片库的集合 配置文件的databaseNames 分表时为对应分片库中所有分片表的集合 tablesNames
     * @param shardingValue        分片属性，包括 logicTableName逻辑表，columnName分片健（字段），value 为从 SQL 中解析出的分片健的值
     * @return
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        // 短链码规则 首位:库位 末位:表位
        String codeDBNo = String.valueOf(shardingValue.getValue().charAt(0));
        for (String targetName : availableTargetNames) {
            log.info("targetName:[{}]", targetName);
            // ds0,ds1,dsa
            // 获取databaseName最后一位 0,1,a
            String DBNo = String.valueOf(targetName.charAt(targetName.length() - 1));
            // 匹配一致
            if (codeDBNo.equalsIgnoreCase(DBNo)) {
                return targetName;
            }
        }
        // TODO 可以返回默认数据源
        throw new BizException(BizCodeEnum.UNKNOWN_DB_ROUTE);
    }
}
