package com.gohoc.xiupuling.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by 28713 on 2017/4/19.
 */

public class MoudlesLeve2Bean extends AbstractExpandableItem<MoudlesBean.DataBean.ModuleListBean> implements MultiItemEntity {
    private boolean isCheck = false;
    private MoudlesBean.DataBean.ModuleListBean moduleListBean;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public MoudlesLeve2Bean(MoudlesBean.DataBean.ModuleListBean moduleListBean) {
        this.moduleListBean = moduleListBean;
    }

    public MoudlesBean.DataBean.ModuleListBean getModuleListBean() {
        return moduleListBean;
    }

    public void setModuleListBean(MoudlesBean.DataBean.ModuleListBean moduleListBean) {
        this.moduleListBean = moduleListBean;
    }

    @Override
    public int getLevel() {
        return MoudlesBean.MODULE_SUB;
    }

    @Override
    public int getItemType() {
        return MoudlesBean.MODULE_SUB;
    }
}
