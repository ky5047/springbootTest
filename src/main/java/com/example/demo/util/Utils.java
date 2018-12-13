package com.example.demo.util;

/**
 * Created by 92992 on 2018/12/7.
 */
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import com.example.demo.util.string.StringUtil;

public class Utils {

    /**
     * 要用到的DATE Format的定义
     */
    public static String DATE_FORMAT_DATEONLY = "yyyy-MM-dd";
    public static String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMAT_DATEONLY_1 = "yyyyMMdd";
    public static String DATE_FORMAT_DATETIME_2 = "yyyyMMddHHmmss";
    public static String DATE_FORMAT_DATETIME_SSS = "yyyyMMddHHmmssSSS";


    // 时间日期格式转换
    public static Date sqlTimestamp2Date(java.sql.Timestamp timestamp) {
        if (timestamp == null)
            return null;
        return new Date(timestamp.getTime());
    }

    public static Date sqlTime2Date(java.sql.Time time) {
        if (time == null)
            return null;
        return new Date(time.getTime());
    }

    public static Date sqlDate2Date(java.sql.Date date) {
        if (date == null)
            return null;
        return new Date(date.getTime());
    }

    public static Date truncDate(Date date) {
        return dateString2Date(date2DateString(date));
    }

    public static String date2DateString(Date date) {
        return date2String(date, "yyyy-MM-dd");
    }

    public static String date2DateString2(Date date) {
        return date2String(date, "yyyy/MM/dd");
    }

    public static Date dateString2Date(String str) {
        return string2Date(str.replace("-", ""), "yyyyMMdd");
    }

    public static java.util.Date long2Date(long timestampLong) {
        return new java.util.Date(timestampLong);
    }

    public static Date dateYearString2Date(String str) {
        return string2Date(str.replace("-", ""), "yyyy");
    }

    public static String date2YearMonthString(Date date) {
        return date2String(date, "yyyy-MM");
    }

    public static String date2MonthDayString(Date date) {
        return date2String(date, "MM-dd");
    }

    public static Date yearMonthString2Date(String str) {
        if (isNullString(str))
            return null;
        return string2Date(str.replace("-", ""), "yyyyMM");
    }

    public static boolean isValidYearMonthString(String str) {
        if (str == null || !str.matches("(\\d){4}-(([0][1-9])|([1][012]))"))
            return false;
        return true;
    }

    public static String date2TimeString(Date date) {
        return date2String(date, "HH:mm:ss");
    }

    public static String date2DateTimeString(Date dateTime) {
        return date2String(dateTime, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date dateTimeString2Date(String str) {
        return string2Date(
                str.replace("-", "").replace(" ", "").replace(":", ""),
                "yyyyMMddHHmmss");
    }

    public static String date2WebDateTimeString(Date dateTime) {
        return date2String(dateTime, "yyyy/MM/dd HH:mm:ss");
    }

    public static String date2WebDateHhMmString(Date dateTime) {
        return date2String(dateTime, "yyyy/MM/dd HH:mm");
    }

    public static Date webDateTimeString2Date(String str) {
        return string2Date(str, "yyyy/MM/dd HH:mm:ss");
    }

    public static boolean isValidWebDateTime(String str) {
        return null != webDateTimeString2Date(str);
    }

    public static String date2String(Date date, String format) {
        if (date == null)
            return "";
        return new SimpleDateFormat(format).format(date);
    }

    public static Date string2Date(String str, String format) {
        if (isNullString(str)) {
            return null;
        }
        return new SimpleDateFormat(format).parse(str, new ParsePosition(0));
    }

    public static void checkStartMonthAndEndMonth(String startMonthStr,
                                                  String endMonthStr) {
        Date startMonth = yearMonthString2Date(startMonthStr);
        Date endMonth = yearMonthString2Date(endMonthStr);
        if (startMonth.after(endMonth))
            throw new IllegalArgumentException(
                    "起始月份${startMonthStr}大于结束月份${endMonthStr}");
    }

    public static String formatYearMonth(String yearMonthStr) {
        if (yearMonthStr.length() == 6)
            return yearMonthStr.substring(0, 4) + "-"
                    + yearMonthStr.substring(4);
        else
            return yearMonthStr;
    }

    public static String formatYearMonthChina(String yearMonthStr) {
        if (yearMonthStr.length() == 7)
            return yearMonthStr.substring(0, 4) + "年"
                    + yearMonthStr.substring(5) + "月";
        else
            return yearMonthStr;
    }

    // 把日期转换成字符串("yyyy 年 MM 月 dd 日")
    public static String formatYearMonthDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        return calendar.get(Calendar.YEAR) + "  年    "
                + (calendar.get(Calendar.MONTH) + 1) + "  月    "
                + calendar.get(Calendar.DATE) + "  日";
    }

    // 通过日期获取 天
    public static int getDayByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    // 通过日期获取 月
    public static int getMonthByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        return (calendar.get(Calendar.MONTH) + 1);
    }

    // 通过日期获取 年
    public static int getYearByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    /*
    * 与当前时间相比，是否已经跨月
    * */
    public  static boolean isPassThisMonth(Date date){
        int year = getYearByDate(date);
        int month = getMonthByDate(date);
        Date now = new Date();
        int currentYear = getYearByDate(now);
        int currentMonth = getMonthByDate(now);
        if(currentYear > year){
            return true;
        }else if((currentYear == year) && (currentMonth > month)){
            return true;
        }
        return false;
    }

