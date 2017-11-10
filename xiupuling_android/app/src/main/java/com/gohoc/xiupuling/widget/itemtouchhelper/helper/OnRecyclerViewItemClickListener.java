package com.gohoc.xiupuling.widget.itemtouchhelper.helper;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/5/12 0012
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description 自定义dialog集类
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class OnRecyclerViewItemClickListener implements RecyclerView.OnItemTouchListener {
    private final GestureDetectorCompat gestureDetectorCompat;
    private RecyclerView recyclerView;

    public OnRecyclerViewItemClickListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        gestureDetectorCompat = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childViewUnder = recyclerView.findChildViewUnder(e.getX(), e.getY());//返回指定位置的View
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(childViewUnder);
                onItemClick(childViewHolder);
            }
            return  true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View childViewUnder = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(childViewUnder);
                onLoncClick(childViewHolder);
            }
        }
    }

    public void onLoncClick(RecyclerView.ViewHolder viewHolder){}
    public void onItemClick(RecyclerView.ViewHolder viewHolder){}
}
