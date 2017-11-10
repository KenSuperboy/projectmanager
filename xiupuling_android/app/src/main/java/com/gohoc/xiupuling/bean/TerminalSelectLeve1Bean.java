package com.gohoc.xiupuling.bean;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by 28713 on 2017/4/21.
 */

public class TerminalSelectLeve1Bean extends AbstractExpandableItem<TerminalListBean> implements MultiItemEntity {
    private GroupTermianlListBean.DataBean gdata;
    private TerminalListBean.DataBean sdata;
    private String sortNo = "0";
    private String shopName;

    public TerminalSelectLeve1Bean() {

    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public GroupTermianlListBean.DataBean getGdata() {
        return gdata;
    }

    public void setGdata(GroupTermianlListBean.DataBean gdata) {
        this.gdata = gdata;
    }

    public TerminalListBean.DataBean getSdata() {
        return sdata;
    }

    public void setSdata(TerminalListBean.DataBean sdata) {
        this.sdata = sdata;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public TerminalSelectLeve1Bean(GroupTermianlListBean.DataBean gdata, String sortNo) {
        this.gdata = gdata;
        this.sortNo = sortNo;
    }

    public TerminalSelectLeve1Bean(TerminalListBean.DataBean sdata, String shopName, String sortNo) {
        this.sdata = sdata;
        this.sortNo = sortNo;
        this.shopName=shopName;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getItemType() {
        return TerminalSelectBean.GROUP_MENU;
    }
}
