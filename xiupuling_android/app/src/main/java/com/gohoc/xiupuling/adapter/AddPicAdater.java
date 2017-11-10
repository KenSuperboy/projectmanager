package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PicBean;
import com.gohoc.xiupuling.constant.Constant;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by 28713 on 2017/2/14.
 */

public class AddPicAdater extends RecyclerView.Adapter<AddPicAdater.MyViewHolder> implements Constant {

    private Context context;
    private List<PicBean> picBeanList;
    private OnItemClickLitener onItemClickLitener;
    private OnPicChangeListion onPicChangeListion;
    private int maxCount = 5;

    public AddPicAdater(Context context, List<PicBean> picBeanList) {
        this.context = context;
        this.picBeanList = picBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_add_pic, parent,
                false));


        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (null != onPicChangeListion) {
            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPicChangeListion.OnDel(v, position);
                }
            });
            holder.pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onPicChangeListion)
                        onPicChangeListion.OnAdd(v, position);
                }
            });

            if (picBeanList.get(position).getType() == 1) {
                String url = picBeanList.get(position).getLocUrl();
                if (null == url) {
                    url = NetConstant.BASE_USER_RESOURE + picBeanList.get(position).getUrl();
                }
                Logger.e(url);
                Glide.with(context)
                        .load(url)
                        // .placeholder(R.mipmap.icon_dengru_touxiang)
                        //.error(R.mipmap.icon_dengru_touxiang)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.pic);
            } else {
                Glide.with(context)
                        .load(R.mipmap.shop_logo_add_nor)
                        // .placeholder(R.mipmap.icon_dengru_touxiang)
                        //.error(R.mipmap.icon_dengru_touxiang)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.pic);
            }


            if (picBeanList.get(position).getType() == 1)
                holder.del.setVisibility(View.VISIBLE);
            else
                holder.del.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return picBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView pic, del;


        public MyViewHolder(View view) {
            super(view);
            pic = (ImageView) view.findViewById(R.id.imageView1);
            del = (ImageView) view.findViewById(R.id.imageViewDel);
        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    public interface OnPicChangeListion {
        void OnDel(View v, int postion);

        void OnAdd(View v, int postion);
    }

    public OnPicChangeListion getOnPicChangeListion() {
        return onPicChangeListion;
    }

    public void setOnPicChangeListion(OnPicChangeListion onPicChangeListion) {
        this.onPicChangeListion = onPicChangeListion;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
