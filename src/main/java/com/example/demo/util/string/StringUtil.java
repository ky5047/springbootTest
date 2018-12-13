package com.example.demo.util.string;

import java.text.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by 92992 on 2018/12/7.
 */
public class StringUtil extends StringUtilParent {

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNotNull(String str) {
        if (str != null && !"".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断对象是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isNotNull(Object obj) {
        if (obj != null && obj.toString() != null && !"".equals(obj.toString().trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否为空(自动截取首尾空白)
     *
     * @param str 源字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null ? true : ("".equals(str) || str.equals(NULL_STR) || str.equals(null_str));
    }

    /**
     * 判断字符串是否为空
     *
     * @param str  源字符串
     * @param trim 是否截取首尾空白
     * @return
     */
    public static boolean isEmpty(String str, boolean trim) {
        if (trim)
            str = str.trim();
        return str == null ? true : ("".equals(str) || str.equals(NULL_STR) || str.equals(null_str));
    }

    /**
     * @param str   the string need to be parsed
     * @param delim the delimiter to seperate created by zqf at 6/1/2013
     */
    public static String[] parseToArray(String str, String delim) {
        ArrayList arr = new ArrayList();
        StringTokenizer st = new StringTokenizer(str, delim);
        while (st.hasMoreTokens()) {
            arr.add(st.nextToken());
        }
        String[] ret = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            ret[i] = (String) arr.get(i);
        }
        return ret;
    }

    /**
     * replace a old substring with rep in str
     *
     * @param str the string need to be replaced
     * @param old the string need to be removed
     * @param rep the string to be inserted
     * @return string replaced
     */
    public static String replace(String str, String old, String rep) {
        if ((str == null) || (old == null) || (rep == null)) {// if one is null
            // return ""
            return "";
        }
        int index = str.indexOf(old);
        if ((index < 0) || "".equals(old)) { // if no old string found or
            // nothing to replace,return the
            // origin
            return str;
        }
        StringBuffer strBuf = new StringBuffer(str);
        while (index >= 0) { // found old part
            strBuf.delete(index, index + old.length());
            strBuf.insert(index, rep);
            index = strBuf.toString().indexOf(old);
        }
        return strBuf.toString();
    }

    /**
     * 带逗号分隔的数字转换为NUMBER类型
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Number stringToNumber(String str) throws ParseException {
        if (str == null || "".equals(str)) {
            return null;
        }
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        dfs.setMonetaryDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###,###,###.##", dfs);
        return df.parse(str);
    }

    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 用于字符串替换
     *
     * @param target      目标对象 需要替换的字符串
     * @param replacement 要替换的字符串
     * @param value       替换的值
     * @return
     */
    public static String replacement(String target, String replacement, String value) {
        if (target != null)
            return target.replace(replacement, value);
        return null;
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算指定时间与当前时间的差
     *
     * @param date
     * @return
     */
    public static String convDateToString(Date date) {
        Long time = System.currentTimeMillis() - date.getTime();
        Long min = time / 1000 / 60;
        if (min < 5) {
            return "刚刚";
        } else if (min >= 5 && min < 60) {
            return min + "分钟之前";
        } else if (min >= 60 && min < 1440) {
            return min / 60 + "小时之前";
        } else if (min >= 1440 && min < 10080) {
            return min / 60 / 24 + "天之前";
        } else if (min >= 10080 && min < 40320) {
            return min / 60 / 24 / 7 + "周之前";
        } else if (min >= 40320 && min < 525600) {
            return min / 60 / 24 / 7 / 4 + "月之前";
        } else if (min >= 525600) {
            return min / 60 / 24 / 365 + "年之前";
        }
        return null;
    }

    /**
     * @return
     * @description 获取当前服务器日期
     */
    public static String getCurrdate(String formatStr) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        String mDateTime = formatter.format(cal.getTime());
        return mDateTime;
    }

    /**
     * 将Object值转换成Double类型
     *
     * @param value
     * @return
     */
    public static double getDoubleByObj(Object value) {
        if (value == null) {
            return 0;
        }
        return Double.valueOf(String.valueOf(value));
    }

    /**
     * 将Object值转换成Float类型
     *
     * @param value
     * @return
     */
    public static float getFloatByObj(Object value) {
        if (value == null) {
            return 0;
        }
        return Float.valueOf(String.valueOf(value));
    }

    /**
     * 将Object值转换成Integer类型
     *
     * @param value
     * @return
     */
    public static Integer getIntegerByObj(Object value) {
        if (value == null) {
            return 0;
        }
        return Integer.valueOf(String.valueOf(value));
    }

    /**
     * 解析字符串 ---> 去掉字符串中回车、换行、空格
     *
     * @param str 被解析字符串
     * @return String 解析后的字符串
     */
    public static String parse(String str) {
        return str.replaceAll("\n", "").replaceAll("chr(13)", "").replaceAll(" ", "");
    }

    public static int strFormateInt(Object obj) {
        if (isNotNull(obj)) {
            return "是".equals(obj) ? 1 : 0;
        } else {
            return 0;
        }
    }

    /**
     * 获取UUID
     *
     * @return UUID
     */
    public static String getUUID() {

        return (UUID.randomUUID() + "").replaceAll("-", "");
    }

    /**
     * 将字符串转移为ASCII码
     *
     * @param cnStr
     * @return
     */
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            // System.out.println(Integer.toHexString(bGBK[i]&0xff));
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

    /**
     * @param initCode 初始化编码
     * @param length   需要生成编码长度
     * @param ind      地增量
     * @return 递增后的编码
     */
    public static String getNextCode(String initCode, int length, int ind) {
        Integer temp = Integer.parseInt(initCode);
        temp = temp + ind;
        String tempCode = temp.toString();
        int tempLen = 0;
        if (tempCode.length() < length) {
            tempLen = length - tempCode.length();
        }
        for (int i = 0; i < tempLen; i++) {
            tempCode = "0" + tempCode;
        }
        return tempCode;
    }

    public static int switchNumber(String str) {
        char c = str.charAt(0);
        int temp = 0;
        switch (c) {
            // 数值
            case '〇':
            case '零':
                temp = 0;
                break;
            case '一':
                temp = 1;
                break;
            case '二':
                temp = 2;
                break;
            case '三':
                temp = 3;
                break;
            case '四':
                temp = 4;
                break;
            case '五':
                temp = 5;
                break;
            case '六':
                temp = 6;
                break;
            case '七':
                temp = 7;
                break;
            case '八':
                temp = 8;
                break;
            case '九':
                temp = 9;
                break;
            // 单位，前缀是单数字
            case '十':
                temp = 10;
                break;
        }
        return temp;
    }

    /**
     * 中文数字转换为阿拉伯数
     *
     * @param s
     */
    public static int cnNumToInt(String s) {
        int result = 0;
        int yi = 1;// 记录高级单位
        int wan = 1;// 记录高级单位
        int ge = 1;// 记录单位
        char c = s.charAt(0);
        int temp = 0;// 记录数值
        switch (c) {
            // 数值
            case '〇':
            case '零':
                temp = 0;
                break;
            case '一':
                temp = 1 * ge * wan * yi;
                ge = 1;
                break;
            case '二':
                temp = 2 * ge * wan * yi;
                ge = 1;
                break;
            case '三':
                temp = 3 * ge * wan * yi;
                ge = 1;
                break;
            case '四':
                temp = 4 * ge * wan * yi;
                ge = 1;
                break;
            case '五':
                temp = 5 * ge * wan * yi;
                ge = 1;
                break;
            case '六':
                temp = 6 * ge * wan * yi;
                ge = 1;
                break;
            case '七':
                temp = 7 * ge * wan * yi;
                ge = 1;
                break;
            case '八':
                temp = 8 * ge * wan * yi;
                ge = 1;
                break;
            case '九':
                temp = 9 * ge * wan * yi;
                ge = 1;
                break;
            // 单位，前缀是单数字
            case '十':
                ge = 10;
                break;
            case '百':
                ge = 100;
                break;
            case '千':
                ge = 1000;
                break;
            // 高级单位，前缀可以是多个数字
            case '万':
                wan = 10000;
                ge = 1;
                break;
            case '亿':
                yi = 100000000;
                wan = 1;
                ge = 1;
                break;
            default:
                return -1;
        }
        result += temp;
        if (ge > 1) {
            result += 1 * ge * wan * yi;
        }
        return result;
    }

    public static String geneStrAry(String str, String splits) {
        if (StringUtil.isEmpty(str))
            return "";
        String[] ary = str.split(splits);
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < ary.length; i++) {
            sb.append("'");
            sb.append(ary[i]);
            sb.append("'");
            if (i < ary.length - 1)
                sb.append(",");
        }
        return sb.toString();
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? false : str2 == null ? true : str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? false : str2 == null ? true : str1.equalsIgnoreCase(str2);
    }

    /**
     * @param obj 传数值类型的obj
     * @return
     */
    public static String decimalFormat(Object obj) {
        if (null == obj)
            return "";
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(obj);
    }

    /**
     * @param obj    传数值类型的obj
     * @param format
     * @return
     */
    public static String decimalFormat(Object obj, String format) {
        if (null == obj)
            return "";
        DecimalFormat df = new DecimalFormat(format);
        return df.format(obj);
    }

    /**
     * 字符串连接
     */
    public static String strConnect(String... strings) {

        if (strings == null || strings.length == 0) {
            return null;
        }

        if (strings.length == 1) {
            return strings[0];
        }

        StringBuilder stringBuilder = new StringBuilder(strings.length);

        for (String str : strings) {

            if (isEmpty(str))
                continue;

            stringBuilder.append(str);
        }

        return stringBuilder.toString();
    }

    //根据字符串获取部门
    public static String strToDepartment(String str) {
        String department = "";
        if (isEmpty(str)) {
            return department;
        }
        if (str.indexOf("-1-") != -1) {
            department = "一部";
        } else if (str.indexOf("-2-") != -1) {
            department = "二部";
        } else if (str.indexOf("-3-") != -1) {
            department = "三部";
        } else if (str.indexOf("-6-") != -1) {
            department = "六部";
        } else if (str.indexOf("-7-") != -1) {
            department = "七部";
        } else if (str.indexOf("-8-") != -1) {
            department = "八部";
        } else if (str.indexOf("-9-") != -1) {
            department = "九部";
        } else if (str.indexOf("-10-") != -1) {
            department = "十部";
        } else if (str.indexOf("-11-") != -1) {
            department = "十一部";
        } else if (str.indexOf("-12-") != -1) {
            department = "十二部";
        } else if (str.indexOf("-13-") != -1) {
            department = "十三部";
        } else if (str.indexOf("-dg-") != -1) {
            department = "东莞分公司";
        } else if (str.indexOf("-fs-") != -1) {
            department = "佛山分公司";
        }
        return department;
    }

    public static String strRange(String low, String high, String unit) {
        if (StringTools.isNotempty(low) && StringTools.isNotempty(high)) {
            return low + "-" + high + unit;

        } else if (StringTools.isNotempty(low) && StringTools.isEmpty(high)) {
            return low + unit + "以上";
        } else if (StringTools.isEmpty(low) && StringTools.isNotempty(high)) {
            return high + unit + "以下";
        } else {
            return "";
        }
    }

    public static String format(String pattern, Object... arguments) {
        MessageFormat temp = new MessageFormat(pattern);
        return temp.format(arguments);
    }


    /***
     * 检查手机号码是否符合格式
     *
     * @param mobileNo
     * @return
     */
    public static boolean validatorMobile(String mobileNo) {
        //TODO 新开放的号段 中国移动的198、中国联通的166以及中国电信的199
        // 中国移动134.135.136.137

        //.138.139.150.151

        //.152.157.158.159

        //.187.188.147段（中国移动TD数据卡）
        // 中国联通130.131.132.155

        //.156.185.186
        // 中国电信133.153.180.189

        //.1349段（原中国卫通卫星电话）
        String regex = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|17[0-9]|18[0-9]|166|19[8|9])\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobileNo);
        return matcher.matches();
    }


