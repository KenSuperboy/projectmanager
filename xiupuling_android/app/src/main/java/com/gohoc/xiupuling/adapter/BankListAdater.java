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
import com.gohoc.xiupuling.bean.BankBean;
import com.gohoc.xiupuling.bean.BusinessTypeBean;
import com.gohoc.xiupuling.constant.Constant;

/**
 * Created by 28713 on 2017/2/14.
 */

public class BankListAdater extends RecyclerView.Adapter<BankListAdater.MyViewHolder> {

    private Context context;
    private BankBean bankBean;
    private OnItemClickLitener onItemClickLitener;

    public BankListAdater(Context context, BankBean bankBean) {
        this.context = context;
        this.bankBean = bankBean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_bank, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name_tv.setText(bankBean.getData().get(position).getBank_name());
        Glide.with(context)
                .load(Constant.NetConstant.BASE_USER_RESOURE + bankBean.getData().get(position).getBank_icon())
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                //.error(R.mipmap.icon_dengru_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.cover_iv);


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
        if (null == bankBean || null == bankBean.getData())
            return 0;
        return bankBean.getData().size();
    }

    public void rf(BankBean bankBean) {
        this.bankBean = bankBean;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cover_iv;
        TextView name_tv;


        public MyViewHolder(View view) {
            super(view);
            name_tv = (TextView) view.findViewById(R.id.name_tv);
            cover_iv = (ImageView) view.findViewById(R.id.cover_iv);
        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }
}
