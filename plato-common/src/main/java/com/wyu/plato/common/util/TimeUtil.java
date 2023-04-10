package com.wyu.plato.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author novo
 * @date 2023-02-21 14:12
 */
public class TimeUtil {

    /**
     * 默认日期格式
     */
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String YY_MM_DD_PATTERN = "yyyy-MM-dd";

    public static final String YYMMDD_PATTERN = "yyyyMMdd";

    /**
     * 默认日期格式
     */
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);

    /**
     * 默认时区
     */
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();


    /**
     * LocalDateTime 转 字符串，指定日期格式
     *
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String timeStr = formatter.format(localDateTime.atZone(DEFAULT_ZONE_ID));
        return timeStr;
    }


    /**
     * Date 转 字符串, 指定日期格式
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String format(Date time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(time.toInstant().atZone(DEFAULT_ZONE_ID));
    }

    /**
     * Date 转 字符串，默认日期格式
     *
     * @param time
     * @return
     */
    public static String format(Date time) {
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
     * timestamp 转 字符串，默认日期格式
     *
     * @param timestamp
     * @return
     */
    public static String format(long timestamp, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(new Date(timestamp).toInstant().atZone(DEFAULT_ZONE_ID));
    }


    /**
     * 字符串 转 Date
     *
     * @param time
     * @return
     */
    public static Date strToDate(String time) {
        return strToDate(time, null);
    }

    /**
     * 字符串 转 Date
     *
     * @param time
     * @return
     */
    public static Date strToDate(String time, String pattern) {
        LocalDateTime localDateTime;
        if (pattern == null) {
            localDateTime = LocalDateTime.parse(time, DEFAULT_DATE_TIME_FORMATTER);
        } else if (pattern.equals(YYMMDD_PATTERN)) {
            // yyyyMMdd只有date没有time 所以要用LocalDate转换
            localDateTime = LocalDate.parse(time, DateTimeFormatter.ofPattern(pattern)).atStartOfDay();
        } else {
            localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern));

        }
        return Date.from(localDateTime.atZone(DEFAULT_ZONE_ID).toInstant());
    }

    /**
     * 字符串日期转换另一种格式
     * example: 20230314->2023-03-14
     *
     * @param time
     * @param oldPattern
     * @param newPattern
     * @return
     */
    public static String format(String time, String oldPattern, String newPattern) {
        Date date = strToDate(time, oldPattern);
        return format(date, newPattern);
    }


    /**
     * 获取当天剩余的秒数,用于流量包过期配置
     *
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

    /**
     * 获取日历 两个时间内的所有日期集合
     *
     * @param startTime
     * @param endTime
     * @param pattern
     * @return
     */
    public static List<String> getCalendar(String startTime, String endTime, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = format.parse(startTime);
            Date endDate = format.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime() <= endDate.getTime()) {
                // 把日期添加到集合
                list.add(format.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.DATE, 1);
                // 获取增加后的日期
                startDate = calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }
}
