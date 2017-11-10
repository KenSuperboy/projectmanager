package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ReqListBean;
import com.gohoc.xiupuling.constant.Constant;

/**
 * Created by 28713 on 2017/3/20.
 */

public class ImgAdater extends BaseAdapter {
    private Context context;
    ReqListBean.DataBean.ListBean worklistBean;
    private LayoutInflater layout;

    public ImgAdater(Context context, ReqListBean.DataBean.ListBean worklistBean) {
        this.context = context;
        this.worklistBean = worklistBean;
        this.layout = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return worklistBean.getWorklist().size();
    }

    @Override
    public Object getItem(int position) {
        return worklistBean.getWorklist().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layout.inflate(R.layout.item_img, null);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        Glide.with(context)
                .load(Constant.NetConstant.BASE_USER_RESOURE + worklistBean.getWorklist().get(position).getMaterial_store_url())
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                //.error(R.mipmap.icon_dengru_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(img);
        return convertView;
    }
}
