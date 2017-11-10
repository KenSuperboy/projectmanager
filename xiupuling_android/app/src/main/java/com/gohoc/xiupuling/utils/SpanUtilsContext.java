package com.gohoc.xiupuling.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by 28713 on 2017/7/13.
 */

public class SpanUtilsContext {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private SpanUtilsContext() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        SpanUtilsContext.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
}
