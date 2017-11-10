package com.gohoc.xiupuling.constant;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.baidu.mapapi.SDKInitializer;
import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.LogUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.VideoPicker;
import com.lzy.imagepicker.view.CropImageView;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sherlock-sky on 2016/12/31.
 */

public class MyApp extends MultiDexApplication implements Constant {
    private static MyApp instance;
    private final String TAG = "MyApp";
    private static Context mContext;
    private ACache mCache;
    private SystemInfoBean systemInfoBean;
    //在自己的Application中添加如下代码

    private static List<Activity> activityList = new ArrayList<>();


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
/*        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher= LeakCanary.install(this);*/
        // Normal app init code...
        inik();
        Logger.i("MyApp:onCreate()");
        // mCache=ACache.get(getContext());
        initImagePicker();


    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);
        imagePicker.setImageLoader(new com.gohoc.xiupuling.utils.ImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        //  imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        VideoPicker videoPicker = VideoPicker.getInstance();
    }


    public void inik() {
        CrashReport.initCrashReport(getApplicationContext(), Constant.BaseConstant.BUGLY_KEY, false);
        // Stetho.initializeWithDefaults(this);
        SDKInitializer.initialize(getApplicationContext());
        // 初始化Looger工具
        LogUtil.init(true);
        //处理Activity回退栈问题

    }

    public static MyApp getInstance() {
        if (null == instance) {
            instance = new MyApp();
        }
        return instance;
    }


    public static Context getmContext() {
        return mContext;
    }

    public static void setmContext(Context mContext) {
        MyApp.mContext = mContext;
    }

    public static List<Activity> getActivityList() {
        return activityList;
    }

    public static void setActivityList(List<Activity> activityList) {
        MyApp.activityList = activityList;
    }

}
