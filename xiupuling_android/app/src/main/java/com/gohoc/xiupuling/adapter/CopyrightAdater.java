package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.CopyrrightBean;

/**
 * Created by 28713 on 2017/2/14.
 */

public class CopyrightAdater extends RecyclerView.Adapter<CopyrightAdater.MyViewHolder> {

    private Context context;
    private CopyrrightBean copyrrightBean;
    private OnItemClickLitener onItemClickLitener;

    public CopyrightAdater(Context context, CopyrrightBean copyrrightBean) {
        this.context = context;
        this.copyrrightBean = copyrrightBean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_copyright2, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.token_title_tv.setText(copyrrightBean.getData().get(position).getWork_name() + "");
        holder.token_time_tv.setText(copyrrightBean.getData().get(position).getPay_time() + "");

        if (copyrrightBean.getData().get(position).getTrade_amount() > 0) {
            holder.token_v_tv.setText("+￥"+copyrrightBean.getData().get(position).getTrade_amount());
            holder.token_v_tv.setTextColor(context.getResources().getColor(R.color.colorPrimary ));

        } else {
            holder.token_v_tv.setTextColor(context.getResources().getColor(R.color.token_item_g));
            holder.token_v_tv.setText("-￥"+Math.abs(copyrrightBean.getData().get(position).getTrade_amount()));
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
        if (null == copyrrightBean)
            return 0;
        return copyrrightBean.getData().size();
    }

    public void rf(CopyrrightBean copyrrightBean) {
        this.copyrrightBean=copyrrightBean;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView token_title_tv, token_time_tv, token_v_tv;
        public MyViewHolder(View view) {
            super(view);
            token_title_tv = (TextView) view.findViewById(R.id.token_title_tv);
            token_time_tv = (TextView) view.findViewById(R.id.token_time_tv);
            token_v_tv = (TextView) view.findViewById(R.id.token_v_tv);
        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }
}
