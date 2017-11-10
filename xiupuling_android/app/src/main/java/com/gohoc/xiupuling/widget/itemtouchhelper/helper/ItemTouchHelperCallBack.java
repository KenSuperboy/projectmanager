package com.gohoc.xiupuling.widget.itemtouchhelper.helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

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
public class ItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    private ItemTouchMoment itemTouchMoment;

    public ItemTouchHelperCallBack(ItemTouchMoment itemTouchMoment) {
        this.itemTouchMoment = itemTouchMoment;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if(recyclerView.getLayoutManager() instanceof GridLayoutManager){
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags,swipeFlags);
        }else{
            final int dragFlags = ItemTouchHelper.UP| ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START| ItemTouchHelper.END;
            return makeMovementFlags(dragFlags,swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int targetPosition = target.getAdapterPosition();
        itemTouchMoment.onMove(fromPosition,targetPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        itemTouchMoment.onSwiped(position);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(actionState== ItemTouchHelper.ACTION_STATE_SWIPE){
            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        }else{
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    private Drawable backgroud = null;
    private int bgcolor = -1;

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (backgroud == null && bgcolor == -1) {
                Drawable drawable = viewHolder.itemView.getBackground();
                if (drawable == null) {
                    bgcolor = 0;
                } else {
                    backgroud = drawable;
                }
            }
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setAlpha(1.0f);
        if(backgroud!=null) viewHolder.itemView.setBackgroundDrawable(backgroud);
        if(bgcolor!=-1) viewHolder.itemView.setBackgroundColor(bgcolor);
        //onActionOver()
    }

    public interface ItemTouchMoment{
        void onMove(int fromPosition, int toPosition);
        void onSwiped(int position);
    }
}
