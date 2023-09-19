package com.linklyze.common.config;

import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 4.1.x sharding-jdbc 不支持 LocalDateTime
 * TODO upgrade sharding-jdbc https://github.com/apache/shardingsphere/pull/6202
 * @author novo
 * @since 2023-09-19
 */
@Component
public class LocalDateTimeTypeHandler extends org.apache.ibatis.type.LocalDateTimeTypeHandler {
    public LocalDateTimeTypeHandler() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, new Timestamp(this.toTimeMillis(parameter)));
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp sqlTimestamp = rs.getTimestamp(columnName);
        return sqlTimestamp != null ? this.toLocalDateTime(sqlTimestamp.getTime()) : null;
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp sqlTimestamp = rs.getTimestamp(columnIndex);
        return sqlTimestamp != null ? this.toLocalDateTime(sqlTimestamp.getTime()) : null;
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
        return sqlTimestamp != null ? this.toLocalDateTime(sqlTimestamp.getTime()) : null;
    }

    private long toTimeMillis(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private LocalDateTime toLocalDateTime(long timeMillis) {
        return new Date(timeMillis).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
