package com.gohoc.xiupuling.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/5/27.
 */

public class PushResultMultiple implements MultiItemEntity ,Serializable{
    private PushReqResultBean.RangeBean rangeBean;
    private PushReqResultBean.UnionBean unionBean;
    private PushReqResultBean.DingtouBean dingtouBean;
    public static final int DING_TYPE = 1;
    public static final int RANG_TYPE = 2;
    public static final int UNION_TYPE = 3;
    private int type;

    public PushResultMultiple(PushReqResultBean.RangeBean rangeBean, int type) {
        this.rangeBean = rangeBean;
        this.type = type;
    }

    public PushResultMultiple(PushReqResultBean.UnionBean unionBean, int type) {
        this.unionBean = unionBean;
        this.type = type;
    }

    public PushResultMultiple(PushReqResultBean.DingtouBean dingtouBean, int type) {
        this.dingtouBean=dingtouBean;
        this.type = type;
    }

    public PushReqResultBean.RangeBean getRangeBean() {
        return rangeBean;
    }

    public void setRangeBean(PushReqResultBean.RangeBean rangeBean) {
        this.rangeBean = rangeBean;
    }

    public PushReqResultBean.UnionBean getUnionBean() {
        return unionBean;
    }

    public void setUnionBean(PushReqResultBean.UnionBean unionBean) {
        this.unionBean = unionBean;
    }

    public int getDING_TYPE() {
        return DING_TYPE;
    }

    public int getRANG_TYPE() {
        return RANG_TYPE;
    }

    public int getUNION_TYPE() {
        return UNION_TYPE;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public PushReqResultBean.DingtouBean getDingtouBean() {
        return dingtouBean;
    }

    public void setDingtouBean(PushReqResultBean.DingtouBean dingtouBean) {
        this.dingtouBean = dingtouBean;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
