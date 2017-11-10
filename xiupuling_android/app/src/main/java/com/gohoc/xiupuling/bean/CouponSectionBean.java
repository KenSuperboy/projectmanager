package com.gohoc.xiupuling.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by 28713 on 2017/4/10.
 */

public class CouponSectionBean extends SectionEntity<CouponBean.DataBean> {
    private boolean isMore;
    private CouponBean.DataBean couponBean;

    public CouponSectionBean(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);

        this.isMore = isMroe;
    }

    public CouponSectionBean(CouponBean.DataBean couponBean) {
        super(couponBean);
        this.couponBean = couponBean;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }


    public CouponBean.DataBean getCouponBean() {
        return couponBean;
    }

    public void setCouponBean(CouponBean.DataBean couponBean) {
        this.couponBean = couponBean;
    }
}
