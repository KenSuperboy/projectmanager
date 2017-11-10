package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.CouponBean;
import com.gohoc.xiupuling.bean.CouponSectionBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.utils.Utils;
import com.wuxiaolong.androidutils.library.TimeUtil;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by 28713 on 2017/4/8.
 */

public class CouponsAdater extends BaseSectionQuickAdapter<CouponSectionBean, BaseViewHolder> {
    private Context context;

    public CouponsAdater(Context context,int layoutResId, int sectionHeadResId, List<CouponSectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
        this.context=context;
    }


    @Override
    protected void convertHead(BaseViewHolder baseViewHolder, CouponSectionBean couponSectionBean) {

    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CouponSectionBean couponSectionBean) {

        ImageView ic=baseViewHolder.getView(R.id.add_iv);
        Glide.with(context)
                .load(Constant.NetConstant.BASE_USER_RESOURE+couponSectionBean.getCouponBean().getIcon_url())
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                //.error(R.mipmap.icon_dengru_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ic);

        baseViewHolder.setText(R.id.coupons_title_tv, couponSectionBean.getCouponBean().getName());
        baseViewHolder.setText(R.id.coupons_time_tv, "有效期至"+ couponSectionBean.getCouponBean().getEnable_time()+"");
        baseViewHolder.setText(R.id.coupons_tips_tv, couponSectionBean.getCouponBean().getInstructions());
        baseViewHolder.setText(R.id.coupons_count_tv,"+"+couponSectionBean.getCouponBean().getValue());
        if (couponSectionBean.getCouponBean().getStatus()!=1)
            baseViewHolder.setBackgroundRes(R.id.bg_rv,R.mipmap.coupons_bg_used);
        else
            baseViewHolder.setBackgroundRes(R.id.bg_rv,R.mipmap.coupons_bg);
    }
}
