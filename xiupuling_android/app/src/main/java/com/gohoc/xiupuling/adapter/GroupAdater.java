package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;


/**
 * Created by 28713 on 2017/2/14.
 */

public class GroupAdater extends RecyclerView.Adapter<GroupAdater.MyViewHolder> implements Constant {

    private Context context;
    private GroupBean groupBean;
    private OnItemLitener onItemClick;

    public GroupAdater(Context context, GroupBean groupBean) {
        this.context = context;
        this.groupBean = groupBean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_group, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Glide.with(context)
                .load(NetConstant.BASE_USER_RESOURE + groupBean.getData().get(position).getUnion_portrait() + Utils.getThumbnail(200,200))
                // .placeholder(R.mipmap.icon_port_home)
                //.error(R.mipmap.icon_port_home)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.groupIv);
        holder.groupName.setText(groupBean.getData().get(position).getUnion_name() + "(" + groupBean.getData().get(position).getMember_cnt() + ")");
        LogUtil.d("终端信息："+groupBean.getData().get(position).getUnion_brief_info());
        if(TextUtils.isEmpty(groupBean.getData().get(position).getUnion_brief_info())||(groupBean.getData().get(position).getUnion_brief_info() + "")=="null"||(groupBean.getData().get(position).getUnion_brief_info() + "")==null){
            holder.groupInfo.setText( "");
        }else {
            holder.groupInfo.setText(groupBean.getData().get(position).getUnion_brief_info() + "");
        }


        if (null != onItemClick) {
            holder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.swipeMenuLayout.smoothClose();
                    onItemClick.onItemClick(v, position);
                }
            });
            holder.mBehindViews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.swipeMenuLayout.smoothClose();
                    onItemClick.onItemDelClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (null == groupBean)
            return 0;
        return groupBean.getData().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView groupIv;
        TextView groupName, groupInfo;
        LinearLayout content, mBehindViews;
        SwipeMenuLayout swipeMenuLayout;

        public MyViewHolder(View view) {
            super(view);
            groupIv = (ImageView) view.findViewById(R.id.group_pic_iv);
            groupName = (TextView) view.findViewById(R.id.group_name_tv);
            groupInfo = (TextView) view.findViewById(R.id.group_info_tv);

            content = (LinearLayout) view.findViewById(R.id.content);
            mBehindViews = (LinearLayout) view.findViewById(R.id.mBehindViews);

            swipeMenuLayout = (SwipeMenuLayout) view.findViewById(R.id.swipeMenuLayout);
        }
    }
    public void rf(GroupBean groupBean) {
        this.groupBean = groupBean;
        notifyDataSetChanged();
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