    // 通过日期获取 Hour
    public static int getHourByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static Date nextNMonth(Date currentDate, int month) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        cal.add(GregorianCalendar.MONTH, month);// 在月份上加month
        return cal.getTime();

    }

    public static Date nextMonth(Date currentDate) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        cal.add(GregorianCalendar.MONTH, 1);// 在月份上加1
        return cal.getTime();

    }

    /**返回日期的下月第N天的日期*/
    public static Date nextMonthNDay(Date currentDate, int day) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        cal.add(GregorianCalendar.MONTH, 1);// 在月份上加1
        cal.set(Calendar.DAY_OF_MONTH, 1);// 把日期设为当月第一天
        cal.add(GregorianCalendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    //获取上个月和的今天的这个时间
    public static Date beforeMonth(Date currentDate) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        cal.add(GregorianCalendar.MONTH, -1);// 在月份上减1
        return cal.getTime();

    }

    /**
     * 在年份上加1
     *
     * @param currentDate
     * @return
     */
    public static Date nextYear(Date currentDate) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        cal.add(GregorianCalendar.YEAR, 1);// 在年份上加1
        return cal.getTime();

    }

    // strDate 例:2012-12 返回 2012-11
    public static String beforeMonthStr(String strDate) {
        Date date = yearMonthString2Date(strDate);

        Date beforeMonth = beforeMonth(date);

        String beforeMonthStr = date2YearMonthString(beforeMonth);

        return beforeMonthStr;
    }

    // strDate 例:2012-12 返回 2013-01
    public static String nextMonthStr(String strDate) {
        Date date = yearMonthString2Date(strDate);

        Date nextMonth = nextMonth(date);

        String nextMonthStr = date2YearMonthString(nextMonth);

        return nextMonthStr;
    }

    public static Date nextDay(Date currentDate) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        cal.add(GregorianCalendar.DAY_OF_MONTH, 1);// 在天数加1
        return cal.getTime();

    }

    public static Date beforeDay(Date currentDate) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        cal.add(GregorianCalendar.DAY_OF_MONTH, -1);// 在天数减1
        return cal.getTime();

    }

    /**
     * 多少天之前的日期
     * */
    public static Date toDayBefore(Integer beforeDays) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(GregorianCalendar.DAY_OF_MONTH, -beforeDays);// 在天数减1
        return cal.getTime();
    }

    /**
     * 在参数日期的基础上;加多少天之后的日期
     * */
    public static Date toDayAfter(Date date, int afterDays) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(GregorianCalendar.DAY_OF_MONTH, +afterDays);// 在天数加1
        return cal.getTime();
    }

    /**
     * 求日期与现在相差几天
     * */
    public static Integer countDaysBetweenDate(Date inputDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long time1 = cal.getTimeInMillis();
        cal.setTime(inputDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time1 - time2) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 求两个日期相差几天
     * @param fDate 被减数
     * @param oDate 减数
     * @return
     */
    public static Integer countDaysBetweenTwo(Date fDate, Date oDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(oDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time1 - time2) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /***
     * 获取当年已过多少天
     *
     * @return
     */
    public static int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        if (yearOfNumber == 1) {
            return -MaxYear;
        } else {
            return 1 - yearOfNumber;
        }
    }

    /***
     * 获取当年第一天
     *
     * @return
     */
    public static Date getCurrentYearFirst() {
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus);
        return currentDate.getTime();
    }

    /***
     * 获取当月第一天
     *
     * @return
     */
    public static Date getCurrentMonthFirst() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return c.getTime();
    }

    /***
     * 获取上个月第一天
     *
     * @return
     */
    public static Date getPreMonthFirst() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return c.getTime();
    }

    /***
     * 获取上个月最后一天
     *
     * @return
     */
    public static Date getPreMonthLast() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);//当月
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        c.add(Calendar.DAY_OF_MONTH, -1);//回滚一天
        return c.getTime();
    }

    /***
     * 获取某年某月最后一天
     *
     * @return
     */
    public static Date getMonthLastDate(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);// 设置
        c.set(Calendar.MONTH, month);// 设置为下月
        c.set(Calendar.DAY_OF_MONTH, 1); // 设置为1号
        c.add(Calendar.DAY_OF_MONTH, -1);//回滚一天
        return c.getTime();
    }

    /***
     * 获取某年某月第一天
     *
     * @return
     */
    public static Date getMonthFirstDate(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);// 设置
        c.set(Calendar.MONTH, month-1);//注意month-1=0时表示第一个月
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return c.getTime();
    }

    /***
     * 获取当月最后一天
     *
     * @return
     */
    public static Date getCurrentMonthLastDay(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        return c.getTime();
    }

    /**
     * 判断季度
     * */
    public static int getCurrentQuarter(int month){
        switch(month){
            case 1:
            case 2:
            case 3:
                return 1;
            case 4:
            case 5:
            case 6:
                return 2;
            case 7:
            case 8:
            case 9:
                return 3;
            case 10:
            case 11:
            case 12:
                return 4;
            default: return 1;
        }
    }

    /**
     * @param year
     * @param quarter
     *
     * 返回年内第几季度的月份范围，包含年份
     * */
    public static List<String> getMonthRange(int year, int quarter){
        List<String> list = new ArrayList<String>(2);

        switch(quarter){
            case 1:
                list.add(String.valueOf(year)+"-01");
                list.add(String.valueOf(year)+"-03");
                break;
            case 2:
                list.add(String.valueOf(year)+"-04");
                list.add(String.valueOf(year)+"-06");
                break;
            case 3:
                list.add(String.valueOf(year)+"-07");
                list.add(String.valueOf(year)+"-09");
                break;
            case 4:
                list.add(String.valueOf(year)+"-10");
                list.add(String.valueOf(year)+"-12");
                break;
            default:
                list.add(String.valueOf(year)+"-01");
                list.add(String.valueOf(year)+"-03");
                break;
        }
        return list;
    }

    /***
     * 调节日期的年月日时分秒
     *
     * @return
     */
    public static Date adjustDate(Date date, int year, int month, int day, int hour24, int minute, int second){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if(year!=-1){
            calendar.set(Calendar.YEAR,year);
        }
        if(month!=-1){
            calendar.set(Calendar.MONTH,month-1);
        }
        if(day!=-1){
            calendar.set(Calendar.DAY_OF_MONTH,day);
        }
        if(hour24!=-1){
            calendar.set(Calendar.HOUR_OF_DAY,hour24);
        }
        if(minute!=-1){
            calendar.set(Calendar.MINUTE,minute);
        }
        if(second!=-1){
            calendar.set(Calendar.SECOND,second);
        }
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /***
     * 调节增加日期的年月日时分秒
     *
     * @return
     */
    public static Date adjustAddDate(Date date, int year, int month, int day, int hour24, int minute, int second){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if(year!=0){
            calendar.add(Calendar.YEAR,year);
        }
        if(month!=0){
            calendar.add(Calendar.MONTH,month);
        }
        if(day!=0){
            calendar.add(Calendar.DAY_OF_MONTH,day);
        }
        if(hour24!=0){
            calendar.add(Calendar.HOUR_OF_DAY,hour24);
        }
        if(minute!=0){
            calendar.add(Calendar.MINUTE,minute);
        }
        if(second!=0){
            calendar.add(Calendar.SECOND,second);
        }
        return calendar.getTime();
    }

    // ================================================================================
    // 日期计算、比较
    // ================================================================================
    public static boolean isSameYearMonth(Date date1, Date date2) {
        if (date1 == date2)
            return true;
        if (date1 == null && date2 != null)
            return false;

        return date2YearMonthString(date1).equals(
                date2YearMonthString(date2));
    }

    public static boolean isSameYearMonthDateTime(Date date1, Date date2) {
        if (date1 == date2)
            return true;
        if (date1 == null && date2 != null)
            return false;

        return date2DateTimeString(date1).equals(
                date2DateTimeString(date2));
    }

    public static Date addDay(int nDay) {
        return new Date(new Date().getTime() + nDay * 24 * 60 * 60 * 1000);
    }

    /**
     * 对双周轮换的排班检查最后一天是排了几周，为1
     * */
    public static int getweeks(int endNo, int startNo , boolean flag){
        if(flag){
            return (endNo-startNo+1)/7%2 == 1?1:2;
        }else{
            return (endNo-startNo+1)/7%2 == 1?2:1;

        }
    }

    // ================================================================================
    // UUID 和 顺序号生成
    // ================================================================================
    // generate UUID
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    // ================================================================================
    // 字符串相关
    // ================================================================================
    // string related
    public static String padString(String str, int width, char padChar) {
        if (str.length() < width) {
            char[] paddingArray = new char[width - str.length()];
            Arrays.fill(paddingArray, padChar);
            String padding = String.valueOf(paddingArray);
            return padding + str;
        } else {
            return str;
        }
    }

    public static String unnull(String str) {
        if (str == null)
            return "";
        else
            return str;
    }

    //String null 字符也返回''
    public static String unnullString(String str) {
        if (str == null){
            return "";
        }else if (str.equals("null")){
            return "";
        }else{
            return str;
        }
    }

    public static boolean isNullString(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String bracket(String str) {
        return "(" + str + ")";
    }

    public static String quote(String str) {
        return "'" + str + "'";
    }

    public static String doubleQuote(String str) {
        return "\"" + str + "\"";
    }

    public static boolean isNumber(String str) {
        String regex = "[+-]?\\d*[.]?[\\d]+";
        return str.matches(regex);
    }

    public static String join(String sep, String... strs) {
        return join(strs, sep);
    }

    public static String join(String[] strs, String sep) {
        return join(Arrays.asList(strs), sep);
    }

    public static String join(Collection<String> strs, String sep) {
        String tmpSep = "";
        String joinStr = "";
        for (String str : strs) {
            joinStr += tmpSep;
            joinStr += str;
            tmpSep = sep;
        }
        return joinStr;
    }

    public static String trim(String str) {
        if (str == null)
            return str;
        return str.replaceAll("^\\s+", "").replaceAll("\\s+$", "");
    }

    public static String trimSpace(String str) {
        if (str == null)
            return str;
        return str.replaceAll("\\s", "");
    }

    public static String protectFromNull(String from, String placeHolder) {
        return isNullString(from) ? placeHolder : from;
    }

    public static String concatPath(String... paths) {
        return join("/", paths).replaceAll("//", "/");
    }

    public static boolean isDigitString(String str) {
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (!Character.isDigit(ch))
                return false;
        }
        return true;
    }

    // 判断两个字符串是否相等
    public static boolean compareString(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }
        if (str1.trim().equals(str2.trim()))
            return true;
        else
            return false;
    }

    // 全角字符转换成半角字符
    public static String Q2B(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    /**
     * 判断number 的小数部分是否超过 count 位，末位的0不考虑
     */
    public static boolean isFractionExceed(BigDecimal number, int count) {
        BigDecimal n = number.movePointRight(count);
        try {
            n.toBigIntegerExact();
            return false;
        } catch (Exception ex) {
            return true;
        }
    }

    /**
     * 判断 o11-o12 与 o21-o22两段范围是否有重叠
     *
     * @param <T>
     *
     * @param o11
     * @param o12
     * @param o21
     * @param o22
     * @return
     */
    public static <T extends Comparable<T>> boolean isOverlapped(T o11, T o12,
                                                                 T o21, T o22) {
        return !((o11.compareTo(o21) < 0 && o12.compareTo(o21) < 0) || (o11
                .compareTo(o22) > 0 && o12.compareTo(o22) > 0));
    }

    /*
     * 去除html格式,仅留下文本
     */
    public static String getHtmlText(String content) {
        content = content.replaceAll("</?[^>]+>", ""); // 剔出了<html>的标签
        content = content.replace("&nbsp;", "");
        content = content.replace("&mdash;", "-");
        content = content.replace(".", "");
        content = content.replace("\"", "‘");
        content = content.replace("'", "‘");
        content = content.replaceAll("\\s*|\t|\r|\n", "");// 去除字符串中的空格,回车,换行符,制表符
        return content;

    }

    // ================================================================================
    // BigDecimal 相关
    // ================================================================================
    // compare BigDecimal
    public static boolean gt(BigDecimal left, BigDecimal right) {
        if (left == null || right == null)
            return false;
        return left.compareTo(right) > 0;
    }

    public static boolean lt(BigDecimal left, BigDecimal right) {
        if (left == null || right == null)
            return false;
        return left.compareTo(right) < 0;
    }

    public static boolean ge(BigDecimal left, BigDecimal right) {
        if (left == right)
            return true;
        if (left == null || right == null)
            return false;
        return left.compareTo(right) >= 0;
    }

    public static boolean le(BigDecimal left, BigDecimal right) {
        if (left == right)
            return true;
        if (left == null || right == null)
            return false;
        return left.compareTo(right) <= 0;
    }

    public static boolean eq(BigDecimal left, BigDecimal right) {
        if (left == right)
            return true;
        if (left == null || right == null)
            return false;
        return left.compareTo(right) == 0;
    }

    public static boolean eqAmount(BigDecimal left, BigDecimal right) {
        if (left == right)
            return true;
        if (left == null || right == null)
            return false;
        return left.subtract(right).abs().compareTo(new BigDecimal("0.01")) <= 0;
    }

    public static String bigDecimal2String(BigDecimal value) {
        if (value == null)
            return "";
        return value.toPlainString();
    }

    public static BigDecimal string2BigDecimal(String str) {
        if (isNullString(str))
            return null;
        return new BigDecimal(str);
    }

    public static BigDecimal roundHalfUp(BigDecimal val, int scale) {
        if (val == null)
            val = BigDecimal.ZERO;
        return val.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal roundMoney(BigDecimal val) {
        return roundMoney(val, 2);
    }

    public static BigDecimal roundMoney(BigDecimal val, int scale) {
        return roundHalfUp(val, scale);
    }

    /**
     * 截数，不四舍五入 如12.5687, 返回12.56
     *
     * @param val
     * @param scale
     * @return
     */
    public static BigDecimal roundDown(BigDecimal val, int scale) {
        return val.setScale(scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * 向上取整,有小数就整数部分加1
     *
     * @param val
     * @return
     */
    public static int ceil(double val) {
        return new BigDecimal(Math.ceil(val)).intValue(); // Math.ceil(val);
    }

    public static Object formartBigDecimal(BigDecimal val, int scale) {
        Object obj = "";
        try{
            NumberFormat formater = NumberFormat.getInstance();
            formater.setGroupingUsed(true);
            formater.setMaximumFractionDigits(scale);
            formater.setMinimumFractionDigits(scale);
            formater.setRoundingMode(RoundingMode.HALF_UP);
            obj = formater.format(val);
        }catch(NumberFormatException ne){
            obj = val;
        }
        return obj;
    }

    /**
     * 小数转百分比
     *
     * @val 要转的数
     * @maxInteger 整数部分保留位数， 建议3位以上
     * @maxFraction 小数部分保留位数，建议2位以上
     * @mode 转换模式，RoundingMode.DOWN:向下取整
     * */
    public static String number2Percent(BigDecimal val, int maxInteger,
                                        int maxFraction, RoundingMode mode) {
        NumberFormat num = NumberFormat.getPercentInstance();
        num.setMaximumIntegerDigits(maxInteger);
        num.setMaximumFractionDigits(maxFraction);
        num.setRoundingMode(mode);
        return num.format(val);

    }

    // ================================================================================
    // List相关
    // ================================================================================
    // 获取List的第一个元素
    public static <T> T getFirst(List<T> list) {
        if (list == null || list.isEmpty())
            return null;
        return list.get(0);
    }

    // 获取List的最后一个元素
    public static <T> T getLast(List<T> list) {
        if (list == null || list.isEmpty())
            return null;
        return list.get(list.size() - 1);
    }

    // 将list按size分成多个list
    public static <T> List<List<T>> divide(List<T> list, int size) {
        if (list == null || size == 0)
            throw new IllegalArgumentException("list = " + list + " size = "
                    + size);

        List<List<T>> ret = new ArrayList<List<T>>();
        for (int count = 0; count < list.size();) {
            List<T> subList = list.subList(count,
                    Math.min((ret.size() + 1) * size, list.size()));
            ret.add(subList);
            count += subList.size();
        }
        return ret;
    }

    /**
     * 获取sublist
     *
     * @param <T>
     * @param list
     * @param offset
     *            起始下标
     * @param count
     *            要获取的元素个数，如果count小于0，获取从offset开始到list最后一个元素
     * @return
     */
    public static <T> List<T> subList(List<T> list, int offset, int count) {
        if (list == null) {
            return null;
        }
        if (offset >= list.size())
            return new ArrayList<T>();

        if (count < 0)
            return list.subList(offset, list.size());
        else
            return list.subList(offset, Math.min(list.size(), offset + count));
    }

    // ================================================================================
    // 打印Map内容
    // ================================================================================
    public static void printMap(Map<?, ?> map) {
        List<String> list = new ArrayList<String>();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            list.add(doubleQuote(entry.getKey().toString()) + ":"
                    + doubleQuote(entry.getValue().toString()));
        }
    }

    // ================================================================================
    // 加密相关
    // ================================================================================
    // SHA encrypt
    public static String shaEncrypt(String str) {
        return hashEncrypt(str, "SHA");
    }

    public static String hashEncrypt(String string, String encryptType) {
        try {
            MessageDigest md = MessageDigest.getInstance(encryptType);
            byte[] result = md.digest(string.getBytes());
            return bytes2HexString(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("encrypt fail " + e.getMessage());
        }
    }

    // ================================================================================
    // byte[]与16进制表示形式转换
    // ================================================================================
    public static byte[] hexString2Bytes(String str) {
        if (str == null || str.length() == 0)
            return new byte[0];

        if (str.length() % 2 != 0)
            throw new IllegalArgumentException(
                    "string length should be times of 2");

        byte[] bytes = new byte[str.length() / 2];
        int i = 0;
        while (i < bytes.length) {
            Short s = Short.parseShort(str.substring(i * 2, (i + 1) * 2), 16);
            bytes[i] = s.byteValue();
            i++;
        }

        return bytes;
    }

    public static String bytes2HexString(byte in[]) {

        byte ch = 0x00;

        int i = 0;

        if (in == null || in.length <= 0)

            return null;

        String pseudo[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F" };

        StringBuffer out = new StringBuffer(in.length * 2);

        while (i < in.length) {

            // Strip off high nibble
            ch = (byte) (in[i] & 0xF0);

            // shift the bits down
            ch = (byte) (ch >>> 4);

            // must do this is high order bit is on!
            ch = (byte) (ch & 0x0F);

            // convert the nibble to a String
            out.append(pseudo[(int) ch]);

            // Strip off low nibble
            ch = (byte) (in[i] & 0x0F);

            // convert the nibble to a String
            out.append(pseudo[(int) ch]);

            i++;

        }

        String rslt = new String(out);

        return rslt;
    }

    /**
     * 返回十六进制字符串
     *
     * @param arr
     * @return
     */
    public static String hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    // ================================================================================
    // 压缩相关
    // ================================================================================
    // GZip
    public static byte[] gzip(String str) {
        ByteArrayOutputStream os = null;
        GZIPOutputStream gzipOs = null;
        try {
            os = new ByteArrayOutputStream();
            gzipOs = new GZIPOutputStream(os);
            byte[] bytes = str.getBytes("UTF-8");
            gzipOs.write(bytes, 0, bytes.length);
            gzipOs.finish();
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return str.getBytes();
        } finally {
            try {
                if (gzipOs != null)
                    gzipOs.close();
                if (os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String ungzip(byte[] bytes) {
        ByteArrayInputStream is = null;
        GZIPInputStream gzipIs = null;
        ByteArrayOutputStream os = null;
        try {
            is = new ByteArrayInputStream(bytes);
            gzipIs = new GZIPInputStream(is);

            os = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int count;
            do {
                count = gzipIs.read(buf, 0, buf.length);
                if (count > 0)
                    os.write(buf, 0, count);
            } while (count > 0);
            return new String(os.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return new String(bytes);
        } finally {
            try {
                if (os != null)
                    os.close();
                if (gzipIs != null)
                    gzipIs.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ================================================================================
    // Object相关
    // ================================================================================
	/*
	 * 克隆对象，值传递，非引用 obj-被克隆的对象
	 */
    public static Object cloneObject(Object obj) throws Exception {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            out = new ObjectOutputStream(byteOut);
            out.writeObject(obj);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(
                    byteOut.toByteArray());
            in = new ObjectInputStream(byteIn);
            return in.readObject();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("报错具体信息为:" + ex.getMessage());
        }finally{
            if(out != null){
                try{
                    out.close();
                }catch(IOException ioe){
                    ;
                }
            }

            if(in != null){
                try{
                    in.close();
                }catch(IOException ioe){
                    ;
                }
            }
        }
    }

    /***
     * 字符串转整数
     *
     * @param str
     * @return
     */
    public static int str2Int(String str) {
        return new BigInteger(str.trim()).intValue();
    }

    public static long str2Long(String str) {
        return new BigInteger(str.trim()).longValue();
    }

    public static boolean isValidNumStr(String str) {
        try {
            str2Long(str.trim());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 获取下一数字 如传入000001,返回000002
     *
     * @param str
     *            当前数字
     * @return
     */
    public static String nextNum(String str) {
        long num = str2Long(str.trim()) + 1;
        String numStr = String.valueOf(num);
        return padString(numStr, str.trim().length(), '0');
    }

    /**
     * 获取下一个奇数或者偶数数字 如传入000001,返回000003。 00002，返回00004
     *
     * @param str
     *            当前数字
     * @return
     */
    public static String nextTwoNum(String str) {
        long num = str2Long(str.trim()) + 2;
        String numStr = String.valueOf(num);
        return padString(numStr, str.trim().length(), '0');
    }

    public static List<String> getServerAddresses() {
        try {
            List<String> ret = new ArrayList<String>();
            Enumeration<NetworkInterface> nis = NetworkInterface
                    .getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    InetAddress ia = ias.nextElement();
                    ret.add(ia.getHostAddress());
                }
            }
            return ret;
        } catch (Exception ex) {
            return new ArrayList<String>();
        }
    }


    /**
     * 获取浏览器版本信息
     *
     * @param request
     * @return
     */
    public static String getBrowerVersion(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    /**
     * 获取客户端ip
     */
    public static String getIp(HttpServletRequest request) {
        String ipaddress = "";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String[] ips = ip.split(",");
        if (ips.length > 1) {
            ipaddress = ips[0];

        } else {
            ipaddress = ip;
        }
        return ipaddress;
    }

    /**
     * 读取coocke值
     *
     * @param key
     * @param request
     * @return
     */
    public static String getCookieByKey(String key, HttpServletRequest request){
        String value = "";
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (key.equalsIgnoreCase(c.getName())) {
                        value = c.getValue();
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("读取Cookie失败");
        }
        return value;
    }

    /**
     * 将字符编码转换成ISO8859-1码
     */
    public static String toISO8859_1(String str)
            throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            // 用新的字符编码生成字符串
            return new String(bs, "ISO8859-1");
        }
        return null;
    }

    /**
     * 将字符编码转换成GB2312码
     */
    public static String toGB2312(String str)
            throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            // 用新的字符编码生成字符串
            return new String(bs, "GB2312");
        }
        return null;
    }

    /*
     * 检查手机号码是否189的号
     */
    public static boolean validator189Mobile(String mobileNo) {
        String regex = "^(18[9])\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobileNo);
        return matcher.matches();
    }

    /***
     * 检查手机号码是否符合格式
     *
     * @param mobileNo
     * @return
     */
    public static boolean validatorMobile(String mobileNo) {
        // 中国移动134.135.136.137.138.139.150.151.152.157.158.159.187.188.147段（中国移动TD数据卡）
        // 中国联通130.131.132.155.156.185.186
        // 中国电信133.153.180.189.1349段（原中国卫通卫星电话）
        String regex = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|17[0-9]|18[0-9])\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobileNo);
        return matcher.matches();
    }

    /**
     * @Description:验证输入字符串为电话，包括手机号码和固话
     * @param phone
     * @return
     * @return: boolean
     * @date: 2013-5-10 上午09:30:07
     * @version V1.0
     */
    public static boolean validatorPhone(String phone) {
        String regexPhone = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$";
        Pattern patternPhone = Pattern.compile(regexPhone);
        Matcher matcherPhone = patternPhone.matcher(phone);
        return matcherPhone.matches() || validatorMobile(phone);
    }

    /**
     * 校验是否为纯数字
     * */
    public static boolean validatorNum(String str){
        String regex = "^[0-9]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    /*
     * 生成缩略图 width 缩略图宽度 height 缩略图高度 path 被缩略图片所在路径 fileName被缩略图片的文件名
     */
    public static void changePhotoPixel(int width, int height, String filePath,
                                        String newPath) {

        // 读入内存
        BufferedImage bi = null;
        try {

            if (filePath.indexOf(".") == -1)
                return;

            bi = ImageIO.read(new File(filePath));

            // 原始宽、高
            int originalWidth = bi.getWidth();
            int originalHeight = bi.getHeight();
            // 宽、高比,默认1，即新宽、高和原始宽、高一样
            double ratio = 1;

            // 原始宽、高比，最终将维持该比例
            double originalRatio = (double) originalWidth / originalHeight;

            // 文件后缀名
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            String newFileType = "jpg";
            if (fileType.equals("png") || fileType.equals("PNG")) {
                newFileType = "png";
            }

            // 如果图片宽度或者高度超出给定范围
            if (originalWidth > width || originalHeight > height) {
                if (width < (int) (Math.floor(height * originalRatio))) {
                    // 以宽度为准，高度自动，维持原始比例
                    ratio = (double) width / originalWidth;
                } else {
                    // 以高度为准，宽度自动，维持原始比例
                    ratio = (double) height / originalHeight;
                }
            }

            AffineTransformOp op = new AffineTransformOp(
                    AffineTransform.getScaleInstance(ratio, ratio), null);
            Image newImage = op.filter(bi, null);
            try {
                ImageIO.write((BufferedImage) newImage, newFileType, new File(
                        newPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能:隐藏电话号码中间四位
     *
     * @param phone
     * @return
     */
    public static String hidMid4Nums(String phone) {
        if (phone != null && (phone.length() > 4)) {
            int mid = (int) phone.length() / 2;
            return phone.substring(0, mid - 2) + "****"
                    + phone.substring(mid + 2);
        }
        return phone;
    }

    // 处理 逗号分割开号码分段(隐藏电话号码中间四位)
    public static String hidMid4NumsForSection(String phone) {
        String phoneArray[] = phone.split(",");
        String retVal = "";
        for (int i = 0; i < phoneArray.length; i++) {
            if (i + 1 == phoneArray.length) {
                retVal += hidMid4Nums(phoneArray[i]);
            } else {
                retVal += hidMid4Nums(phoneArray[i]) + ",";
            }
        }
        return retVal;
    }

    /**
     * 根据隐藏样式隐藏电话号码
     * @param phones 需要隐藏的电话号码
     * @param hideStyle 隐藏的格式
     * @return
     * 若hideStyle为空，默认隐藏电话号码中间四位，否则按照hideStyle隐藏
     */
    public static String hidPhonesByStyle(String phones, String hideStyle) {
        if(phones == null || isNullString(phones.trim())){
            return "";
        }
        StringBuffer retVal = new StringBuffer();
        if(isNullString(hideStyle)){
            return hidMid4NumsForSection(phones);
        }else{
            String[] styles = hideStyle.split(";");
            String[] phoneArray = phones.split(",");
            for (int i = 0; i< phoneArray.length;i++) {
                if(!isNullString(phoneArray[i]) && phoneArray[i].trim().length() == 11){
                    char[] charArray = phoneArray[i].trim().toCharArray();
                    if(styles != null && styles.length >0){
                        for (String index : styles[0].split(",")) {
                            if(!isNullString(index)){
                                charArray[Integer.parseInt(index)] = '*';
                            }
                        }
                    }
                    for (char c : charArray) {
                        retVal.append(c);
                    }
                }else if(styles.length == 2){
                    int startIndex = phoneArray[i].trim().length() - 8;
                    startIndex = startIndex>0?startIndex:0;
                    char[] charArray = phoneArray[i].trim().substring(startIndex).toCharArray();
                    for (String index : styles[1].split(",")) {
                        if(!isNullString(index) && (charArray.length > Integer.parseInt(index))){
                            charArray[Integer.parseInt(index)] = '*';
                        }
                    }
                    retVal.append(phoneArray[i].trim().substring(0,startIndex));
                    for (char c : charArray) {
                        retVal.append(c);
                    }
                }else{
                    retVal.append(phoneArray[i].trim());
                }
                if (i + 1 != phoneArray.length){
                    retVal.append(",");
                }

            }
        }
        return retVal.toString();
    }

    /**
     * pre:前缀
     * 返回pre+yyyy-MM-dd字符串的MD5字节数组
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * */
    public static byte[] md5(String pre) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        // 输入的字符串转换成字节数组
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        byte[] inputByteArray = (pre.concat(dateStr)).getBytes("UTF8");

        // inputByteArray是输入字符串转换得到的字节数组
        messageDigest.update(inputByteArray);

        // 转换并返回结果，也是字节数组，包含16个元素
        return messageDigest.digest();
    }

    /**直接返回ihkapp_web+yyyy-MM-dd字符串的MD5 16进制串
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException */
    public static String appMD5HexStr() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return hex(md5("ihkapp_web"));
    }

    /**
     * 字符串参数取对应值
     * @param key
     * @param formatStr 格式：1:1,2:2
     * @return value
     */
    public static String getValByFormatStr(String key, String formatStr){
        String[] values = formatStr.split(",");
        try {
            for (int i = 0; i < values.length; i++) {
                if(values[i].split(":")[0].equals(key)){
                    return values[i].split(":")[1];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("getValByFormatStr():" + e.getMessage());
        }
        return "";
    }

    //生成随机位数（含数字和字母）
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        // 参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }


    /**
     * 网络红包流水编号自动生成工具
     * @author xiawk
     * @param sno: 你传入的编号如2016022600001
     * @return id : 返回的下一个编号 2016022600002
     */
    public static synchronized String getNextNumber(String sno) {
        String id = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        if (sno == null) {
            id = formatter.format(date) + "0001";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                sb.append("0");
            }
            DecimalFormat df = new DecimalFormat("0000");
            id = formatter.format(date) + df.format(1 + Integer.parseInt(sno.substring(8, 12)));
        }
        return id;
    }

    //数据库字段转换成实体名称，小写开头
    public static String columnToProperty(String column){
        return columnToProperty(column,true);
    }

    //数据库字段转换成实体名称，开头大小写由firstLowerCase决定
    public static String columnToProperty(String column, boolean firstLowerCase) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (column == null || column.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!column.contains("_")) {
            // 不含下划线，仅将首字母小写
            return column.substring(0, 1).toLowerCase() + column.substring(1);
        } else {
            // 用下划线将原始字符串分割
            String[] columns = column.split("_");
            for (String columnSplit : columns) {
                // 跳过原始字符串中开头、结尾的下换线或双重下划线
                if (columnSplit.isEmpty()) {
                    continue;
                }
                // 处理真正的驼峰片段
                if (result.length() == 0 && firstLowerCase) {
                    // 第一个驼峰片段，全部字母都小写
                    result.append(columnSplit.toLowerCase());
                } else {
                    // 其他的驼峰片段，首字母大写
                    result.append(columnSplit.substring(0, 1).toUpperCase()).append(columnSplit.substring(1).toLowerCase());
                }
            }
            return result.toString();
        }
    }

    //实体字段名称转化为数据库字段
    public static String propertyToColumn(String property){
        if (property == null || property.isEmpty()){
            return "";
        }
        StringBuilder column = new StringBuilder();
        column.append(property.substring(0,1).toLowerCase());
        for (int i = 1; i < property.length(); i++) {
            String s = property.substring(i, i + 1);
            // 在小写字母前添加下划线
            if(!Character.isDigit(s.charAt(0)) && s.equals(s.toUpperCase())){
                column.append("_");
            }
            // 其他字符直接转成小写
            column.append(s.toLowerCase());
        }
        return column.toString();
    }

    //返回开始时间和结束时间的Str表达
    public static String startEndDateToString(Date startDate, Date endDate){
        if(startDate!=null||endDate!=null){
            if(startDate==null){
                return "至"+date2DateTimeString(endDate)+"为止";
            }else if(endDate==null){
                return "自"+date2DateTimeString(startDate)+"开始";
            }else{
                return date2DateTimeString(startDate)+" - "+date2DateTimeString(endDate);
            }
        }else{
            return "";
        }
    }

    //算出两个数的比值
    public static double getRate(int numerator, int denominator){
        double _percent  = 0.00;
        double _numerator = numerator;
        double _denominator = denominator;
        if(denominator>0){
            _percent = _numerator/_denominator;
        }
        return _percent;
    }

    //把小数用百分比表示
    public static String doubleToPercentString(double decimal){
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        return decimalFormat.format(decimal);
    }

    //算出两个数的比值，并用百分比表示
    public static String getPercentageRateString(int numerator, int denominator){
        return doubleToPercentString(getRate(numerator,denominator));
    }

    //获取amr音频文件时长
    public static int getAmrDuration(File file) throws IOException {
        long duration = -1;
        int[] packedSize = { 12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0, 0, 0 };
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            long length = file.length();//文件的长度
            int pos = 6;//设置初始位置
            int frameCount = 0;//初始帧数
            int packedPos = -1;

            byte[] datas = new byte[1];//初始数据值
            while (pos <= length) {
                randomAccessFile.seek(pos);
                if (randomAccessFile.read(datas, 0, 1) != 1) {
                    duration = length > 0 ? ((length - 6) / 650) : 0;
                    break;
                }
                packedPos = (datas[0] >> 3) & 0x0F;
                pos += packedSize[packedPos] + 1;
                frameCount++;
            }
            duration += frameCount * 20;//帧数*20
        } finally {
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }
        return (int) (duration / 1000);
    }

    /**
     * 获取WAV音频时长
     * @param path
     * @return
     */
    public static int getWavDuration(String path){
        int length = 0;
        try {
            File file = new File(path);
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip.open(ais);
            length = (int)(clip.getMicrosecondLength() / 1000000D);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 获取本地磁盘大小
     * @return
     */
    public static List<Map> getHardDisk(){
        List<Map> list = new ArrayList();
        File[] roots = File.listRoots();
        double constm = 1024 * 1024;
        for (File file : roots) {
            Map map = new HashMap();
            map.put("path",file.getPath());
            map.put("size",doubleFormat(file.getFreeSpace()/constm));
            list.add(map);
        }
        return list;
    }

    private static String doubleFormat(double d){
        DecimalFormat df = new DecimalFormat("0.##");
        return df.format(d);
    }

    // 判断网络状态
    public static boolean isConnect(String ip) {
        if(StringUtil.isEmpty(ip)){
            return false;
        }
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("ping " + ip);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            isr.close();
            br.close();

            if (null != sb && !sb.toString().equals("")) {
                if (sb.toString().indexOf("TTL") > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获取录音文件物理路径
     * @param number
     * @return
     */
    public static String getRecordFilePath(String number){

        if(number.equals("0")){
            return "/cloudcall_file/rec_20050/OUT/";
        }else if(number.equals("1")){
            return "/cloudcall_file/rec_20051/OUT/";
        }else{
            return "";
        }

/*
		if(number.equals("0")){
			return "x:/OUT/";
		}else if(number.equals("1")){
			return "y:/OUT/";
		}else{
			return "";
		}
*/
    }

}
