package com.gohoc.xiupuling.callback;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public interface OnItemCallbackListener {
    /**
     * @param fromPosition 起始位置
     * @param toPosition 移动的位置
     */
    void onMove(int fromPosition, int toPosition);
    void onSwipe(int position);
}

