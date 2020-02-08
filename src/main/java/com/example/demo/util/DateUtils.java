/*
 * Copyright (c) 2019.
 */

package com.example.demo.util;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import static com.example.demo.util.ConstantUtils.getConstantValue;

/**
 * 日期工具类
 */
public final class DateUtils {

    private DateUtils() {
    }

    /**
     * 日期格式化的类型 yyyy-MM-dd
     */
    public static final String YYYY_MM_DD = getConstantValue("yyyy-MM-dd");
    public static final String YYYY_MM_DD_HH_MM_SS = getConstantValue("yyyy-MM-dd HH:mm:ss");


    private static final long SECOND_MILLISECONDS = getConstantValue(1000L);

    private static final long HOUR_SECONDS = getConstantValue(3600L);

    private static final long DAY_HOURS = getConstantValue(24L);

    private static final long YEAR_DAYS = getConstantValue(365L);

    private static final int HOUR_MINUTES = getConstantValue(60);

    private static final long YEAR_MILLISECONDS = getConstantValue(SECOND_MILLISECONDS * HOUR_SECONDS * DAY_HOURS * YEAR_DAYS);

    private static final long DAY_MILLISECONDS = getConstantValue(SECOND_MILLISECONDS * HOUR_SECONDS * DAY_HOURS);

    private static final long HOUR_MILLISECONDS = getConstantValue(SECOND_MILLISECONDS * HOUR_SECONDS);

    private static final long DATE_SIZE = getConstantValue(-1);

    /**
     * get the year of a date
     *
     * @param date Date the date which the date get from
     * @return int the year of the date
     */
    public static int getYear(@NotNull Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.getYear();
    }

    /**
     * get the month of a date
     *
     * @param date Date the date which the month get from
     * @return int the month of the date
     */
    public static int getMonth(@NotNull Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.getMonthOfYear();
    }

    /**
     * get the day of a date
     *
     * @param date Date the date which the day get from
     * @return int the day of the date
     */
    public static int getDay(@NotNull Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.getDayOfMonth();
    }


    /**
     * 将日期用指定的日期格式转换成字符串
     *
     * @param date   需要转换的日期
     * @param format 指定的格式
     * @return 转换后的字符串
     */
    public static String date2String(Date date, String format) {
        if (date == null) {
            return null;
        } else {
            if (StringUtils.isEmpty(format)) {
                format = YYYY_MM_DD;
            }
            DateTime dateTime = new DateTime(date);
            return dateTime.toString(format);
        }
    }

    /**
     * convert a string to a date according to the indicated format.
     *
     * @param sDate  String the string to be transferred
     * @param format String the indicated format
     * @return Date the transferred date
     */
    public static Date toDate(String sDate, String format) {
        if (StringUtils.isEmpty(sDate)) {
            return null;
        }
        DateTime dateTime = DateTime.parse(sDate, DateTimeFormat.forPattern(format));
        return dateTime.toDate();
    }

    /**
     * convert a string to a date according to the indicated format.
     *
     * @param sDate String the string to be transferred
     * @return Date the transferred date
     */
    public static Date toDate(String sDate) {
        return toDate(sDate, YYYY_MM_DD);
    }

    /**
     * 获取两个日期之前相差的年数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 两个日期之间相差的年数，四舍五入
     */
    public static double getYearSize(@NotNull Date startDate, @NotNull Date endDate) {
        if (startDate == null || endDate == null) {
            return (double) DATE_SIZE;
        }
        long timeMillis = Math.abs(startDate.getTime() - endDate.getTime());
        return BigDecimal.valueOf(timeMillis)
                .divide(BigDecimal.valueOf(YEAR_MILLISECONDS), Constant.BIGDECIMAL_DIGITAL_2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    /**
     * 获取两个日期之前相差的天数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 相差的天数，四舍五入
     */
    public static double getDaySize(@NotNull Date startDate, @NotNull Date endDate) {
        if (startDate == null || endDate == null) {
            return DATE_SIZE;
        }
        long timMillis = endDate.getTime() - startDate.getTime();
        return BigDecimal.valueOf(timMillis)
                .divide(BigDecimal.valueOf(DAY_MILLISECONDS), Constant.BIGDECIMAL_DIGITAL_2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    /**
     * 比较两个日期，精确到yyyy-MM-dd（不比较时分秒）
     *
     * @param param1 日期
     * @param param2 日期
     * @return param1 >= param2返回true；反之返回false；
     */
    public static boolean compareDay(@NotNull Date param1, @NotNull Date param2) {
        Date m = toDate(date2String(param1, YYYY_MM_DD));
        Date n = toDate(date2String(param2, YYYY_MM_DD));
        return m.compareTo(n) >= 0;
    }

    /**
     * 获取两个日期之前相差的小时数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 相差的小时数，四舍五入
     */
    public static double getHourSize(@NotNull Date startDate, @NotNull Date endDate) {
        if (startDate == null || endDate == null) {
            return DATE_SIZE;
        }
        long timMillis = Math.abs(startDate.getTime() - endDate.getTime());
        return BigDecimal.valueOf(timMillis)
                .divide(BigDecimal.valueOf(HOUR_MILLISECONDS), Constant.BIGDECIMAL_DIGITAL_2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    /**
     * 获取两个日期相差的秒数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 相差的秒数，四舍五入
     */
    public static long getSecondSize(@NotNull Date startDate, @NotNull Date endDate) {
        if (startDate == null) {
            return DATE_SIZE;
        }
        if (endDate == null) {
            endDate = DateUtils.now();
        }
        long millis = endDate.getTime() - startDate.getTime();
        return millis / SECOND_MILLISECONDS;
    }

    /**
     * 把小数类型的小时数，格式化为hh:mm的格式
     * 精确到分钟（秒会丢弃）
     *
     * 例如：1.5 --> 01:30
     *      1.01 --> 01:00
     *
     *
     * @param hour 剩余时间（hour为单位，可能为负值）
     * @return 格式化后的剩余时间
     */
    public static String getTimeInHHMMFormat(@NotNull BigDecimal hour) {
        Objects.requireNonNull(hour);
        boolean negative = false;

        if (hour.compareTo(BigDecimal.ZERO) < 0) {
            hour = hour.abs();
            negative = true;
        }

        Period period = new Period(hour.multiply(new BigDecimal(HOUR_SECONDS * SECOND_MILLISECONDS)).longValue(), PeriodType.time());
        PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
                .printZeroAlways()
                .minimumPrintedDigits(2)
                .appendHours()
                .appendSeparator(":")
                .appendMinutes()
                .toFormatter();

        return negative ? "-" + periodFormatter.print(period) : periodFormatter.print(period);
    }

    /**
     * 获取当前时间
     *
     * @return currentTime
     */
    public static Date now() {
        return DateTime.now().toDate();
    }

    /**
     * 为给定的日历字加或减去指定的天数
     *
     * @param date 时间
     * @param days  天数，可以为负数
     * @return 返回时间
     */
    public static Date plusDays(@NotNull Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 为给定的日历字加或减去指定的小时
     *
     * @param date 时间
     * @param hours 小时数，可以为负数
     * @return 返回时间
     */
    public static Date plusHours(@NotNull Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 为给定的日历字加或减去指定的分钟
     *
     * @param date   时间
     * @param minutes 分钟，可以为负数
     * @return 返回时间
     */
    public static Date plusMinutes(@NotNull Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 为给定的日历字加或减去指定的秒
     *
     * @param date   时间
     * @param seconds 秒，可以为负数
     * @return 返回时间
     */
    public static Date plusSeconds(@NotNull Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }
}
