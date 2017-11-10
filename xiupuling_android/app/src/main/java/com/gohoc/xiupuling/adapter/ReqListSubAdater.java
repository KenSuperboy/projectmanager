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
import com.gohoc.xiupuling.bean.ReqListBean;
import com.gohoc.xiupuling.constant.Constant;

/**
 * Created by 28713 on 2017/2/14.
 */

public class ReqListSubAdater extends RecyclerView.Adapter<ReqListSubAdater.MyViewHolder> implements Constant{

    private Context context;
    private ReqListBean.DataBean.ListBean worklistBean;


    public ReqListSubAdater(Context context, ReqListBean.DataBean.ListBean worklistBean) {
        this.context = context;
        this.worklistBean = worklistBean;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_img, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
      //  holder.tv.setText(worklistBean.getData().getList().get(position).getActivity_title() + "");
        Glide.with(context)
                .load(NetConstant.BASE_USER_RESOURE+worklistBean.getWorklist().get(position).getMaterial_store_url())
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                //.error(R.mipmap.icon_dengru_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.img);


    }

    @Override
    public int getItemCount() {
       // return worklistBean.getWorklist().size();
        int count =worklistBean.getWorklist().size();
        if(count>3)
          return 3;
        return count;
    }

    public void rf(ReqListBean.DataBean.ListBean worklistBean) {
        this.worklistBean = worklistBean;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;


        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

}
