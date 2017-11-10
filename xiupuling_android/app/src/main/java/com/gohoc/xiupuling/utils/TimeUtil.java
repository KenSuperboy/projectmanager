package com.gohoc.xiupuling.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hongjun by 2016-3-31
 */
public class TimeUtil {
    public static final String DETAIL_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getTimeFormMillis(long mill, String rules) {
        Date date = new Date(mill);
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(rules);
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    public static String getTimeFormMillis(String mill, String rules) {
        if (mill == null || mill.trim().equals("") || mill.equals("0")) {
            return "";
        }
        long mills = Long.parseLong(mill);
        Date date = new Date(mills);
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(rules);
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    public static long getMillisFormTime(String mill, String rules) {
        if (mill == null || mill.trim().equals("") || mill.equals("0")) {
            return 0;
        }
        long time = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(rules);
            Date parse = sdf.parse(mill);
            time = parse.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }


}
