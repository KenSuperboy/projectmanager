package com.gohoc.xiupuling.utils;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/6/10 0010
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class ViewUtils {


    private static long lastClickTime;

    private static final int TOUCH_OFFSET = 20;     // 触摸区域（左、上、右、下）的偏移量。数值越大，触摸区域越大

    /**
     * 防止快速点击
     *
     * @return
     */
    public static boolean isFastDoubleClick(long spaceTime) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (spaceTime < timeD) {
            lastClickTime = time;
            return false;
        }
        lastClickTime = time;
        return true;
    }

    /**
     * 检测是否点击了EditText输入控件之外的区域
     *
     * @param v
     * @param event
     * @return 如果点击的是EditText输入区，返回false,否则返回true
     */
    public static boolean isTouchedViewOutSideEditText(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            return isTouchedViewOutSideView(v, event);
        }
        if (v == null) {
            return false;
        }
        return true;
    }

    /**
     * 检测是否点击了EditText输入控件之外的区域
     *
     * @param v
     * @param event
     * @return 如果点击的是EditText输入区，返回false,否则返回true
     */
    public static boolean isTouchedViewOutSideView(View v, MotionEvent event) {
        if (v != null) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left - TOUCH_OFFSET && event.getX() < right + TOUCH_OFFSET &&
                    event.getY() > top - TOUCH_OFFSET && event.getY() < bottom + TOUCH_OFFSET) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public static void setListViewHeight(ListView listview, int num) {
        ListAdapter listAdapter = listview.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int itemHeight = 0;
        int count = listAdapter.getCount() > num ? num : listAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, listview);
            try {
                listItem.measure(0, 0);
                itemHeight = listItem.getMeasuredHeight();
            } catch (Exception e) {
                e.printStackTrace();
            }
            totalHeight += itemHeight;
        }
        ViewGroup.LayoutParams params = listview.getLayoutParams();
        params.height = totalHeight + (listview.getDividerHeight() * (count - 1));
        listview.setLayoutParams(params);
    }
}
