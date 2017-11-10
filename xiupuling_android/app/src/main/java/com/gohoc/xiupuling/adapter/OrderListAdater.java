package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.OrderBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.utils.SpanUtils;
import com.gohoc.xiupuling.widget.RatingBar;


import java.util.List;

/**
 * Created by 28713 on 2017/2/14.
 */

public class OrderListAdater extends BaseQuickAdapter<OrderBean.DataBean, BaseViewHolder> {

    private Context context;

    public OrderListAdater(Context context, int layoutResId, List<OrderBean.DataBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, OrderBean.DataBean dataBean) {
        SpanUtils money=new SpanUtils().append("￥").append(dataBean.getTotalamt()+"").append("元").setForegroundColor(context.getResources().getColor(R.color.tips_font));
        baseViewHolder
                .setText(R.id.termianl_title_tv, (dataBean.getTerminal_no() + "号机").trim()+" " + (dataBean.getShop_name()+ "").trim())
                .setText(R.id.v_title_tv, dataBean.getWork_name() + "")
                .setText(R.id.v_money_tv,money.create());
        ImageView iv = baseViewHolder.getView(R.id.works_iv);
        Glide.with(context)
                .load(Constant.NetConstant.BASE_USER_RESOURE + dataBean.getMaterial_store_url() + "")
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                //.error(R.mipmap.icon_dengru_touxiang)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);
        RatingBar ratingBar = baseViewHolder.getView(R.id.ratingbar);
        ratingBar.setFocusable(false);
        ratingBar.setEnabled(false);
        ratingBar.setmClickable(false);
        ratingBar.setStar(dataBean.getStar_level());
    }

}

