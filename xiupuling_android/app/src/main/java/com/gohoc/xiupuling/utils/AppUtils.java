package com.gohoc.xiupuling.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.BuildConfig;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//跟App相关的辅助类
public class AppUtils {

    public static String getTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = simFormat.format(date);
        return dateString;
    }

    private AppUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    public static String getmediaTime(String filePath) {
        String timelong = "";
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {

                }

            });
            timelong = (int) Math.ceil((mediaPlayer.getDuration() / 1000)) + "''";//转换为秒 单位为''
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        } catch (Exception e) {
        }
        return timelong;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        /*try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }*/
        return BuildConfig.VERSION_NAME;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        /*try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }*/
        return BuildConfig.VERSION_CODE;
    }


    /**
     * 判断系统中是否安装某种软件
     */
    public static boolean CheckApp(Context context, String appString) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> infoList = pm.getInstalledPackages(PackageManager.GET_SERVICES);
        for (PackageInfo info : infoList) {
            if (appString.equals(info.packageName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 下载软件
     */
    public static void DownLoadApp(Context context, String appString) {
        Uri uri = Uri.parse("market://details?id=" + appString);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * 跳转界面
     */
    public static void startActivity(Activity currentActivity, Class<?> nextActivity, Bundle bundle) {
        Intent intent = new Intent(currentActivity, nextActivity);
        if (bundle != null) {
            intent.putExtra("intent_data", bundle);
        }
        currentActivity.startActivity(intent);
    }

    public static final String ENCODING = "UTF-8";


    /**
     * 获取设备唯一id
     */
    public static String getDeviceId(Context context) {
        String deviceId = "";
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        } else {
            TelephonyManager TelephonyMgr = (TelephonyManager) context
                    .getSystemService(context.TELEPHONY_SERVICE);
            deviceId = TelephonyMgr.getDeviceId();
        }
        return deviceId;
    }

    public static void installApk(Context context,String urlString) {
        File apkfile = new File(urlString);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        context.startActivity(i);
    }

}
