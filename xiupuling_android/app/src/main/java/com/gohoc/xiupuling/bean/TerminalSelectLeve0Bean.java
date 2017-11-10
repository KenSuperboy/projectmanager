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

public class TerminalSelectLeve0Bean extends AbstractExpandableItem<TerminalSelectLeve1Bean> implements MultiItemEntity {

    private GroupTermianlListBean.DataBean gdataBean;

    public TerminalSelectLeve0Bean() {

    }

    public GroupTermianlListBean.DataBean getGdataBean() {
        return gdataBean;
    }

    public void setGdataBean(GroupTermianlListBean.DataBean gdataBean) {
        this.gdataBean = gdataBean;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return TerminalSelectBean.GROUP;
    }
}
