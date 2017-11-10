package com.gohoc.xiupuling.callback;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.gohoc.xiupuling.adapter.combinationadapter.CombinationPaixuAdapter;
import com.gohoc.xiupuling.adapter.linkpackage.LiandongbaoPaixuAdapter;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public class OnItemCallbackHelper extends ItemTouchHelper.Callback {
    /**
     * Item是否能被Swipe到dismiss
     * 也就是删除这条数据
     */
    private LiandongbaoPaixuAdapter adapter;
    private CombinationPaixuAdapter mCombinationPaixuAdapter;

    public OnItemCallbackHelper(LiandongbaoPaixuAdapter myAdapter1,CombinationPaixuAdapter mCombinationPaixuAdapter1){
        this.adapter=myAdapter1;
        this.mCombinationPaixuAdapter=mCombinationPaixuAdapter1;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    /**
     * Item长按是否可以拖拽
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * 设置Drag/Swipe的Flag
     * 这里我们把滑动(Drag)的四个方向全都设置上了,说明Item可以随意移动
     * 然后把删除(暂且叫删除/swipe)的方向设置为Start和End,说明可以水平拖动删除
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.RIGHT;
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        /**
         * 回调
         */
        if(adapter!=null){
            adapter.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }else if(mCombinationPaixuAdapter!=null){
            mCombinationPaixuAdapter.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }

        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        /**
         * 回调
         */
        if(adapter!=null){
            adapter.onSwipe(viewHolder.getAdapterPosition());
        }else if(mCombinationPaixuAdapter!=null){
            mCombinationPaixuAdapter.onSwipe(viewHolder.getAdapterPosition());
        }

    }
}