    /***
     * 检查固话区号
     *
     * @param
     * @return
     */
    public static boolean validatorPhoneCode(String areaCode) {

        String[] list = {"10", "22", "23", "020", "0660", "0662", "0663", "0668", "0750", "0751", "0752", "0753", "0754", "0755", "0756", "0757", "0758", "0759", "0760", "0762", "0763", "0766", "0768", "0769", "0770", "0771", "0771", "0772", "0772", "0773", "0774", "0774", "0775", "0775", "0776", "0777", "0778", "0779", "0310", "0311", "0312", "0313", "0314", "0315", "0316", "0317", "0318", "0319", "0335", "0349", "0350", "0351", "0352", "0353", "0354", "0355", "0356", "0357", "0358", "0359", "0470", "0471", "0472", "0473", "0474", "0475", "0476", "0477", "0478", "0479", "0482", "0483", "024", "024", "0411", "0412", "024", "0414", "0415", "0416", "0417", "0418", "0419", "0421", "0427", "0429", "0431", "0432", "0433", "0434", "0435", "0436", "0437", "0438", "0439", "0451", "0452", "0453", "0454", "0455", "0456", "0457", "0458", "0459", "0464", "0467", "0468", "0469", "025", "0510", "0511", "0512", "0513", "0514", "0515", "0516", "0517", "0518", "0519", "0523", "0527", "0570", "0571", "0572", "0573", "0574", "0575", "0576", "0577", "0578", "0579", "0580", "0550", "0551", "0552", "0553", "0554", "0555", "0556", "0557", "0558", "0559", "0561", "0562", "0563", "0564", "0565", "0566", "0591", "0592", "0593", "0594", "0595", "0596", "0597", "0598", "0599", "0790", "0791", "0792", "0793", "0794", "0795", "0796", "0797", "0798", "0799", "0701", "0530", "0531", "0532", "0533", "0534", "0535", "0536", "0537", "0538", "0539", "0543", "0546", "0631", "0632", "0633", "0634", "0635", "0370", "0371", "0372", "0373", "0374", "0375", "0376", "0377", "0378", "0379", "0391", "0391", "0392", "0393", "0394", "0395", "0396", "0398", "027", "0710", "0711", "0712", "0713", "0714", "0715", "0716", "0717", "0718", "0719", "0719", "0722", "0724", "0728", "0728", "0728", "0730", "0731", "0731", "0731", "0734", "0735", "0736", "0737", "0738", "0739", "0743", "0744", "0745", "0746", "0898", "028", "0812", "0813", "0816", "0817", "0818", "0825", "0826", "0827", "0830", "0831", "0832", "028", "0833", "028", "0834", "0835", "0836", "0837", "0838", "0839", "0851", "0852", "0853", "0854", "0855", "0856", "0857", "0858", "0859", "0691", "0692", "0870", "0871", "0872", "0873", "0874", "0875", "0876", "0877", "0878", "0879", "0883", "0886", "0887", "0888", "0891", "0892", "0893", "0894", "0895", "0896", "0897", "029", "0911", "0912", "0913", "0914", "0915", "0916", "0917", "0919", "0930", "0931", "0932", "0933", "0934", "0935", "0935", "0936", "0937", "0937", "0938", "0939", "0941", "0943", "0951", "0952", "0953", "0954", "0955", "0970", "0971", "0972", "0973", "0974", "0975", "0976", "0979", "0901", "0902", "0903", "0906", "0908", "0909", "0990", "0991", "0993", "0994", "0994", "0995", "0996", "0997", "0997", "0998", "0998", "0999", "00852", "00853", "886"};
        for (String s : list) {
            if (s.equals(areaCode)) {
                return true;
            }
        }
        return false;
    }

    // 过滤特殊字符
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}