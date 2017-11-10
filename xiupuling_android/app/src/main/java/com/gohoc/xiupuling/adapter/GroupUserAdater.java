package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.BusinessTypeBean;
import com.gohoc.xiupuling.bean.GroupUserBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.utils.Utils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

/**
 * Created by 28713 on 2017/2/14.
 */

public class GroupUserAdater extends RecyclerView.Adapter<GroupUserAdater.MyViewHolder> {

    private Context context;
    private GroupUserBean groupUserBean;
    private OnItemLitener onItemClick;

    public boolean isSwp() {
        return isSwp;
    }

    private boolean isSwp ;

    public void setSwp(boolean swp) {
        isSwp = swp;
    }

    public GroupUserAdater(Context context, GroupUserBean groupUserBean, boolean isSwp) {
        this.context = context;
        this.groupUserBean = groupUserBean;
        this.isSwp=isSwp;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_group_user, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if(position==0 || !isSwp)
        {
            ((SwipeMenuLayout) holder.itemView).setLeftSwipe(false).setSwipeEnable(false);
        }else
            ((SwipeMenuLayout) holder.itemView).setLeftSwipe(true).setSwipeEnable(true);

        holder.group_user_name_tv.setText(groupUserBean.getData().get(position).getNick_name()+"");
        holder.group_user_count_tv.setText(groupUserBean.getData().get(position).getTerminal_cnt()+"台");
        Glide.with(context)
                .load(Constant.NetConstant.BASE_USER_RESOURE+groupUserBean.getData().get(position).getPortrait_url()+ Utils.getThumbnail(100,100))
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                  .error(R.mipmap.icon_my_port_group_1)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.cover);

        if (null != onItemClick) {
            holder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemClick(v, position);
                }
            });
            holder.mBehindViews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemDelClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return groupUserBean.getData().size();
    }

    public void rf(GroupUserBean groupUserBeans) {
        this.groupUserBean=groupUserBeans;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView group_user_name_tv,group_user_count_tv;
        ImageView cover;
        LinearLayout content, mBehindViews;


        public MyViewHolder(View view) {
            super(view);
            group_user_name_tv = (TextView) view.findViewById(R.id.group_user_name_tv);
            group_user_count_tv = (TextView) view.findViewById(R.id.group_user_count_tv);
            cover= (ImageView) view.findViewById(R.id.cover);
            content = (LinearLayout) view.findViewById(R.id.content);
            mBehindViews = (LinearLayout) view.findViewById(R.id.mBehindViews);
        }
    }

    public OnItemLitener getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemLitener onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemLitener {
        void onItemClick(View view, int position);
        void onItemDelClick(View view, int position);
    }
}
