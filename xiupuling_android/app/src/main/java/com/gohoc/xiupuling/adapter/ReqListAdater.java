package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ReqListBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.utils.Utils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.orhanobut.logger.Logger;

/**
 * Created by 28713 on 2017/2/14.
 */

public class ReqListAdater extends RecyclerView.Adapter<ReqListAdater.MyViewHolder> {

    private Context context;
    private ReqListBean reqListBean;
    private OnItemClickLitener onItemClickLitener;
    private int DownX, DownY, moveX, moveY;
    private long currentMS;

    public ReqListAdater(Context context, ReqListBean reqListBean) {
        this.context = context;
        this.reqListBean = reqListBean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_req, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (reqListBean.getData().getList().get(position).getWorklist().size() > 0)
        {
            holder.img1.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(Constant.NetConstant.BASE_USER_RESOURE + reqListBean.getData().getList().get(position).getWorklist().get(0).getMaterial_store_url()+ Utils.getThumbnail(100,100))
                   .placeholder(R.mipmap.df_logo)
                    //.error(R.mipmap.icon_dengru_touxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(holder.img1);
        }else
                holder.img1.setVisibility(View.GONE);

        if (reqListBean.getData().getList().get(position).getWorklist().size() > 1)
        {
            holder.img2.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(Constant.NetConstant.BASE_USER_RESOURE + reqListBean.getData().getList().get(position).getWorklist().get(1).getMaterial_store_url())
                    // .placeholder(R.mipmap.icon_dengru_touxiang)
                    //.error(R.mipmap.icon_dengru_touxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(holder.img2);
        }else
                holder.img2.setVisibility(View.GONE);
        if (reqListBean.getData().getList().get(position).getWorklist().size() > 2)
        {
            holder.img3.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(Constant.NetConstant.BASE_USER_RESOURE + reqListBean.getData().getList().get(position).getWorklist().get(2).getMaterial_store_url())
                    // .placeholder(R.mipmap.icon_dengru_touxiang)
                    //.error(R.mipmap.icon_dengru_touxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(holder.img3);
        }else
                holder.img3.setVisibility(View.GONE);

        holder.tv.setText(reqListBean.getData().getList().get(position).getActivity_title() + "");

        if (reqListBean.getData().getList().get(position).getUnviewedworkcnt() > 0) {
            holder.new_count_tv.setText(reqListBean.getData().getList().get(position).getUnviewedworkcnt() + "");
            holder.new_count_tv.setVisibility(View.VISIBLE);
        } else
            holder.new_count_tv.setVisibility(View.GONE);

        if (reqListBean.getData().getList().get(position).getWorklist().size() > 0) {
            holder.imgRv.setVisibility(View.VISIBLE);
            holder.no_data_rl.setVisibility(View.GONE);

        } else {
            holder.imgRv.setVisibility(View.GONE);
            holder.no_data_rl.setVisibility(View.VISIBLE);
        }


        if (null != onItemClickLitener) {
            holder.content_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.swipeMenuLayout.smoothClose();
                    onItemClickLitener.onClick(v, position);
                }
            });

            holder.mBehindViews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.swipeMenuLayout.smoothClose();
                    onItemClickLitener.onEdit(v, position);
                }
            });
            holder.mBehindViews2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.swipeMenuLayout.smoothClose();
                    onItemClickLitener.onClosed(v, position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return reqListBean.getData().getList().size();
    }

    public void rf(ReqListBean reqListBean) {
        this.reqListBean = reqListBean;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv, new_count_tv;
        LinearLayout content_ll;
        RelativeLayout no_data_rl, imgRv;
        LinearLayout mBehindViews, mBehindViews2;
        private SwipeMenuLayout swipeMenuLayout;
        private ImageView img1, img2, img3;


        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.req_name_tv);
            new_count_tv = (TextView) view.findViewById(R.id.new_count_tv);
            content_ll = (LinearLayout) view.findViewById(R.id.content_ll);
            imgRv = (RelativeLayout) view.findViewById(R.id.imgRv);
            no_data_rl = (RelativeLayout) view.findViewById(R.id.no_data_rl);
            mBehindViews = (LinearLayout) view.findViewById(R.id.mBehindViews);
            mBehindViews2 = (LinearLayout) view.findViewById(R.id.mBehindViews2);
            swipeMenuLayout = (SwipeMenuLayout) view.findViewById(R.id.swipeMenuLayout);
            img1 = (ImageView) view.findViewById(R.id.img1);
            img2 = (ImageView) view.findViewById(R.id.img2);
            img3 = (ImageView) view.findViewById(R.id.img3);
        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    public ReqListBean getReqListBean() {
        return reqListBean;
    }

    public void setReqListBean(ReqListBean reqListBean) {
        this.reqListBean = reqListBean;
    }

    public interface OnItemClickLitener {
        void onClick(View v, int position);

        void onClosed(View v, int position);

        void onEdit(View v, int position);
    }
}
