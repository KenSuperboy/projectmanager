package com.gohoc.xiupuling.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

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
public class MVidioView extends VideoView {


    private int width;
    private int height;

    public MVidioView(Context context) {
        super(context);
    }

    public MVidioView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MVidioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // TODO: 2017/6/6 0006 重写vidoview 宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 默认高度，为了自动获取到focus
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width;
        // 这个之前是默认的拉伸图像
        if (this.width > 0 && this.height > 0) {
            width = this.width;
            height = this.height;
        }
        setMeasuredDimension(width, height);
    }

    /**
     * 设置视频画面的宽和高
     */
    public void setMeasure(int width, int height) {
        this.width = width;
        this.height = height;
    }



}