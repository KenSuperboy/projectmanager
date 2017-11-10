package com.gohoc.xiupuling.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by 28713 on 2017/4/19.
 */

public class MoudlesLeve1Bean extends AbstractExpandableItem<MoudlesLeve2Bean> implements MultiItemEntity {

    private MoudlesBean.DataBean leve2datas;

    public MoudlesBean.DataBean getLeve2datas() {
        return leve2datas;
    }

    public void setLeve2datas(MoudlesBean.DataBean leve2datas) {
        this.leve2datas = leve2datas;
    }

    public MoudlesLeve1Bean(MoudlesBean.DataBean leve2datas) {
        this.leve2datas = leve2datas;
    }
    private int openCount=0;

    public int getOpenCount() {
        return openCount;
    }

    public void setOpenCount(int openCount) {
        this.openCount = openCount;
    }

    @Override
    public int getLevel() {
        return MoudlesBean.MODULE;
    }

    @Override
    public int getItemType() {
        return MoudlesBean.MODULE;
    }
}
