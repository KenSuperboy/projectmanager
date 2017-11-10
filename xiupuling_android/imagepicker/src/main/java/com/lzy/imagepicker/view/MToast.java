package com.lzy.imagepicker.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.R;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/7/20 0020
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class MToast extends Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     * or {@link Activity} object.
     */
    private Context context;

    public MToast(Context context,String tex) {
        super(context);
        this.context = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        TextView textView = (TextView) inflate.findViewById(R.id.text);
        textView.setText(tex);
        setGravity(Gravity.CENTER, 0, 0);
        setView(inflate);
    }

    @Override
    public void setView(View view) {
        super.setView(view);
    }
}
