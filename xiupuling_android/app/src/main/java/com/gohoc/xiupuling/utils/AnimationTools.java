package com.gohoc.xiupuling.utils;

import android.view.animation.TranslateAnimation;

/**
 * Created by wjj on 2017/10/11.
 */
public class AnimationTools {

    public static TranslateAnimation getTranslateAnimation(int fromX,int toX,int fromY,int toY,long time)
    {
        TranslateAnimation translateAnimation=new TranslateAnimation(fromX,toX,fromY,toY);
        translateAnimation.setDuration(time);
        return translateAnimation;
    }
}
