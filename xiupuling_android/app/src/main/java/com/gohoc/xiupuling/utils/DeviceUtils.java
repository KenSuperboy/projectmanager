package com.gohoc.xiupuling.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

/**
 * Created by sherlock-sky on 2017/2/4.
 */

public class DeviceUtils {

    public static String getUid(Context context) {
        String uid = "";
        try {
            ApplicationInfo appinfo = context.getApplicationInfo();
            uid = appinfo.uid + "";
            Logger.d("!!" + uid);

        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("!!获取uid失败" + e.toString());
        }
        return uid;
    }

    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }
}
