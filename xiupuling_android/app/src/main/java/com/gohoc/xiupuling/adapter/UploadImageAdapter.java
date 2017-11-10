package com.gohoc.xiupuling.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.UploadBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/7/4 0004
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class UploadImageAdapter extends CubefishBaseAdapter<UploadBean.PicBean, UploadImageAdapter.ViewHolder> {

    private List<UploadBean.PicBean> datas = new ArrayList<>();
    private final Object lock = new Object();
    private int Max = 9;

    public UploadImageAdapter(Context context) {
        super(context);
    }

    public void setMax(int max) {
        Max = max;
    }

    public void setDatas(List<UploadBean.PicBean> datas) {
        synchronized (lock) {
            this.datas.clear();
            this.datas.addAll(datas);
            if (this.datas.size() < Max) {
                UploadBean.PicBean model = new UploadBean.PicBean();
                model.setUploadStatus(100);
                this.datas.add(model);
            }
        }
        this.notifyDataSetChanged();
    }


    @Override
    public int getLayoutRes() {
        return R.layout.item_add_pic2;
    }

    @Override
    public ViewHolder initViewHolder() {
        return new ViewHolder();
    }

    @Override
    public void onBindView(int position, UploadBean.PicBean data, ViewHolder holder) {

        if (data.getUploadStatus() == 100) {
            Glide.with(mContext).load(R.mipmap.btn_shangchuan).into(holder.pic);
            holder.del.setVisibility(View.GONE);
        } else {
            Glide.with(mContext).load(R.mipmap.btn_shangchuan).into(holder.pic);
        }
    }

    public class ViewHolder implements CubefishBaseViewHolder {
        ImageView pic, del;
        ProgressBar progressBar2;
        View mView;

        @Override
        public void onInFlate(View v) {
            this.mView = v;
            pic = (ImageView) v.findViewById(R.id.imageView1);
            del = (ImageView) v.findViewById(R.id.imageViewDel);
            progressBar2 = (ProgressBar) v.findViewById(R.id.progressBar2);
        }
    }
}
