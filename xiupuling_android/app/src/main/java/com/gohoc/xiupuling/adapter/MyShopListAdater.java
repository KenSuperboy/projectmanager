package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ShopBean;

/**
 * Created by 28713 on 2017/2/14.
 */

public class MyShopListAdater extends RecyclerView.Adapter<MyShopListAdater.MyViewHolder> {

    private Context context;
    private ShopBean shopBean;
    private OnItemClickLitener onItemClickLitener;


    public MyShopListAdater(Context context, ShopBean shopBean) {
        this.context = context;
        this.shopBean = shopBean;
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

       holder.tv.setText(shopBean.getData().get(position).getShop_name());
        if (shopBean.getData().get(position).isSelect()) {
            holder.rIv.setVisibility(View.VISIBLE);
        } else {
            holder.rIv.setVisibility(View.INVISIBLE);
        }

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
        return shopBean.getData().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView rIv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.menu_title);
            rIv = (ImageView) view.findViewById(R.id.right_iv);
        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }


}
