package com.line.backstage.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 日期工具类
 * @Author mmd
 * @Date 2019/9/9 11:13
 */
public class DateUtils {

    /**
     * 格式：yyyyMMdd
     */
    public static final String PATTERN_SIMPLE = "yyyyMMdd";

    /**
     * 格式：yyyyMMddHHmmss
     */
    public static final String SHORT_DATETIME_FORMAT = "yyyyMMddHHmmss";

    /**
     * 格式：yyyy-MM-dd
     */
    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String STANDARD_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式：yyyy-MM-dd HH:mm:ss.S
     */
    public static final String FULL_STANDARD_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 返回每天的 00:00:00日期 23:59:59日期
     *
     * @param date
     * @param flag 0 返回yyyy-MM-dd 00:00:00日期 1 返回yyyy-MM-dd 23:59:59日期
     * @return
     */
    public static Date weeHours(Date date, int flag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        // 时分秒（毫秒数）
        long millisecond = hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000;
        // 凌晨00:00:00
        cal.setTimeInMillis(cal.getTimeInMillis() - millisecond);

        if (flag == 0) {
            return cal.getTime();
        } else if (flag == 1) {
            // 凌晨23:59:59
            cal.setTimeInMillis(cal.getTimeInMillis() + 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000);
        }
        return cal.getTime();
    }

