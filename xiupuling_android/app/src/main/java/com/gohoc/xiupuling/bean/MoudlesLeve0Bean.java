package com.gohoc.xiupuling.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

/**
 * Created by 28713 on 2017/4/19.
 */

public class MoudlesLeve0Bean extends AbstractExpandableItem<MoudlesLeve1Bean> implements MultiItemEntity {


    private String title;


    public MoudlesLeve0Bean(String title) {    //一级
        this.title = title;
    }


    @Override
    public int getItemType() {
        return MoudlesBean.TITLE_TEXT;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
