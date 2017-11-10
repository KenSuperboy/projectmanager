package com.gohoc.xiupuling.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by 28713 on 2017/6/1.
 */

public class MoudleContMultiple implements MultiItemEntity {
    public static final int HEAD_TYPE = 1;
    public static final int CONTENT_TYPE = 2;
    private  MaterialBean.DataBean dataBean;
    private  MaterialBean.DataBean.MaterialListBean materialListBean;
    private  int type;

    public MoudleContMultiple(MaterialBean.DataBean dataBean, int type) {
        this.dataBean = dataBean;
        this.type = type;
    }

    public MoudleContMultiple(MaterialBean.DataBean.MaterialListBean materialListBean, int type) {
        this.materialListBean = materialListBean;
        this.type = type;
    }

    public MaterialBean.DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(MaterialBean.DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public MaterialBean.DataBean.MaterialListBean getMaterialListBean() {
        return materialListBean;
    }

    public void setMaterialListBean(MaterialBean.DataBean.MaterialListBean materialListBean) {
        this.materialListBean = materialListBean;
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
