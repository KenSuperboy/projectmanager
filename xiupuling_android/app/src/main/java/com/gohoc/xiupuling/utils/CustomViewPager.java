package com.gohoc.xiupuling.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 28713 on 2017/2/10.
 */


public class CustomViewPager extends ViewPager {
    private boolean itCanSlide = false;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return isItCanSlide();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return isItCanSlide();
    }

    public boolean isItCanSlide() {
        return itCanSlide;
    }

    public void setItCanSlide(boolean itCanSlide) {
        this.itCanSlide = itCanSlide;
    }
}