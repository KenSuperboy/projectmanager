package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.TokenBean;

/**
 * Created by 28713 on 2017/2/14.
 */

public class TokenAdater extends RecyclerView.Adapter<TokenAdater.MyViewHolder> {

    private Context context;
    private TokenBean tokenBean;
    private OnItemClickLitener onItemClickLitener;

    public TokenAdater(Context context, TokenBean tokenBean) {
        this.context = context;
        this.tokenBean = tokenBean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_token, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.token_title_tv.setText(tokenBean.getData().getList().get(position).getTrade_name() + "");
        holder.token_time_tv.setText(tokenBean.getData().getList().get(position).getCreate_time() + "");

        if (tokenBean.getData().getList().get(position).getTrade_point() > 0) {
            holder.token_v_tv.setText("+"+tokenBean.getData().getList().get(position).getTrade_point());
            holder.token_v_tv.setTextColor(context.getResources().getColor(R.color.token_item_r));

        } else {
            holder.token_v_tv.setTextColor(context.getResources().getColor(R.color.token_item_g));
            holder.token_v_tv.setText(tokenBean.getData().getList().get(position).getTrade_point()+"");
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
        return tokenBean.getData().getList().size();
    }

    public void rf(TokenBean tokenBeanTemp) {
        this.tokenBean = tokenBeanTemp;
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

    public TokenBean getTokenBean() {
        return tokenBean;
    }

    public void setTokenBean(TokenBean tokenBean) {
        this.tokenBean = tokenBean;
    }
}
