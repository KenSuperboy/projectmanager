package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GoodsBean;
import com.gohoc.xiupuling.bean.ShopViewBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.utils.Utils;

import java.util.List;

/**
 * Created by 28713 on 2017/2/14.
 */

public class MemberShopSubAdater extends RecyclerView.Adapter<MemberShopSubAdater.MyViewHolder> {

    private Context context;
    private List<GoodsBean.DataBean> shops;
    private OnItemClickLitener onItemClickLitener;

    public MemberShopSubAdater(Context context, List<GoodsBean.DataBean> shops) {
        this.context = context;
        this.shops = shops;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_shop_sub, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
       holder.sp_nane_tv.setText(shops.get(position).getGoods_name());
        holder.sp_money_tv.setText("￥"+shops.get(position).getGoods_price());
        Glide.with(context)
                .load(Constant.NetConstant.BASE_USER_RESOURE+shops.get(position).getGoods_icon()+ Utils.getThumbnail(100,100))
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                //.error(R.mipmap.icon_dengru_touxiang)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.sp_cover_iv);

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
        return shops.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sp_nane_tv, sp_money_tv;
        ImageView sp_cover_iv;


        public MyViewHolder(View view) {
            super(view);
            sp_nane_tv = (TextView) view.findViewById(R.id.sp_nane_tv);
            sp_money_tv = (TextView) view.findViewById(R.id.sp_money_tv);
            sp_cover_iv= (ImageView) view.findViewById(R.id.sp_cover_iv);
        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }
}
