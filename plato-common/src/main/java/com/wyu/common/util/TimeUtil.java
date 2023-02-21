package com.wyu.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author novo
 * @date 2023-02-21 14:12
 */
public class TimeUtil {

    /**
     * 默认日期格式
     */
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认日期格式
     */
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER  = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);

    /**
     * 默认时区
     */
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();


    /**
     * LocalDateTime 转 字符串，指定日期格式
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String format(LocalDateTime localDateTime, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String timeStr = formatter.format(localDateTime.atZone(DEFAULT_ZONE_ID));
        return timeStr;
    }


    /**
     * Date 转 字符串, 指定日期格式
     * @param time
     * @param pattern
     * @return
     */
    public static String format(Date time, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(time.toInstant().atZone(DEFAULT_ZONE_ID));
    }

    /**
     *  Date 转 字符串，默认日期格式
     * @param time
     * @return
     */
    public static String format(Date time){
        return DEFAULT_DATE_TIME_FORMATTER.format(time.toInstant().atZone(DEFAULT_ZONE_ID));
    }

    /**
     * timestamp 转 字符串，默认日期格式
     *
     * @param timestamp
     * @return
     */
    public static String format(long timestamp) {
        return DEFAULT_DATE_TIME_FORMATTER.format(new Date(timestamp).toInstant().atZone(DEFAULT_ZONE_ID));
    }


    /**
     * 字符串 转 Date
     *
     * @param time
     * @return
     */
    public static Date strToDate(String time) {
        LocalDateTime localDateTime = LocalDateTime.parse(time, DEFAULT_DATE_TIME_FORMATTER);
        return Date.from(localDateTime.atZone(DEFAULT_ZONE_ID).toInstant());
    }


    /**
     * 获取当天剩余的秒数,用于流量包过期配置
     * @param currentDate
     * @return
     */
    public static Integer getRemainSecondsOneDay(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                        ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);

        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }
}
