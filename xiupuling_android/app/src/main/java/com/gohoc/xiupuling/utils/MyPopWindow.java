package com.gohoc.xiupuling.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.gohoc.xiupuling.R;


/**
 * Created by 28713 on 2017/4/19.
 */

public class MyPopWindow extends PopupWindow {


    public MyPopWindow(Context context, View vp) {
        //设置SelectPicPopupWindow弹出窗体的宽、高
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //30%黑色透明背景
        this.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.transparent_mask)));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.mypopwindow_anim_style);
        //填充布局
        this.setContentView(vp);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);


    }
}
