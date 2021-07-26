package com.line.backstage.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Description: 日期工具类
 * @Author mmd
 * @Date 2019/9/9 11:13
 */
public class DateUtil {

    /**
     * 格式：yyyy-MM-dd HH:mm:ss.S
     */
    public static final String FULL_STANDARD_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String STANDARD_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 格式：yyyy-MM-dd HH:mm
     */
    public static final String INCOMPLETE_DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    /**
     * 格式：yyyyMMddHHmmss
     */
    public static final String SHORT_DATETIME_FORMAT = "yyyyMMddHHmmss";
    /**
     * 格式：yyyy-MM-dd
     */
    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 格式：yyyyMMdd
     */
    public static final String SHORT_DATE_FORMAT = "yyyyMMdd";
    /**
     * 格式：MMdd
     */
    public static final String SHORT_MMDD_FORMAT = "MMdd";
    /**
     * 格式：HHmmss
     */
    public static final String SHORT_TIME_FORMAT = "HHmmss";
    /**
     * 格式: yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    public static final String UTC_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static final String SHORT_YYMM_FORMAT = "yyyyMM";

    private DateUtil() {
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
     * 格式完全日期时间字符串（包含毫秒，标准格式：yyyy-MM-dd HH:mm:ss.S）
     *
     * @param date 日期时间
     * @return 完全日期时间字符串
     */
    public static String parseIncompleteDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(INCOMPLETE_DATETIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 格式日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param date 日期时间
     * @return 日期时间字符串
     */
    public static String formatStandardDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 格式日期时间字符串（短格式：yyyyMMddHHmmss）
     *
     * @param date 日期时间
     * @return 日期时间字符串
     */
    public static String formatShortDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATETIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 格式日期时间字符串（短格式：yyyyMMddHHmmss）
     *
     * @param date 日期时间
     * @param zone 时区
     * @return 日期时间字符串
     */
    public static String formatShortDateTime(Date date, TimeZone zone) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATETIME_FORMAT);
        sdf.setTimeZone(zone);
        return sdf.format(date);
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
     * 格式日期字符串（短格式：yyyyMMdd）
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String formatShortDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_FORMAT);
        return sdf.format(date);
    }

    public static int getTodayIntNum() {
        return Integer.parseInt(formatShortDate(new Date()));
    }

    /**
     * 格式日期字符串（短格式：yyyyMMdd）
     *
     * @param date 日期
     * @param zone 时区
     * @return 日期字符串
     */
    public static String formatShortDate(Date date, TimeZone zone) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_FORMAT);
        sdf.setTimeZone(zone);
        return sdf.format(date);
    }

    /**
     * 格式日期字符串（短格式：MMdd）
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String formatShortMMDDDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_MMDD_FORMAT);
        return sdf.format(date);
    }

    /**
     * 格式时间字符串（短格式：HHmmss）
     *
     * @param date 时间
     * @return 时间字符串
     */
    public static String formatShortTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_TIME_FORMAT);
        return sdf.format(date);
    }

    /**
     * 解析完全日期时间字符串（包含毫秒，标准格式：yyyy-MM-dd HH:mm:ss.S）
     *
     * @param dateTimeStr 完全日期时间字符串
     * @return 日期时间
     * @throws ParseException
     */
    public static Date parseFullStandardDateTime(String dateTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_STANDARD_DATETIME_FORMAT);
        return sdf.parse(dateTimeStr);
    }

    /**
     * 解析日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param dateTimeStr 日期时间字符串
     * @return 日期时间
     * @throws ParseException
     */
    public static Date parseStandardDateTime(String dateTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATETIME_FORMAT);
        return sdf.parse(dateTimeStr);
    }

    /**
     * 解析日期时间字符串 (不完整格式：yyyy-MM-dd HH:mm)
     *
     * @param dateTimeStr
     * @return
     * @throws ParseException
     */
    public static Date parseIncompleteDateTime(String dateTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(INCOMPLETE_DATETIME_FORMAT);
        return sdf.parse(dateTimeStr);
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
     * 解析日期时间字符串（短格式：yyyyMMddHHmmss）
     *
     * @param dateTimeStr 日期时间字符串
     * @param zone        时区
     * @return 日期时间
     * @throws ParseException
     */
    public static Date parseShortDateTime(String dateTimeStr, TimeZone zone) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATETIME_FORMAT);
        sdf.setTimeZone(zone);
        return sdf.parse(dateTimeStr);
    }

    /**
     * 解析日期字符串（标准格式：yyyy-MM-dd）
     *
     * @param dateStr 日期字符串
     * @return 日期
     * @throws ParseException
     */
    public static Date parseStandardDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
        return sdf.parse(dateStr);
    }

    /**
     * 解析日期字符串（短格式：yyyyMMdd）
     *
     * @param dateStr 日期字符串
     * @return 日期
     * @throws ParseException
     */
    public static Date parseShortDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_FORMAT);
        return sdf.parse(dateStr);
    }

    /**
     * 解析日期字符串（短格式：yyyyMMdd）
     *
     * @param dateStr 日期字符串
     * @param zone    时区
     * @return 日期
     * @throws ParseException
     */
    public static Date parseShortDate(String dateStr, TimeZone zone) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_FORMAT);
        sdf.setTimeZone(zone);
        return sdf.parse(dateStr);
    }

    /**
     * 解析日期字符串（短格式：MMdd）
     *
     * @param dateStr 日期字符串
     * @return 日期
     * @throws ParseException
     */
    public static Date parseShortMMDDDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_MMDD_FORMAT);
        return sdf.parse(dateStr);
    }

    /**
     * 解析时间字符串（短格式：HHmmss）
     *
     * @param dateStr 时间字符串
     * @return 时间
     * @throws ParseException
     */
    public static Date parseShortTime(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_TIME_FORMAT);
        return sdf.parse(dateStr);
    }

    /**
     * 获取传入日期的上一天
     *
     * @param date
     * @return 日期
     */
    public static Date getStandardDatePre(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 获取传入日期的下一天
     *
     * @param date
     * @return 日期
     */
    public static Date getStandardDateNext(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * 获取传入日期的下几天
     *
     * @param date
     * @return 日期
     */
    public static Date getStandardDateNext(Date date, Integer number) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, number);
        return cal.getTime();
    }


    /**
     * 获取指定日期的0时0分0秒
     *
     * @param date
     * @return
     */
    public static Date getStartTimeOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取指定日期的0时0分0秒
     *
     * @param date
     * @return
     */
    public static Date getEndTimeOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /**
     * 获取当天的0时0分0秒
     *
     * @return
     */
    public static Date getStartTimeOfToday() {
        return getStartTimeOfDay(new Date());
    }

    //获得当天24点时间
    public static int getTimesnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static Long getTimesnight2() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 获取当前时间到当天结束的秒数
     *
     * @return
     */
    public static int getTodayEndTime() {
        Long t1 = getTimesnight2();
        return (int) ((t1 - System.currentTimeMillis()) / 1000);
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
     * 获取当前日期时间字符串（短格式：yyyyMMddHHmmss）
     *
     * @return 日期时间字符串
     */
    public static String getShortNowDateTime() {
        return formatShortDateTime(new Date());
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
     * 获取当前日期字符串（短格式：yyyyMMdd）
     *
     * @return 日期字符串
     */
    public static String getShortNowDate() {
        return formatShortDate(new Date());
    }

    /**
     * 获取当前日期时间字符串（格式：yyyy-MM-dd'T'HH:mm:ss'Z'）
     * <p>
     * UTC时间对应的时区是GMT-0:00
     *
     * @return 日期时间字符串
     */
    public static String getUTCDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(UTC_DATETIME_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-0:00"));
        return sdf.format(new Date());
    }

    /**
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: getCurrentYearAndMonth
     * @author weiqingping
     * @dateTime 2017年4月13日 上午11:13:55
     * @email weiqingping@migu.cn
     * @Description: TODO 获取当年时间的年月 格式为 yyyyMM
     */
    public final static String getCurrentYearAndMonth() {
        return getYearAndMonth(new Date());
    }

    /**
     * @param @param  date
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: getYearAndMonth
     * @author weiqingping
     * @dateTime 2017年4月13日 上午11:10:39
     * @email weiqingping@migu.cn
     * @Description: TODO  获取某事件的年月 格式为yyyyMM
     */
    public static String getYearAndMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_YYMM_FORMAT);

        return sdf.format(date);
    }


    /**
     * 获取月份的开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartTimeOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 当前时间加一分钟
     *
     * @param jdTimeStr
     * @param num
     */
    public static String addMin(String jdTimeStr, Integer num, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        try {
            long time = sdf.parse(jdTimeStr).getTime();
            Date date = new Date(time + (num * 60 * 1000));
            String format = sdf.format(date);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据身份证号计算年龄
     *
     * @param pensonnelIdCard
     * @return
     */
    public static int getPersonAgeFromIdCard(String pensonnelIdCard) {
        try {
            //截取身份证中出行人出生日期中的年、月、日
            Integer personYear = Integer.parseInt(pensonnelIdCard.substring(6, 10));
            Integer personMonth = Integer.parseInt(pensonnelIdCard.substring(10, 12));
            Integer personDay = Integer.parseInt(pensonnelIdCard.substring(12, 14));
            Calendar cal = Calendar.getInstance();
            // 得到当前时间的年、月、日
            Integer yearNow = cal.get(Calendar.YEAR);
            Integer monthNow = cal.get(Calendar.MONTH) + 1;
            Integer dayNow = cal.get(Calendar.DATE);
            // 用当前年月日减去生日年月日
            Integer yearMinus = yearNow - personYear;
            Integer monthMinus = monthNow - personMonth;
            Integer dayMinus = dayNow - personDay;
            int age = yearMinus; //先大致赋值
            if (yearMinus == 0) { //出生年份为当前年份
                age = 0;
            } else { //出生年份大于当前年份
                if (monthMinus < 0) {//出生月份小于当前月份时，还没满周岁
                    age = age - 1;
                }
                if (monthMinus == 0) {//当前月份为出生月份时，判断日期
                    if (dayMinus < 0) {//出生日期小于当前月份时，没满周岁
                        age = age - 1;
                    }
                }
            }
            return age;
        } catch (Exception e) {
        }
        return -1;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     */
    public static String timeStamp2DateStr(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     */
    public static Date timeStamp2Date(String seconds) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return new Date();
        }
        return new Date(Long.valueOf(seconds + "000"));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    /**
     * 取得昨天0点时间戳
     *
     * @return
     */
    public static String getYesterdayZeroStamp() {
        return String.valueOf(Long.parseLong(DateUtil.getTodayZeroStamp()) - 86400);
    }

    /**
     * 取得昨天8点时间戳
     *
     * @return
     */
    public static String getYesterdayEightStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-1,8,0,0);
        return String.valueOf(calendar.getTime().getTime()/1000);
    }

    /**
     * 取得昨天此时时间戳
     *
     * @return
     */
    public static String getYesterdayStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return String.valueOf(calendar.getTime().getTime() / 1000);
    }

    /**
     * 取得昨天此时时间戳 分钟
     *
     * @return
     */
    public static String getYesterdayMinuteStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        long ss = calendar.getTime().getTime() / 1000;
        long mm = ss - (ss % 60);
        return String.valueOf(mm);
    }

    /**
     * 取得昨天此时时间戳 小时
     *
     * @return
     */
    public static String getYesterdayHourStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        long ss = calendar.getTime().getTime() / 1000;
        long hh = ss - (ss % 3600);
        return String.valueOf(hh);
    }

    /**
     * 取得今天0点时间戳
     *
     * @return
     */
    public static String getTodayZeroStamp() {
        long nowTime = System.currentTimeMillis();
        long todayStartTime = nowTime - ((nowTime + TimeZone.getDefault().getRawOffset()) % (24 * 60 * 60 * 1000L));
        return String.valueOf((todayStartTime / 1000));
    }

    /**
     * date转时间戳 秒
     *
     * @return
     */
    public static String dateToTimeStamp(Date date) {
        return String.valueOf((date.getTime() / 1000));
    }

    /**
     * 取得当前时间戳（精确到分钟） error
     *
     * @return
     */
    public static String getNowMinuteStamp() {
        return String.valueOf((System.currentTimeMillis()) / 60000);
    }

    /**
     * 取得5分钟后时间戳（精确到分钟）
     *
     * @return
     */
    public static String getFiveMinuteAfterStamp() {
        return String.valueOf((System.currentTimeMillis() + 300000) / 60000);
    }

    /**
     * 取得2分钟后时间戳（精确到分钟）
     *
     * @return
     */
    public static String getTowMinuteAfterStamp() {
        return String.valueOf((System.currentTimeMillis() + 120000) / 60000);
    }

    /**
     * 取得当前秒钟数
     *
     * @return
     */
    public static String getMillionNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("ss");
        return sdf.format(new Date());
    }

}
