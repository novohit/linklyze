package com.wyu.plato.stream.func;

import com.wyu.plato.stream.domain.LogRecord;
import com.wyu.plato.stream.util.TimeUtil;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;

public class SetNuMapFunction extends RichMapFunction<LogRecord, LogRecord> {

    private transient ValueState<String> dayState;

    @Override
    public void open(Configuration parameters) throws Exception {
        ValueStateDescriptor<String> descriptor =
                new ValueStateDescriptor<>("visit_day", String.class); // default value of the state, if nothing was set
        dayState = getRuntimeContext().getState(descriptor);
    }

    @Override
    public LogRecord map(LogRecord value) throws Exception {
        String lastVisit = dayState.value();
        String currentVisit = TimeUtil.format(value.getTimestamp());
        if (lastVisit == null) {
            value.setDnu(1);
            dayState.update(currentVisit);
        } else {
            if (!currentVisit.equals(lastVisit)) {
                value.setDnu(1);
                dayState.update(currentVisit);
            }
        }
        return value;
    }
}
