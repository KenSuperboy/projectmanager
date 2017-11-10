package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 28713 on 2017/4/26.
 */

public class PushGroupMenuBean implements Serializable {
    private int tyepe = 0;// 0群id  1店铺id   2群组内的终端  3店铺内终端
    private GroupBean.DataBean union;
    private ShopBean.DataBean shop;
    private ArrayList<TerminalListBean.DataBean> shopTerminalList;
    private ArrayList<GroupTermianlListBean.DataBean> groupTerminalList;
    private String shopName;
    private int groupTermianlType = 0;//群终端分类      0 是其他人的终端  1是我自己的终端
    private boolean isFree = false;
    private int terminalCount = 0;
    private String shopId; //店铺id，只在选择店铺部分终端情况下使用，前期未考虑，后新加上

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public int getGroupTermianlType() {
        return groupTermianlType;
    }

    public void setGroupTermianlType(int groupTermianlType) {
        this.groupTermianlType = groupTermianlType;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getTyepe() {
        return tyepe;
    }

    public void setTyepe(int tyepe) {
        this.tyepe = tyepe;
    }

    public GroupBean.DataBean getUnion() {
        return union;
    }

    public void setUnion(GroupBean.DataBean union) {
        this.union = union;
    }

    public ShopBean.DataBean getShop() {
        return shop;
    }

    public void setShop(ShopBean.DataBean shop) {
        this.shop = shop;
    }


    public ArrayList<TerminalListBean.DataBean> getShopTerminalList() {
        return shopTerminalList;
    }

    public void setShopTerminalList(ArrayList<TerminalListBean.DataBean> shopTerminalList) {
        this.shopTerminalList = shopTerminalList;
    }

    public ArrayList<GroupTermianlListBean.DataBean> getGroupTerminalList() {
        return groupTerminalList;
    }

    public void setGroupTerminalList(ArrayList<GroupTermianlListBean.DataBean> groupTerminalList) {
        this.groupTerminalList = groupTerminalList;
    }

    public int getTerminalCount() {
        return terminalCount;
    }

    public void setTerminalCount(int terminalCount) {
        this.terminalCount = terminalCount;
    }
}
