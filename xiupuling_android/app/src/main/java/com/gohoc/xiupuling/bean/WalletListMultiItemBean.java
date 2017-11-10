package com.gohoc.xiupuling.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by 28713 on 2017/5/16.
 */

public class WalletListMultiItemBean implements MultiItemEntity {
    public static final int HEAD = 1;
    public static final int  CONTENT= 2;
    private int type;
    WalletListBean.DataBean.ListBean list;
    private double count;
    private String times;

    public WalletListMultiItemBean(int type,WalletListBean.DataBean.ListBean list) {
        this.type = type;
        this.list = list;
    }

    public WalletListMultiItemBean(int type, double count, String times) {
        this.type = type;
        this.count = count;
        this.times = times;
    }

    @Override
    public int getItemType() {
        return type;
    }


    public WalletListBean.DataBean.ListBean getList() {
        return list;
    }

    public void setList(WalletListBean.DataBean.ListBean list) {
        this.list = list;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
