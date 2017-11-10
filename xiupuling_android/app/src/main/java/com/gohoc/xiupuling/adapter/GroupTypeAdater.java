package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.BusinessTypeBean;
import com.gohoc.xiupuling.bean.KvBean;

import java.util.List;

/**
 * Created by 28713 on 2017/2/14.
 */

public class GroupTypeAdater extends RecyclerView.Adapter<GroupTypeAdater.MyViewHolder> {

    private Context context;
    private List<KvBean> menuList;
    private OnItemClickLitener onItemClickLitener;

    public GroupTypeAdater(Context context, List<KvBean> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_simple_clickbox, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv.setText(menuList.get(position).getV() + "");
        if(menuList.get(position).isCheck())
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
        return menuList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView right_iv;


        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.menu_title);
            right_iv = (ImageView) view.findViewById(R.id.right_iv);
        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }
}
