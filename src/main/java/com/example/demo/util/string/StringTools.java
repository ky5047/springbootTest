package com.example.demo.util.string;

import java.util.List;
import java.util.UUID;

/**
 * Created by 92992 on 2018/12/7.
 */
public class StringTools {

    public static String ORACLE = "oracle";

    public static String MYSQL = "mysql";



    /**
     * 验证一个字符串或者数组是空
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            return list.size() == 0;
        } else {
            return obj.toString().trim().length() == 0;
        }
    }

    /**
     * 验证一个字符串或者数组不为空
     *
     * @param obj
     * @return
     */
    public static boolean isNotempty(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            return list.size() > 0;
        } else {
            return obj.toString().trim().length() > 0;
        }
    }


    public static <T> T notSetValue(T t,T t1){
        if(t==null){
            return t1;
        }else{
            return t;
        }

    }


    /**
     * 获取分页语句
     */
    public static String getPagingSql(String paSql, int page, int pageSize,String type) {


    	/*oracle     */
        if(type.equals(StringTools.ORACLE)) {
            StringBuffer sql = new StringBuffer("select * from( select ttt.*,rownum rn from( ");
            sql.append(paSql);
            int pages = ((page - 1) * pageSize);
            int pageSizes = page * pageSize;
            sql.append("  ) ttt where rownum<= ");
            sql.append(pageSizes);
            sql.append(" ) where rn > ");
            sql.append(pages);
            return sql.toString();
        }else if(type.equals(StringTools.MYSQL)) {
            //mysql
            StringBuffer sql = new StringBuffer("select * from (" + paSql + ") as ttt  LIMIT " + (page - 1) * pageSize + "," + pageSize);
            return sql.toString();
        }else{
            StringBuffer sql = new StringBuffer("select * from (" + paSql + ") as ttt  LIMIT " + (page - 1) * pageSize + "," + pageSize);
            return sql.toString();
        }
    }

    public static boolean isNotNull(String str) {
        if (str != null && !"".equals(str.trim())) {
            return true;
        } else {
            return false;
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


    /**
     * 用于Es查询出来的数据自定义分页，值得注意的是page是默认从0开始的，但是传进来要从1开始（跟页面同步）
     * @param list
     * @param page
     * @param pageSize
     * @param <T>
     * @return
     */

    public static <T>List<T> esPageForList(List<T> list,int page,int pageSize){
        if(StringTools.isEmpty(list)) {
            return list;
        }
        int to = 0;
        if(list.size()<page*pageSize){
            to=list.size();
        }else{
            to=page*pageSize;
        }
        //自定义分页
        list = list.subList((page-1)*pageSize,to);
        return list;
    }

    public static long generateTimestamp(int page, Long timestamp) {
        if(page == 1) {
            return System.currentTimeMillis();
        } else if(timestamp != null) {
            return timestamp.longValue();
        } else {
            return 0L;
        }
    }

    public static boolean isNullString(String str) {
        return str == null || str.trim().isEmpty();
    }
}
