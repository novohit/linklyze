package com.linklyze.link.strategy;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author novo
 * @since 2023-03-15
 */
@Slf4j
public class CustomTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    /**
     * @param availableTargetNames 数据源集合 在分库时值为所有分片库的集合 配置文件的databaseNames 分表时为对应分片库中所有分片表的集合 tablesNames
     * @param shardingValue        分片属性，包括 logicTableName逻辑表，columnName分片健（字段），value 为从 SQL 中解析出的分片健的值
     * @return
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        // 获取的是配置文件中actual-data-nodes:ds0.short_link的逻辑表名short_link
        // actual-data-nodes也可以配置成真实库.真实表:ds0.short_link_0,ds0.short_link_a,... 然后循环截取最后一位与短链码表位进行匹配
        // 只需要获取其中一个逻辑表名 因为配置的逻辑表名都相同
        String targetName = availableTargetNames.iterator().next();
        log.info("targetName:[{}]", targetName);
        // 短链码规则 首位:库位 末位:表位
        String code = shardingValue.getValue();
        String codeTableNo = String.valueOf(code.charAt(code.length() - 1));
        // 拼接Actual table short_link_a
        return targetName + "_" + codeTableNo;
        // TODO 可以返回默认数据源
        //throw new BizException(BizCodeEnum.UNKNOWN_DB_ROUTE);
    }
}
