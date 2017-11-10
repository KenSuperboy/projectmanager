package com.gohoc.xiupuling.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/6/6 0006
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class MScrollView extends ScrollView {
    public MScrollView(Context context) {
        super(context);
    }

    public MScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
