package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupSearchBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;
import com.orhanobut.logger.Logger;



/**
 * Created by 28713 on 2017/2/14.
 */

public class GroupSearchAdater extends RecyclerView.Adapter<GroupSearchAdater.MyViewHolder> {

    private Context context;
    private GroupSearchBean groupSearchBean;
    private OnItemClickLitener onItemClickLitener;

    public GroupSearchAdater(Context context,GroupSearchBean groupSearchBean) {
        this.context = context;
        this.groupSearchBean = groupSearchBean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_group_search, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.group_name_tv.setText(groupSearchBean.getData().get(position).getUnion_name()+"("+groupSearchBean.getData().get(position).getMember_cnt()+")");
        holder.group_info_tv.setText(groupSearchBean.getData().get(position).getUnion_brief_info()+"");
        Glide.with(context)
                .load(Constant.NetConstant.BASE_USER_RESOURE+groupSearchBean.getData().get(position).getUnion_portrait())
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                //.error(R.mipmap.icon_dengru_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.group_pic_iv);
        Logger.e(Constant.NetConstant.BASE_USER_RESOURE+groupSearchBean.getData().get(position).getUnion_portrait()+"");

        if (null != onItemClickLitener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickLitener.onItemClick(v, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickLitener.onItemLongClick(v, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(groupSearchBean==null)
            return  0;
        return groupSearchBean.getData().size();
    }

    public void rf(GroupSearchBean groupSearchBean) {
        this.groupSearchBean=groupSearchBean;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView group_name_tv,group_info_tv;
        CircleImageView group_pic_iv;

        public MyViewHolder(View view) {
            super(view);
            group_name_tv = (TextView) view.findViewById(R.id.group_name_tv);
            group_info_tv = (TextView) view.findViewById(R.id.group_info_tv);
            group_pic_iv= (CircleImageView) view.findViewById(R.id.group_pic_iv);
        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }
}
