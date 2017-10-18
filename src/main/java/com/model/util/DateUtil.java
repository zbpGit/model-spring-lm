package com.model.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/23.
 */
public class DateUtil {
    /**
     * 日期转换成字符串
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取付费后的时间
     * @param time
     * @return
     */
    public static String Delay(Integer time){
        long curren = System.currentTimeMillis();
        curren += (time*60) * 60 * 1000;
        Date da = new Date(curren);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(da);
    }

    public static String Time(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

    public static String Stop(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");//设置日期格式
        return df.format(new Date());
    }
    /**
     * 获取网上当前时间
     * @return
     */
    public static String date(){
        long curren = System.currentTimeMillis();
        curren += 60 * 1000;
        Date da = new Date(curren);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(da);
    }

    public static long Time(String Upto){
        String date = date();
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long between = 0;
        try {
            Date begin = dfs.parse(Upto);
            Date end = dfs.parse(date);
            between = ( begin.getTime()-end.getTime());// 得到两者的毫秒数
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        long hour = (between / ( 60 * 1000));
        return hour;
    }
    /*充值续费后的时间*/
    public static String VtRenew(String date,Integer time){
        Date dt = StrToDate(date);
        long lSysTime1 = dt.getTime();
        lSysTime1 += (time*60) * 60 * 1000;
        Date da = new Date(lSysTime1);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(da);
    }
    /*获取服务器当前时间*/
    public static String servicer(){
        String ddate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        return ddate;
    }

    public static String DateDouble(String date,Double time){
        Date dt = StrToDate(date);
        long lSysTime1 = dt.getTime();
        lSysTime1 += (long)(time*60) * 60 * 1000;
        Date da = new Date(lSysTime1);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(da);
    }

}
