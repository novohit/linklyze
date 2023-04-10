package com.wyu.plato.stream.func;

import com.alibaba.fastjson2.JSON;
import com.wyu.plato.stream.domain.WideInfo;
import com.wyu.plato.stream.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.configuration.Configuration;

@Slf4j
public class UVFilterFunction extends RichFilterFunction<WideInfo> {

    private transient ValueState<String> dayState;

    @Override
    public void open(Configuration parameters) throws Exception {
        ValueStateDescriptor<String> descriptor =
                new ValueStateDescriptor<>("uv_visit_day", String.class); // default value of the state, if nothing was set
        // 设置状态过期时间
        StateTtlConfig config = StateTtlConfig.newBuilder(Time.days(1)).build();
        descriptor.enableTimeToLive(config);
        dayState = getRuntimeContext().getState(descriptor);
    }

    @Override
    public boolean filter(WideInfo value) throws Exception {
        String lastVisit = dayState.value();
        String currentVisit = TimeUtil.format(value.getTimestamp(), TimeUtil.YY_MM_DD_PATTERN);
        if (StringUtils.isNotBlank(lastVisit) && currentVisit.equals(lastVisit)) {
            // 在同一天访问过
            log.info("udid:[{}] 在[{}]时间已经访问过", value.getUdid(), lastVisit);
            return false;
        } else {
            log.info("udid:[{}] 在[{}]时间第一次访问", value.getUdid(), currentVisit);
            dayState.update(currentVisit);
            return true;
        }
    }
}
