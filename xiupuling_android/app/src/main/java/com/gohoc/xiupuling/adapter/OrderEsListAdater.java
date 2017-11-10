package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GoodsBean;
import com.gohoc.xiupuling.bean.OrderSBean;
import com.gohoc.xiupuling.bean.ShopViewBean;
import com.gohoc.xiupuling.ui.order.OrderConfirmVipActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 28713 on 2017/2/14.
 */

public class OrderEsListAdater extends RecyclerView.Adapter<OrderEsListAdater.MyViewHolder> {

    private Context context;
    private OrderSBean orderSBean;
    private OnItemClickLitener onItemClickLitener;

    public OrderEsListAdater(Context context,  OrderSBean orderSBean) {
        this.context = context;
        this.orderSBean = orderSBean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_order2, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


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
        return orderSBean.getData().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView sp_type_iv;
        TextView sp_type_name_tv;
        RecyclerView  list;

        public MyViewHolder(View view) {
            super(view);
            sp_type_name_tv= (TextView) view.findViewById(R.id.sp_type_name_tv);
            sp_type_iv= (ImageView) view.findViewById(R.id.sp_type_iv);
            list= (RecyclerView) view.findViewById(R.id.list);
        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }
}
