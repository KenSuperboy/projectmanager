package com.gohoc.xiupuling.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by 28713 on 2017/2/28.
 */

public class ShopAndTerminal1Bean extends AbstractExpandableItem  implements MultiItemEntity{

    private ShopBean.DataBean.TermlistBean termlistBean;
    private boolean isLast=false;
    private ShopBean.DataBean shop;

    public ShopAndTerminal1Bean(ShopBean.DataBean shop) {
        this.shop = shop;
    }

    public ShopBean.DataBean getShop() {
        return shop;
    }

    public void setShop(ShopBean.DataBean shop) {
        this.shop = shop;
    }

    public ShopBean.DataBean.TermlistBean getTermlistBean() {
        return termlistBean;
    }

    public void setTermlistBean(ShopBean.DataBean.TermlistBean termlistBean) {
        this.termlistBean = termlistBean;
    }

    public ShopAndTerminal1Bean(ShopBean.DataBean.TermlistBean termlistBean, boolean isLast) {
        this.termlistBean = termlistBean;
        this.isLast = isLast;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public int getItemType() {
        return 2;
    }
}
