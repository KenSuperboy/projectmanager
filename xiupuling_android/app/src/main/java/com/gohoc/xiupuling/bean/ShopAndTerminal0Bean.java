package com.gohoc.xiupuling.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by 28713 on 2017/2/28.
 */

public class ShopAndTerminal0Bean extends AbstractExpandableItem<ShopAndTerminal1Bean> implements MultiItemEntity {

  private ShopBean.DataBean  shopDateBean;

    public ShopAndTerminal0Bean(ShopBean.DataBean shopDateBean) {
        this.shopDateBean = shopDateBean;
    }

    public ShopBean.DataBean getShopDateBean() {
        return shopDateBean;
    }

    public void setShopDateBean(ShopBean.DataBean shopDateBean) {
        this.shopDateBean = shopDateBean;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
