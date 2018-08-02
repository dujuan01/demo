package com.hansight.atom.custom.collector.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by haodeyu on 2017/7/6.
 */
public class DateUtil {
    /*
     * 毫秒转化时分秒毫秒
     */
    public static String formatTime_(long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if(day > 0) {
            sb.append(day+"天");
        }
        if(hour > 0) {
            sb.append(hour+"小时");
        }
        if(minute > 0) {
            sb.append(minute+"分钟");
        }
        if(second > 0) {
            sb.append(second+"秒");
        }
        if(milliSecond > 0) {
            sb.append(milliSecond+"毫秒");
        }
        return sb.toString();
    }

    /*  功能描述：日期转换cron表达式
     * @param flag 1:一天  7：七天
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     * //    0 0 23 1/1 * ?       每天23点       55 56 23 1/7 * ?    每周23：56：55 时分秒
     */
    public static String formatDateToCron(String flag ,String date){
        String cron = "";
        String[] datelines=date.split(":");
        Map map = new HashMap();
        for(int i=0;i<datelines.length;i++){
            map.put(i,datelines[i]);
        }
        if("1".equals(flag)){
            cron = map.get(2) + " " + map.get(1) + " " + map.get(0) + " " + "1/1 * ?";
        }else {
            cron = map.get(2) + " " + map.get(1) + " " + map.get(0) + " " + "1/7 * ?";
        }
        return cron;
    }

    /**
     * @param format
     * @return 根据格式返回时间类型 返回前一天日期
     */
    public static String formatDate(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date().getTime()-1000*60*60*24);
    }

    /**
     * @param format
     */
    public static String formatDateByTime(long time,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }
    /**
     * @return 根据格式返回时间类型 返回前7天的日期
     */
    public static List get7Days(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        long base =1000*60*60*24;
        List list = new ArrayList();
        for(int i=1;i<=7;i++){
            list.add(sdf.format(new Date().getTime()-base*i));
        }
        return list;
    }

    /**
     * 根据循环周期 获取 7天的list
     * @param cycleTime
     * @return
     */
    public static List get7Days(String cycleTime) throws ParseException {
        //String startTime = cycleTime.split("-")[0];
        String endTime = cycleTime.split("-")[1];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //Date startDate = sdf.parse(startTime);
        Date endDate = sdf.parse(endTime);
        long base =1000*60*60*24;
        List list = new ArrayList();
        for(int i=0;i<=6;i++){
            list.add(sdf.format(endDate.getTime()-base*i));
        }
        return list;
    }
    /**
     * 将Date类转换为XMLGregorianCalendar
     * @param date
     * @return
     */
    public static XMLGregorianCalendar dateToXmlDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DatatypeFactory dtf = null;
        try {
            dtf = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
        }
        XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
        dateType.setYear(cal.get(Calendar.YEAR));
        //由于Calendar.MONTH取值范围为0~11,需要加1
        dateType.setMonth(cal.get(Calendar.MONTH)+1);
        dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
        dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
        dateType.setMinute(cal.get(Calendar.MINUTE));
        dateType.setSecond(cal.get(Calendar.SECOND));
        return dateType;
    }


    public  static void main(String[]args){
        try {
            /*Calendar cal = Calendar.getInstance();
            cal.getTimeInMillis();
            System.out.println(cal.getTimeInMillis()+"$$"+new Date().getTime());*/
            //System.out.println(dateToXmlDate(new Date(new Date().getTime()-28800000)));
            /*Calendar cal = Calendar.getInstance();
            System.out.println("local millis = " + cal.getTimeInMillis()); // 等效System.currentTimeMillis() , 统一值，不分时区
            int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
            int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
            // 从本地时间里扣除这些差量，即可以取得UTC时间：
            cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
            long mills = cal.getTimeInMillis();
            System.out.println("UTC = " + mills);
            SimpleDateFormat foo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = foo.format(cal.getTime());
            System.out.println("GMT time= " + time);
            // 从本地时间里扣除这些差量，即可以取得UTC时间：
            cal.add(java.util.Calendar.MILLISECOND, (zoneOffset + dstOffset));
            time = foo.format(cal.getTime());
            System.out.println("Local time = " + time);*/
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(sdf.format(new Date()));;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
