package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupTermianlListBean;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

/**
 * Created by 28713 on 2017/2/14.
 */

public class GroupTerminalOrtherAdater extends RecyclerView.Adapter<GroupTerminalOrtherAdater.MyViewHolder> {

    private Context context;
    private GroupTermianlListBean groupTermianlListBean;
    private OnItemClickLitener onItemClickLitener, onDel;
    private boolean isSwipeEnable = false;

    public GroupTerminalOrtherAdater(Context context, GroupTermianlListBean groupTermianlListBean, boolean isSwipeEnable) {
        this.context = context;
        this.groupTermianlListBean = groupTermianlListBean;
        this.isSwipeEnable = isSwipeEnable;
    }

    public void rf(GroupTermianlListBean groupTermianlListBean) {
        this.groupTermianlListBean = groupTermianlListBean;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_group_arr, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ((SwipeMenuLayout) holder.itemView).setLeftSwipe(isSwipeEnable).setSwipeEnable(isSwipeEnable);

        holder.termianlInfoTv.setText(groupTermianlListBean.getData().get(position).getTerminal_no() + "号机  " + groupTermianlListBean.getData().get(position).getShop_name());
        if (groupTermianlListBean.getData().get(position).getTerm_orientation() == 0)
            holder.og.setImageResource(R.mipmap.icon_term_orientation_h);
        else
            holder.og.setImageResource(R.mipmap.icon_group_add_port);


        if (null != onItemClickLitener) {
            holder.content_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickLitener.onItemClick(v, position);
                }
            });
            holder.content_ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickLitener.onItemLongClick(v, position);
                    return true;
                }
            });
        }
        if (null != onDel) {
            holder.mBehindViews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDel.onItemClick(v, position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (groupTermianlListBean == null)
            return 0;
        return
                groupTermianlListBean.getData().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout content_ll, mBehindViews;
        ImageView og;
        TextView termianlInfoTv;


        public MyViewHolder(View view) {
            super(view);
            termianlInfoTv = (TextView) view.findViewById(R.id.termianl_info_tv);
            og = (ImageView) view.findViewById(R.id.termianl_og_iv);
            content_ll = (LinearLayout) view.findViewById(R.id.content_ll);
            mBehindViews = (LinearLayout) view.findViewById(R.id.mBehindViews);

        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    public OnItemClickLitener getOnDel() {
        return onDel;
    }

    public void setOnDel(OnItemClickLitener onDel) {
        this.onDel = onDel;
    }
}
