package com.gohoc.xiupuling.bean;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by 28713 on 2017/4/21.
 */

public class TerminalSelectBean implements MultiItemEntity {
    public static final int TITLE_TEXT = 1;
    public static final int GROUP = 2;
    public static final int CHECK = 3;
    public static final int GROUP_MENU = 4;
    private int type;
    private GroupTermianlListBean.DataBean gdataBean;
    private TerminalListBean.DataBean tdataBean;
    private String shopName;
    private String sortNo = "0";
    private  boolean isCheck=false;

    public TerminalSelectBean(int type, GroupTermianlListBean.DataBean gdataBean, String sortNo) {
        this.type = type;
        this.gdataBean = gdataBean;
        this.sortNo=sortNo;
    }

    public TerminalSelectBean(int type, TerminalListBean.DataBean tdataBean, String shopName, String sortNo) {
            this.type = type;
        this.tdataBean = tdataBean;
        this.shopName = shopName;
        this.sortNo=sortNo;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public GroupTermianlListBean.DataBean getGdataBean() {
        return gdataBean;
    }

    public void setGdataBean(GroupTermianlListBean.DataBean gdataBean) {
        this.gdataBean = gdataBean;
    }



    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public TerminalListBean.DataBean getTdataBean() {
        return tdataBean;
    }

    public void setTdataBean(TerminalListBean.DataBean tdataBean) {
        this.tdataBean = tdataBean;
    }

    public int getType() {
        return type;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
