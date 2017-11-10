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
import com.gohoc.xiupuling.bean.ReqListBean;
import com.gohoc.xiupuling.constant.Constant;

import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

/**
 * Created by 28713 on 2017/2/14.
 */

public class ReqMineWorksListAdater extends RecyclerView.Adapter<ReqMineWorksListAdater.MyViewHolder> implements Constant{

    private Context context;
    private ReqBean reqBean;
    private OnItemClickLitener onItemClickLitener,onClickConactdsListernter,onSelectReqListenter;


    public ReqMineWorksListAdater(Context context, ReqBean reqBean) {
        this.context = context;
        this.reqBean = reqBean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_works, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.og_tv.setText(reqBean.getData().getList().get(position).getOrientation()==0?"横屏":"竖屏");
        holder.type_tv.setText(reqBean.getData().getList().get(position).getRq_orientation_str()+"");
        holder.designer_name_tv.setText(reqBean.getData().getList().get(position).getNick_name()+"");
        holder.update_time_v_tv.setText(reqBean.getData().getList().get(position).getCreate_time());
        Glide.with(context)
                .load(BASE_USER_RESOURE+reqBean.getData().getList().get(position).getMaterial_store_url())
                .centerCrop()
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                //.error(R.mipmap.icon_dengru_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.woks_cover_iv);

        holder.contact_designer_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      if(null!=onClickConactdsListernter)
                          onClickConactdsListernter.onItemClick(v,position);
            }
        });
        holder.select_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=onSelectReqListenter)
                {
                    onSelectReqListenter.onItemClick(v,position);
                }
            }
        });



        if (null != onItemClickLitener) {
            holder.contact_designer_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickLitener.onItemClick(v, position);
                }
            });
            holder.contact_designer_ll.setOnLongClickListener(new View.OnLongClickListener() {
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
        return reqBean.getData().getList().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout contact_designer_ll;
        TextView og_tv,type_tv,designer_name_tv,update_time_v_tv,select_tv;
        ImageView woks_cover_iv;



        public MyViewHolder(View view) {
            super(view);
            og_tv = (TextView) view.findViewById(R.id.og_tv);
            type_tv = (TextView) view.findViewById(R.id.type_tv);
            designer_name_tv = (TextView) view.findViewById(R.id.designer_name_tv);
            update_time_v_tv = (TextView) view.findViewById(R.id.update_time_v_tv);
            select_tv = (TextView) view.findViewById(R.id.select_tv);
            contact_designer_ll= (LinearLayout) view.findViewById(R.id.contact_designer_ll);
            woks_cover_iv= (ImageView) view.findViewById(R.id.woks_cover_iv);

        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    public OnItemClickLitener getOnClickConactdsListernter() {
        return onClickConactdsListernter;
    }

    public void setOnClickConactdsListernter(OnItemClickLitener onClickConactdsListernter) {
        this.onClickConactdsListernter = onClickConactdsListernter;
    }

    public OnItemClickLitener getOnSelectReqListenter() {
        return onSelectReqListenter;
    }

    public void setOnSelectReqListenter(OnItemClickLitener onSelectReqListenter) {
        this.onSelectReqListenter = onSelectReqListenter;
    }

    public void rf(ReqBean reqBean) {
        this.reqBean = reqBean;
        notifyDataSetChanged();
    }

    public ReqBean getReqBean() {
        return reqBean;
    }

    public void setReqBean(ReqBean reqBean) {
        this.reqBean = reqBean;
    }
}
