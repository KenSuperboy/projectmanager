package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.BusinessBean;
import com.gohoc.xiupuling.bean.BusinessTypeBean;

/**
 * Created by 28713 on 2017/2/14.
 */

public class BusinessTypeAdater extends RecyclerView.Adapter<BusinessTypeAdater.MyViewHolder> {

    private Context context;
    private BusinessTypeBean businessBean;
    private OnItemClickLitener onItemClickLitener;

    public BusinessTypeAdater(Context context, BusinessTypeBean businessBean) {
        this.context = context;
        this.businessBean = businessBean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_simple_menu_arr, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv.setText(businessBean.getData().get(position).getBusiness_name());


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
        return businessBean.getData().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;


        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.menu_title);
        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }
}
