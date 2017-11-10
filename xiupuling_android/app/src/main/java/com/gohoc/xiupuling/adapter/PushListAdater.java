package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ReqBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.utils.Utils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

/**
 * Created by 28713 on 2017/2/14.
 */

public class PushListAdater extends RecyclerView.Adapter<PushListAdater.MyViewHolder> {

    private Context context;
    private ReqBean reqListBean;
    private OnItemClickLitener onItemClickLitener;

    public PushListAdater(Context context, ReqBean reqListBean) {
        this.context = context;
        this.reqListBean = reqListBean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_push, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // holder.tv.setText(reqListBean.getData().getList());
        holder.v_title_tv.setText(reqListBean.getData().getList().get(position).getWork_name() + "");
        holder.termianl_count_tv.setText(reqListBean.getData().getList().get(position).getTerminal_cnt() + "");
        holder.rp_count_tv.setText(reqListBean.getData().getList().get(position).getCycle_cnt() + "");
        Glide.with(context)
                .load(Constant.NetConstant.BASE_USER_RESOURE + reqListBean.getData().getList().get(position).getMaterial_store_url() + Utils.getThumbnail(202,202))
                 .placeholder(R.mipmap.df_logo)
                //.error(R.mipmap.icon_port_home)\
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.works_iv);
        if (null != onItemClickLitener) {
            holder.content_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.swipeMenuLayout.smoothClose();
                    onItemClickLitener.onItemClick(v, position);
                }
            });
            holder.mBehindViews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.swipeMenuLayout.smoothClose();
                    onItemClickLitener.onItemPush(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (null == reqListBean)
            return 0;
        return
           reqListBean.getData().getList().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView v_title_tv, termianl_count_tv, rp_count_tv;
        ImageView works_iv;
        LinearLayout content_ll,mBehindViews;
        SwipeMenuLayout swipeMenuLayout;


        public MyViewHolder(View view) {
            super(view);
            v_title_tv = (TextView) view.findViewById(R.id.v_title_tv);
            termianl_count_tv = (TextView) view.findViewById(R.id.termianl_count_tv);
            rp_count_tv = (TextView) view.findViewById(R.id.rp_count_tv);
            works_iv = (ImageView) view.findViewById(R.id.works_iv);
            content_ll= (LinearLayout) view.findViewById(R.id.content_ll);
            mBehindViews= (LinearLayout) view.findViewById(R.id.mBehindViews);
            swipeMenuLayout= (SwipeMenuLayout) view.findViewById(R.id.swipeMenuLayout);

        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    public void rf(ReqBean reqBean) {
        this.reqListBean = reqBean;
        notifyDataSetChanged();
    }

    public ReqBean getReqListBean() {
        return reqListBean;
    }

    public void setReqListBean(ReqBean reqListBean) {
        this.reqListBean = reqListBean;
    }

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemPush(View view , int position);
    }
}
