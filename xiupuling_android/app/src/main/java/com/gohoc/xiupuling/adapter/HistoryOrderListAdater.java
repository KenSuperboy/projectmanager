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
import com.gohoc.xiupuling.bean.OrderHistoryBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.RatingBar;


/**
 * Created by 28713 on 2017/2/14.
 */

public class HistoryOrderListAdater extends RecyclerView.Adapter<HistoryOrderListAdater.MyViewHolder> {

    private Context context;
    private OrderHistoryBean orderBean;
    private OnItemClickLitener onItemClickLitener;

    public HistoryOrderListAdater(Context context, OrderHistoryBean orderBean) {
        this.context = context;
        this.orderBean = orderBean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_order2, parent,
                false));
        return holder;
    }

    public void rf(OrderHistoryBean myorderBean) {
        this.orderBean = myorderBean;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.termianl_title_tv.setText(orderBean.getData().get(position).getTerminal_no()+orderBean.getData().get(position).getShop_name());
        holder.v_title_tv.setText(orderBean.getData().get(position).getWork_name()+"");
        LogUtil.d(orderBean.getData().get(position).getMaterial_store_url()+":适配器里的图片路径："+Constant.NetConstant.BASE_USER_RESOURE+orderBean.getData().get(position).getMaterial_store_url()+ Utils.getThumbnail(202,202));
        Glide.with(context)
                .load(Constant.NetConstant.BASE_USER_RESOURE+orderBean.getData().get(position).getMaterial_store_url()+ Utils.getThumbnail(202,202))
                 .placeholder(R.mipmap.df_logo)
                //.error(R.mipmap.icon_dengru_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.works_iv);

        holder.v_money_tv.setText("￥"+orderBean.getData().get(position).getTotalamount()+"元");
        holder.ratingbar.setStar(orderBean.getData().get(position).getStar_level());
        holder.v_time_tv.setText("结束于 "+ Utils.formatDate(Utils.StrToDate(orderBean.getData().get(position).getCreate_time()),"yyyy-MM-dd"));
        holder.ratingbar.setEnabled(false);
        holder.ratingbar.setmClickable(false);
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
        if (null == orderBean||orderBean.getData()==null||orderBean.getData().size()==0)
            return 0;
        return orderBean.getData().size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView termianl_title_tv, v_title_tv, v_money_tv,v_time_tv;
        ImageView works_iv;
        RatingBar ratingbar;


        public MyViewHolder(View view) {
            super(view);
            termianl_title_tv = (TextView) view.findViewById(R.id.termianl_title_tv);
            v_title_tv = (TextView) view.findViewById(R.id.v_title_tv);
            works_iv = (ImageView) view.findViewById(R.id.works_iv);
            ratingbar = (RatingBar) view.findViewById(R.id.ratingbar);
            v_money_tv = (TextView) view.findViewById(R.id.v_money_tv);
            v_time_tv = (TextView) view.findViewById(R.id.v_time_tv);
        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }
}
