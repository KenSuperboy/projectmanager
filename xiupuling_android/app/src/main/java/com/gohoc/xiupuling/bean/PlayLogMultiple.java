package com.gohoc.xiupuling.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by 28713 on 2017/6/1.
 */

public class PlayLogMultiple implements MultiItemEntity {
    public static final int HEAD_TYPE = 1;
    public static final int CONTENT_TYPE = 2;
    public static final int CONTENT_H_TYPE = 3;
    private PlayLogBean.DataBean dataBean;
    private PlayLogBean.DataBean.DaydtllistBean daydtllistBean;
    private int type;
    private String total;

    public PlayLogMultiple(PlayLogBean.DataBean dataBean, int type) {
        this.dataBean = dataBean;
        this.type = type;
    }

    public PlayLogMultiple(PlayLogBean.DataBean.DaydtllistBean daydtllistBean, int type) {
        this.daydtllistBean = daydtllistBean;
        this.type = type;
    }

    public PlayLogMultiple(String total,PlayLogBean.DataBean.DaydtllistBean daydtllistBean, int type) {
        this.daydtllistBean = daydtllistBean;
        this.type = type;
        this.total=total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public PlayLogBean.DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(PlayLogBean.DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public PlayLogBean.DataBean.DaydtllistBean getDaydtllistBean() {
        return daydtllistBean;
    }

    public void setDaydtllistBean(PlayLogBean.DataBean.DaydtllistBean daydtllistBean) {
        this.daydtllistBean = daydtllistBean;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