    /**
     * 每月第一天
     *
     * @return
     */
    public static Date monthFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date time = cal.getTime();
        String s = new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00:00:00";
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sf2.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
        //update by yxy 201907240 start
    }

    /**
     * 每月最后一天
     *
     * @return
     */
    public static Date monthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String ss = sf.format(calendar.getTime());
        String dateStr = ss + " 23:59:59";
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sf2.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseStr2Date(String dateStr) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parseStr2Date(String dateStr, String pattern) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static boolean isBetween(Date current, Date start, Date end) {
        return current.before(end) && current.after(start);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 20180515-20180517
     *
     * @param dateStr
     * @return
     */
    public static List<Date> getBetweenDates(String dateStr) {
        String[] split = dateStr.split("-");
        if (split.length != 2) {
            List<Date> lists = new ArrayList<>();
            Date date = parseStr2Date(dateStr, "yyyyMMdd");
            lists.add(date);
            return lists;
        }
        Date startDate = parseStr2Date(split[0], "yyyyMMdd");
        Date endDate = parseStr2Date(split[1], "yyyyMMdd");
        List<Date> betweenDates = getBetweenDates(startDate, endDate);
        return betweenDates;
    }

    /**
     * 20180515-20180517|20180519
     *
     * @param dateStr
     * @return
     */
    public static List<Date> getBetweenDatesHasSplit(String dateStr) {
        List<Date> results = new ArrayList<>();
        String[] strings = dateStr.split("\\|");
        for (String str : strings) {
            List<Date> betweenDates = getBetweenDates(str);
            results.addAll(betweenDates);
        }
        return results;
    }

    public static long getTime() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        String format = sdf.format(now);
        return Long.parseLong(format);
    }

    /**
     * @param begin
     * @param end
     * @return
     */
    public static List<Date> getBetweenDates(Date begin, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        while (begin.getTime() <= end.getTime()) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }
        return result;
    }


    public static String formatShortDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATETIME_FORMAT);
        return sdf.format(date);
    }

    public static String formatShortDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_SIMPLE);
        return sdf.format(date);
    }

    /**
     * 解析日期时间字符串（短格式：yyyyMMddHHmmss）
     *
     * @param dateTimeStr 日期时间字符串
     * @return 日期时间
     * @throws ParseException
     */
    public static Date parseShortDateTime(String dateTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATETIME_FORMAT);
        return sdf.parse(dateTimeStr);
    }

    /**
     * 格式日期字符串（标准格式：yyyy-MM-dd）
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String formatStandardDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * 解析日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param dateTimeStr 日期时间字符串
     * @return 日期时间
     */
    public static Date parseStandardDateTime(String dateTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);
        return sdf.parse(dateTimeStr);
    }

    /**
     * 返回当前日期数 exp:20180703 返回03
     *
     * @return
     */
    public static String nowCurrentDay() {
        String formatStandardDate = formatStandardDate(new Date());
        return formatStandardDate.split("-")[2];
    }

    /**
     * 获取上周周一
     *
     * @return
     */
    public static Date getPreviousMonday() {
        Calendar cal = Calendar.getInstance();
        // 将每周第一天设为星期一，默认是星期天
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.add(Calendar.DATE, -1 * 7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取上周周日
     *
     * @return
     */
    public static Date getSunday() {
        Calendar cal = Calendar.getInstance();
        //将每周第一天设为星期一，默认是星期天
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.add(Calendar.DATE, -1 * 7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 返回当前星期几
     *
     * @return
     * @throws Exception
     */

    public static String dayForWeek() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return String.valueOf(dayForWeek);
    }

    /**
     * 返回当月天数
     *
     * @return
     */
    public static int getMonthDays() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 格式完全日期时间字符串（包含毫秒，标准格式：yyyy-MM-dd HH:mm:ss.S）
     *
     * @param date 日期时间
     * @return 完全日期时间字符串
     */
    public static String formatFullStandardDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_STANDARD_DATETIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 格式标准日期时间字符串（包含毫秒，标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param date 日期时间
     * @return 标准日期时间字符串
     */
    public static String formatStandardDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 获取当前日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @return 日期时间字符串
     */
    public static String getStandardNowDateTime() {
        return formatStandardDateTime(new Date());
    }

    /**
     * 获取当前日期字符串（标准格式：yyyy-MM-dd）
     *
     * @return 日期字符串
     */
    public static String getStandardNowDate() {
        return formatStandardDate(new Date());
    }

    /**
     * 获取昨天的日期
     *
     * @return
     */
    public static String getYesterdayDate() {
        DateFormat df = new SimpleDateFormat(DateUtils.STANDARD_DATE_FORMAT);
        SimpleDateFormat fmt = new SimpleDateFormat(DateUtils.STANDARD_DATETIME_FORMAT);

        long yesTimeLong = System.currentTimeMillis() - (1000 * 24 * 3600);
        return df.format(new Date(yesTimeLong));
    }

    /**
     * 每周第一天
     *
     * @return
     */
    public static Date weekFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, 2);
        Date time = cal.getTime();
        String s = new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00:00:00";
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sf2.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回每周第一天 格式yyyyMMdd
     *
     * @return
     */
    public static String weekFirst() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, 2);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyyMMdd").format(time);
    }

    /**
     * 每周最后一天
     *
     * @return
     */
    public static Date weekLastDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_WEEK, -6);
        Date time = cal.getTime();
        String s = new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59:59";
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sf2.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本周的第一天
     *
     * @return String
     **/
    public static String getWeekStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00:00:00";
    }

    public static Date getWeekStartDate() {
        String s = getWeekStart();
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sf2.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本周的最后一天
     *
     * @return String
     **/
    public static String getWeekEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_WEEK, 1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59:59";
    }

    public static Date getWeekEndDate() {
        String s = getWeekEnd();
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sf2.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 昨日时间
     *
     * @return
     */
    public static Date getYesterday() {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
        try {
            return sdf.parse(DateUtils.getYesterdayDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上月第一天
     *
     * @return
     */
    public static Date lastMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date time = cal.getTime();
        String s = new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00:00:00";
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sf2.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
        //update by yxy 201907240 start
    }

    public static String getTimeForHour(Date time){
        SimpleDateFormat sf = new SimpleDateFormat("HH");
        return sf.format(time);
    }

    /**
     * 上月最后一天
     *
     * @return
     */
    public static Date lastMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String ss = sf.format(calendar.getTime());
        String dateStr = ss + " 23:59:59";
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sf2.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

/*    public static void main(String[] args) throws ParseException {
        Date yesterdayBegin =DateUtils.lastMonthFirstDay();
        Date yesterdayEnd =DateUtils.lastMonthLastDay();
        System.out.println(yesterdayBegin);
        System.out.println(yesterdayEnd);
    }*/
}
