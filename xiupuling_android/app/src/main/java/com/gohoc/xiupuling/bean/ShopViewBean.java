package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 28713 on 2017/4/6.
 */

public class ShopViewBean implements Serializable{
    private List<GoodsBean.DataBean> shoplist=new ArrayList<>();
    private int ico;
    private String title;

    public int getIco() {
        return ico;
    }

    public ShopViewBean setIco(int ico) {
        this.ico = ico;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ShopViewBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<GoodsBean.DataBean> getShoplist() {
        return shoplist;
    }

    public void setShoplist(List<GoodsBean.DataBean> shoplist) {
        this.shoplist = shoplist;
    }
}
