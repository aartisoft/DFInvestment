package com.tyqhwl.jrqh.base;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//时间工具类
public class BaseTime implements Serializable {


    /**
     * 获取当前的时间戳
     *
     * @return
     */
    public static String getTimeStame() {
        //获取当前的毫秒值
        long time = System.currentTimeMillis();
        //将毫秒值转换为String类型数据
        String time_stamp = String.valueOf(time);
        //返回出去
        return time_stamp;
    }

    //获取当天凌晨0点毫秒值
    public static long getTodayZero() {
        Date date = new Date();
        long l = 24 * 60 * 60 * 1000;
        //每天的毫秒数 //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），
        // 理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
        // 减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime() % l) - 8 * 60 * 60 * 1000);
    }


    //获取当天23:59点毫秒值
    public static long getTodayEnd() {
        Calendar calendar2 = Calendar.getInstance();

        calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
                23, 59, 59);
        Date endOfDate = calendar2.getTime();

        return endOfDate.getTime();
    }

    //检测某一时间是否为今天
    public static boolean getIsToday(long time) {
        if (getTodayZero() > time) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 毫秒转分
     *
     * @param ms
     * @return
     */
    public static String msToM(int ms) {
        int seconds = ms / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;

        String m = null;
        String s = null;

        if (minutes == 0 && seconds == 0) seconds = 1;

        if (minutes < 10) m = "0" + minutes;
        else
            m = "" + minutes;

        if (seconds < 10) s = "0" + seconds;
        else
            s = "" + seconds;

        int time = (new Integer(m) * 60) + new Integer(s);

        return m;
    }


    /**
     * @param
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss, int type) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;


        switch (type) {
            case 0:
                return days + " days " + hours + " hours " + minutes + " minutes "
                        + seconds + " seconds ";
            case 1:
                return days + "";

            case 2:
                return hours + "";

            case 3:
                return minutes + "";

            case 4:
                return seconds + "";

            default:
                return "";

        }

    }

    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
//        time1.setText("Date获取当前日期时间"+simpleDateFormat.format(date));
        return simpleDateFormat.format(date);
    }
}
