package com.gohoc.xiupuling.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/4/20.
 */

public class ShopAndGroupItemBean implements MultiItemEntity,Serializable {

    public static final int TEXT = 1;
    public static final int SHOP = 2;
    public static final int GROUP = 3;
    private int type;
    private GroupBean.DataBean groupDates;
    private ShopBean.DataBean shopBean;
    private String title;

    public ShopAndGroupItemBean(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public ShopAndGroupItemBean(int type, GroupBean.DataBean groupDates) {
        this.type = type;
        this.groupDates = groupDates;
    }

    public ShopAndGroupItemBean(int type, ShopBean.DataBean shopBean) {
        this.type = type;
        this.shopBean = shopBean;
    }

    public GroupBean.DataBean getGroupDates() {
        return groupDates;
    }

    public void setGroupDates(GroupBean.DataBean groupDates) {
        this.groupDates = groupDates;
    }

    public ShopBean.DataBean getShopBean() {
        return shopBean;
    }

    public void setShopBean(ShopBean.DataBean shopBean) {
        this.shopBean = shopBean;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
