package com.gohoc.xiupuling.widget.itemtouchhelper.utils;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/5/12 0012
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description 自定义dialog集类
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class VibratorUtil {

    /**
     * final Activity activity  ：调用该方法的Activity实例
     * long milliseconds ：震动的时长，单位是毫秒
     * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static void Vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }
    public static void Vibrate(final Activity activity, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

}
