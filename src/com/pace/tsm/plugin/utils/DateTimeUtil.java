
package com.pace.tsm.plugin.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtil {
    public static final long DayLong = 86400000;
    private static String TAG = "DateTimeUtil";

    public static Timestamp currentTimestamp() {
        return Timestamp
                .valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    public static Time currentSqlTime() {
        return Time.valueOf(new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }

    public static java.sql.Date currentSqlDate() {
        return java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public static Timestamp parseDateString(String dateString, String pattern) {
        if (pattern == null || "".equals(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        try {
            return new Timestamp(new SimpleDateFormat(pattern).parse(dateString).getTime());
        } catch (ParseException e) {
            LogUtil.loge(TAG, e.getMessage());
            return null;
        }
    }

    public static Timestamp minValue() {
        return Timestamp.valueOf("2011-01-01 00:00:00");
    }

    public static String currentDateTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String currentDateString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String currentDateString(String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd";
        }
        return new SimpleDateFormat(pattern).format(new Date());
    }

    public static String currentTimeString() {
        return new SimpleDateFormat("hh:mm:ss").format(new Date());
    }

    public static String format(Timestamp timestamp) {
        if (timestamp != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        }
        return "";
    }

    public static String format(Date date, String pattern) {
        if (ValueUtil.isEmpty(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String formatYMD(Timestamp timestamp) {
        if (timestamp != null) {
            return new SimpleDateFormat("yyyy-MM-dd").format(timestamp);
        }
        return "";
    }

    public static String format(Timestamp timestamp, String pattern) {
        if (timestamp != null) {
            return new SimpleDateFormat(pattern).format(timestamp);
        }
        return "";
    }

    public static String formatSqlDate(java.sql.Date date) {
        if (date != null) {
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        }
        return "";
    }

    public static String formatSqlTime(Time time) {
        if (time != null) {
            return new SimpleDateFormat("HH:mm").format(time);
        }
        return "";
    }

    public static String formatSqlTime(Time time, String pattern) {
        if (ValueUtil.isEmpty(pattern)) {
            pattern = "HH:mm:ss";
        }
        if (time != null) {
            return new SimpleDateFormat(pattern).format(time);
        }
        return "";
    }

    public static Timestamp parseLong(String longString) {
        return new Timestamp(Long.valueOf(longString).longValue());
    }

    public static Timestamp parseTimestamp(String timestamp) {
        return Timestamp.valueOf(timestamp);
    }

    public static Timestamp addDateTimeToDate(Date date, int value, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(field, value);
        return Timestamp.valueOf(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
    }

    public static java.sql.Date addDateTimeToDate(java.sql.Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time.getTime()));
        calendar.add(5, 8);
        return new java.sql.Date(calendar.getTime().getTime());
    }

    public static String addDateTimeToDate(String date, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate(date, "yyyy-MM-dd"));
        calendar.add(5, value);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    public static Date parseDate(String value) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
        } catch (ParseException e) {
            LogUtil.loge(TAG, e.getMessage());
            return null;
        }
    }

    public static java.sql.Date parseSqlDate(String value) {
        return java.sql.Date.valueOf(value);
    }

    public static Time parseSqlTime(String value) {
        return Time.valueOf(value);
    }

    public static Date parseDate(String value, String partten) {
        if (ValueUtil.isEmpty(partten)) {
            partten = "yyyy-MM-dd";
        }
        try {
            return new SimpleDateFormat(partten).parse(value);
        } catch (ParseException e) {
            LogUtil.loge(TAG, e.getMessage());
            return null;
        }
    }

    public static int getCurrentField(int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(field) + 1;
    }

    public static int secondsBetween(Date fromDate, Date toDate) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(toDate);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(fromDate);
        return (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / 1000);
    }

    public static int compareCurrentTime(Timestamp timestamp) {
        return 7 - ((int) ((System.currentTimeMillis() - timestamp.getTime()) / DayLong));
    }

    public static int compareDate(String date1, String date2, String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd";
        }
        DateFormat format = new SimpleDateFormat(pattern);
        try {
            long d1 = format.parse(date1).getTime();
            long d2 = format.parse(date2).getTime();
            if (d1 - d2 > 0) {
                return 1;
            }
            if (d1 - d2 < 0) {
                return -1;
            }
            return 0;
        } catch (ParseException e) {
            LogUtil.loge(TAG, e.getMessage());
        }
        return 0;
    }

    public static int compareTime(java.sql.Date actDate, Time startTime, Time endTime) {
        String str = formatSqlDate(actDate);
        String start = formatSqlTime(startTime);
        String end = formatSqlTime(endTime);
        String curr = currentDateString("yyyy-MM-dd HH:mm");
        String sd = new StringBuilder(String.valueOf(str)).append(" ").append(start).toString();
        String ed = new StringBuilder(String.valueOf(str)).append(" ").append(end).toString();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            long lcd = format.parse(curr).getTime();
            long lsd = format.parse(sd).getTime();
            long led = format.parse(ed).getTime();
            if (lcd - lsd < 0) {
                return 0;
            }
            if (lcd - led > 0) {
                return 2;
            }
            if (lcd - lsd >= 0 && lcd - led <= 0) {
                return 1;
            }
            return 0;
        } catch (ParseException e) {
            LogUtil.loge(TAG, e.getMessage());
        }
        return 0;
    }

    public static String getMonthString(int month) {
        if (month > 12) {
            month %= 12;
        }
        switch (month) {
            case 0:
                return "十二月";
            case 1:
                return "一月";
            case 2:
                return "二月";
            case 3:
                return "三月";
            case 4:
                return "四月";
            case 5:
                return "五月";
            case 6:
                return "六月";
            case 7:
                return "七月";
            case 8:
                return "八月";
            case 9:
                return "九月";
            case ValueUtil.LineNumber /* 10 */:
                return "十月";
            case 11:
                return "十一月";
            case 12:
                return "十二月";
            default:
                return null;
        }
    }

    public static long getCurrentDayTime() {
        return System.currentTimeMillis() - getCurrentDayZeroTime();
    }

    public static boolean isBeforeTime(Time time) {
        return ((long) ((((time.getHours() * 3600) + (time.getMinutes() * 60))
                + time.getSeconds()) * 1000)) - getCurrentDayTime() > 0;
    }

    public static boolean isLateTime(Time time) {
        return ((long) ((((time.getHours() * 3600) + (time.getMinutes() * 60))
                + time.getSeconds()) * 1000)) - getCurrentDayTime() < 0;
    }

    public static Timestamp getTimestamp(Date actDate, Time time) {
        Calendar startTimeCalendar = Calendar.getInstance();
        startTimeCalendar.setTimeInMillis(time.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(actDate);
        calendar.set(11, startTimeCalendar.get(11));
        calendar.set(12, startTimeCalendar.get(12));
        calendar.set(13, startTimeCalendar.get(13));
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static long getCurrentDayZeroTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    public static Timestamp getDayZeroTime(Timestamp currentTimestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimestamp.getTime());
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static int getDays(Calendar cal) {
        int year = cal.get(1);
        switch (cal.get(2) + 1) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case ValueUtil.LineNumber /* 10 */:
            case 12:
                return 31;
            case 2:
                if ((year % 4 != 0 || year % 100 == 0) && year % 400 != 0) {
                    return 28;
                }
                return 29;
            case 4:
            case 6/* 6 */:
            case 9:
            case 11:
                return 30;
            default:
                return 0;
        }
    }

    public static boolean calculateAmmount(String strStartDate, String strEndDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate(strStartDate, "yyyy-MM-dd"));
        calendar.add(2, 1);
        calendar.add(6, -1);
        String st = format(calendar.getTime(), null);
        calendar.setTime(parseDate(strEndDate, "yyyy-MM-dd"));
        if (compareDate(st, format(calendar.getTime(), null), null) > 0) {
            return false;
        }
        return true;
    }

    public static Timestamp getOneMonthBefore() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(2, -1);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static String currentGMTTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'",
                Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date());
    }
}
