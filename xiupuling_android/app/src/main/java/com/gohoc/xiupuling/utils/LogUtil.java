package com.gohoc.xiupuling.utils;

import android.util.Log;

import com.gohoc.xiupuling.constant.Constant;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by 28713 on 2017/2/28.
 */

public class LogUtil {
    /**
     * 初始化log工具，在app入口处调用
     *
     * @param isLogEnable 是否打印log
     */
    public static void init(boolean isLogEnable) {
        Logger.init("LogHttpInfo")
                .hideThreadInfo()
                .logLevel(isLogEnable ? LogLevel.FULL : LogLevel.NONE)
                .methodOffset(2);
    }

    public static void d(String message) {
        //Logger.d(message);
        if(Constant.BaseConstant.isTest){
            Log.d("TAG",message);
        }else {

        }
    }

    public static void i(String message) {
        Logger.i(message);
    }

    public static void w(String message, Throwable e) {
        String info = e != null ? e.toString() : "null";
        Logger.w(message + "：" + info);
    }

    public static void e(String message, Throwable e) {
        Logger.e(e, message);
    }

    public static void json(String json) {
        Logger.json(json);
    }
}
