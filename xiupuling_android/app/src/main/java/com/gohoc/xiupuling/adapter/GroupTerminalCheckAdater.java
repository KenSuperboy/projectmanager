package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupTermianlListBean;
import com.suke.widget.SwitchButton;

/**
 * Created by 28713 on 2017/2/14.
 */

public class GroupTerminalCheckAdater extends RecyclerView.Adapter<GroupTerminalCheckAdater.MyViewHolder> {

    private Context context;
    private GroupTermianlListBean groupTermianlListBean;
    private OnItemClickLitener onItemClickLitener;

    public GroupTerminalCheckAdater(Context context, GroupTermianlListBean groupTermianlListBean) {
        this.context = context;
        this.groupTermianlListBean = groupTermianlListBean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_group_termianl_s, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.termianlInfoTv.setText(groupTermianlListBean.getData().get(position).getTerminal_no() + "号机  " + groupTermianlListBean.getData().get(position).getShop_name());
        if (groupTermianlListBean.getData().get(position).getTerm_orientation() == 0)
            holder.og.setImageResource(R.mipmap.icon_term_orientation_h);
        else
            holder.og.setImageResource(R.mipmap.icon_group_add_port);

        if (groupTermianlListBean.getData().get(position).isCheck())
            holder.right_iv.setVisibility(View.VISIBLE);
        else
            holder.right_iv.setVisibility(View.GONE);
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
        return groupTermianlListBean.getData().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView og, right_iv;
        TextView termianlInfoTv;
        SwitchButton switchButton;


        public MyViewHolder(View view) {
            super(view);
            termianlInfoTv = (TextView) view.findViewById(R.id.termianl_info_tv);
            og = (ImageView) view.findViewById(R.id.termianl_og_iv);
            right_iv = (ImageView) view.findViewById(R.id.right_iv);
            switchButton = (SwitchButton) view.findViewById(R.id.switch_button);

        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }
}
