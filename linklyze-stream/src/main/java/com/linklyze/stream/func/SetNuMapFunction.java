package com.linklyze.stream.func;

import com.alibaba.fastjson2.JSON;
import com.linklyze.stream.domain.Log;
import com.linklyze.stream.util.TimeUtil;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;

public class SetNuMapFunction extends RichMapFunction<Log, String> {

    private transient ValueState<String> dayState;

    @Override
    public void open(Configuration parameters) throws Exception {
        ValueStateDescriptor<String> descriptor =
                new ValueStateDescriptor<>("visit_day", String.class); // default value of the state, if nothing was set
        dayState = getRuntimeContext().getState(descriptor);
    }

    @Override
    public String map(Log value) throws Exception {
        String lastVisit = dayState.value();
        String currentVisit = TimeUtil.format(value.getTimestamp(), TimeUtil.YY_MM_DD_PATTERN);
        if (lastVisit == null) {
            value.setDnu(1);
            dayState.update(currentVisit);
        } else {
            if (!currentVisit.equals(lastVisit)) {
                value.setDnu(1);
                dayState.update(currentVisit);
            }
        }
        try {
            return JSON.toJSONString(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
