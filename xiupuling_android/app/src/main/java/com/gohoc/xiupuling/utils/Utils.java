package com.gohoc.xiupuling.utils;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.bean.UserBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.account.LoginActivity;
import com.gohoc.xiupuling.widget.dialog.BaseDialog;
import com.gohoc.xiupuling.widget.dialog.NormalDialog;
import com.orhanobut.logger.Logger;
import com.wuxiaolong.androidutils.library.AppUtils;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 28713 on 2017/2/9.
 */

public class Utils {


    public static void logout(final Context context, boolean isShowCf) {
        if (isShowCf) {
            Utils.dialog(context, "注意", "确认退出账号？", null, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    logout(context, false);
                }
            });
        } else {
            RxRetrofitClient.getInstance(context).logout(new Observer<VCodeBenan>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Utils.toast(context, "请检查网络是否正常");
                    try {
                        throw e;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }

                @Override
                public void onNext(VCodeBenan vCodeBenan) {
                    if (vCodeBenan.getCode() == 1) {
                        LinkedHashMap<String, UserBean> userList = (LinkedHashMap<String, UserBean>) ACache.get(context).getAsObject("userList");
                        ACache.get(context).clear();
                        ACache.get(context).put("userList", userList);
                        ACache.get(context).put("FirstBlood", "FirstBlood");

                        Intent intent = new Intent(context, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }

                }
            });


        }
    }

    public static void dialog(Context context, String title, String message, DialogInterface.OnClickListener cancelListener, DialogInterface.OnClickListener comfimListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("取消", cancelListener);
        builder.setPositiveButton("确定", comfimListener);
        builder.show();

    }


    public static String getUploadKey(String pre, String url) {
        String result = "";
        String times = System.currentTimeMillis() + "";
        String t1 = MD5Utils.getMD5String(getFileName(url));
        String t2 = url.substring(url.lastIndexOf('.') + 1);//文件后缀
        result = pre + times + t1 + "." + t2;

        LogUtil.d("本地的key："+result);
        return result;
    }

    public static String getFileName(String url) {
        Matcher m = Pattern.compile("/.+?$").matcher(url);
        if (m.find()) {
            return url.substring(m.start(), m.end()).replace("/", "");
        } else {
            return "";
        }
    }

    public static void toShop(Context context) {
        try {
            String mAddress = "market://details?id=" + context.getPackageName();
            Intent marketIntent = new Intent("android.intent.action.VIEW");
            marketIntent.setData(Uri.parse(mAddress));
            context.startActivity(marketIntent);
        } catch (Exception e) {
            Utils.toast(context, "未发现任何应用商店 ");
        }

    }


    public static String doMobile(String m) {
        if (m.length() < 11)
            return m;
        return m.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }


    public static void toast(Context context, String str) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public static int analyzePasswordSecurityLevel(String pwd) {
        int securityLevelFlag = 0;
        if (pwd.length() < 6) {
            return 0;
        } else {

            if (pwd.matches(".*[a-z]+.*")) {
                securityLevelFlag++; // lowercase
                Logger.e("analyzePasswordSecurityLevel:包含小写字母");
            }
            if (pwd.matches(".*[A-Z]+.*")) {
                securityLevelFlag++; // uppercase
                Logger.e("analyzePasswordSecurityLevel:包含大写字母");
            }
            if (pwd.matches(".*[0-9]+.*")) {
                securityLevelFlag++; // digital
                Logger.e("analyzePasswordSecurityLevel:包含数字");
            }
            if (containSpecialChar(pwd)) {
                securityLevelFlag++; // specialcase
                Logger.e("analyzePasswordSecurityLevel:特殊字符");
            }
            return securityLevelFlag;
        }
    }

    public static String analyzePasswordSecurityLevelStr(int pwdleval) {
        if (pwdleval > 3)
            return "高";
        else if (pwdleval > 1)
            return "中";
        return "低";
    }

    public static String getSafeLevelStr(UserBaseBean userBaseBean, int pwdleval) {
        if (pwdleval > 1) {
            if (userBaseBean.getData().getIs_security_question() == 1)
                return "高";
            return "中";
        } else {
            if (userBaseBean.getData().getIs_security_question() == 1)
                return "中";
            return "低";
        }
    }


    private static boolean containSpecialChar(String pwd) {
        if (pwd.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0) {
            //不包含特殊字符
            return false;
        }
        return true;
    }

    public static SystemInfoBean getSystemInfoBean(Context context) {
        SystemInfoBean systemInfoBean = (SystemInfoBean) ACache.get(context).getAsObject("SystemInfoBean");
        return systemInfoBean;
    }


    public static Date addDate(Date times, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(times);
        cal.add(Calendar.DAY_OF_MONTH, day);//取当前日期的前一天.
        return cal.getTime();
    }


    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

    public static <T> void subscribe(Observable<T> observable, Observer<T> observer) {
        Subscription subscribe = observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0.5
     *
     * @param s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int getLength(String s) {
        double valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                // 其他字符长度为0.5
                valueLength += 0.5;
            }
        }
        //进位取整
        return (int) Math.ceil(valueLength);
    }

    /**
     * 获取当前日期是星期几
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取当前日期是星期几
     *
     * @param time 时间文本
     * @param form 时间格式
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(String time, String form) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        DateFormat fmt = new SimpleDateFormat(form);
        Calendar cal = Calendar.getInstance();
        try {
            Date date = fmt.parse(time);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取当前日期是星期几
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekNumberOfDate(Date dt) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }


    public static int countTwoDayWeek(Date startDate, Date endDate) {
        int weeks = 1;
        if (startDate != null && endDate != null) {
            //joda-time
            LocalDate start = new LocalDate(startDate);
            LocalDate end = new LocalDate(endDate);

            int day = Days.daysBetween(start, end).getDays();
            for (int a = 0; a < day; a++) {
                if (weeks == 7)
                    weeks++;
                int w = (start.plusDays(a)).getDayOfWeek();
                if (w == 7)
                    weeks++;
            }
            Logger.e("zcs", weeks);
        }
        return weeks;
    }


    public static String formatDate(Date time, String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        return sdf.format(time);
    }

    /**
     * 字符串转换成日期
     *
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
     * 字符串转换成日期
     *
     * @param r   时间格式
     * @param str 时间
     * @return date
     */
    public static Date StrToDate(String str, String r) {
        SimpleDateFormat format = new SimpleDateFormat(r);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static int StrToDay(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        int day = 0;
        Calendar calendar;
        try {
            date = format.parse(str);
            calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            day = calendar.get(Calendar.DATE);
        } catch (ParseException e) {
            e.printStackTrace();
            Logger.e(e.toString());
        }
        return day;
    }
    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static int StrToMonth(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        int month = 0;
        Calendar calendar;
        try {
            date = format.parse(str);
            calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            month = calendar.get(Calendar.MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
            Logger.e(e.toString());
        }
        return month;
    }

    public static String delTime(String startdate) {
        if (TextUtils.isEmpty(startdate) || startdate.length() < 7)
            return "";
        return startdate.substring(0, 4) + "-" + startdate.substring(4, 6) + "-" + startdate.substring(6, 8);
    }


    public static String getPhotoPathFromContentUri(Context context, Uri uri) {
        String photoPath = "";
        if (context == null || uri == null) {
            return photoPath;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if (isExternalStorageDocument(uri)) {
                String[] split = docId.split(":");
                if (split.length >= 2) {
                    String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        photoPath = Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                }
            } else if (isDownloadsDocument(uri)) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                photoPath = getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                String[] split = docId.split(":");
                if (split.length >= 2) {
                    String type = split[0];
                    Uri contentUris = null;
                    if ("image".equals(type)) {
                        contentUris = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUris = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUris = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    String selection = MediaStore.Images.Media._ID + "=?";
                    String[] selectionArgs = new String[]{split[1]};
                    photoPath = getDataColumn(context, contentUris, selection, selectionArgs);
                }
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            photoPath = uri.getPath();
        } else {
            photoPath = getDataColumn(context, uri, null, null);
        }

        return photoPath;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return null;
    }

    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    public static long getFileSize(String path) throws Exception {
        long size = 0;
        File file = new File(path);
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            //file.createNewFile();
            Logger.e("获取文件大小", "文件不存在!");
        }
        return size;
    }


    /**
     * 根据指定的图像路径和大小来获取缩略图
     * 此方法有两点好处：
     * 1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
     * 2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
     * 用这个工具生成的图像不会被拉伸。
     *
     * @param imagePath 图像的路径
     * @param width     指定输出图像的宽度
     * @param height    指定输出图像的高度
     * @return 生成的缩略图
     */
    private static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                           int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        System.out.println("w" + bitmap.getWidth());
        System.out.println("h" + bitmap.getHeight());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }


    /**
     * 从.mp4的url视频中获取第一帧
     *
     * @param url
     * @return
     */
    public static Bitmap getBitmapFormUrl(String url) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        return bitmap;
    }

    public static int getMonthOfDate(Date time) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int m = cal.get(Calendar.MONTH) + 1;

        return m;
    }


    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";


    public static Date timestampToDate(long timestamp) {
        Date date = new Date(timestamp * 1000);
        Logger.e(formatDate(date, "yyyy-MM-dd"));
        return date;
    }

    public static String timestampToDateStr(long timestamp, String str) {
        Logger.e(formatDate(timestampToDate(timestamp * 1000), "yyyy-MM-dd"));
        return formatDate(timestampToDate(timestamp * 1000), str);
    }

    public static String format(Date date) {
        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

    public static Date getCurrDate() {
        return new Date(System.currentTimeMillis());
    }


    public static void callPhone(final Activity activity, final String tell) {
        NormalDialog.create(activity.getFragmentManager())
                .setNormalDialogListenner(new NormalDialog.NormalDialogListenner() {
                    @Override
                    public void onClick(NormalDialog.NormalType normalType) {
                        if (normalType == NormalDialog.NormalType.OK) {
                            AppUtils.actionCall(activity, tell);
                        }
                    }
                })
                .setmTileText("")
                .setmContent(tell)
                .setmCannelText("取消")
                .setmConfirmText("呼叫")
                .setmOutsideCancel(false)
                .setmBackCancel(true)
                .setmTag("提示")
                .setDialogViewListener(new BaseDialog.DialogViewListener() {
                    @Override
                    public void bindView(View v) {

                    }

                    @Override
                    public void dismiss() {

                    }
                })
                .show();
    }

    public static void toQQ(final Activity activity, final String qq) {
        String url11 = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq + "&version=1";
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url11)));
    }

    public static void toEmail(final Activity activity, final String mailAdress) {
        Uri uri = Uri.parse("mailto:" + mailAdress);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
        // intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分"); // 主题
        // intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分"); // 正文
        activity.startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
    }

    /***
     * 手机号验证
     * @param str
     * @return
     */
    public static boolean isMobile(final String str) {
        if (TextUtils.isEmpty(str))
            return false;
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static boolean checkPW(final String str) {
        if (TextUtils.isEmpty(str) || str.length() < 6 || str.length() > 16)
            return false;
        return true;
    }

    public static boolean isToDy(String tipsTimes) {
        Date a = new Date(Long.parseLong(tipsTimes));
        Date b = getCurrDate();
        return a.getDay() == b.getDay();
    }

    public static String delUrl(String link) {
        StringBuffer tmp = new StringBuffer();
        String links = link.toLowerCase();
        if (links.startsWith("http://") || links.startsWith("https://")) {
            return links;
        } else if (links.startsWith("www."))
            tmp.append("http://").append(link);
        else
            tmp.append("http://www.").append(link);
        return tmp.toString();
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }


    /**
     * 金额格式化
     *
     * @param s   金额
     * @param len 小数位数
     * @return 格式后的金额
     */
    public static String insertComma(String s, int len) {
        if (s == null || s.length() < 1) {
            return "";
        }
        NumberFormat formater = null;
        double num = Double.parseDouble(s);
        if (len == 0) {
            formater = new DecimalFormat("###,###");

        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("###,###.");
            for (int i = 0; i < len; i++) {
                buff.append("#");
            }
            formater = new DecimalFormat(buff.toString());
        }
        return formater.format(num);
    }

    public static String getThumbnail(int w, int h) {

        return "?imageView2/1/w/" + w + "/h/" + h;
    }
}
