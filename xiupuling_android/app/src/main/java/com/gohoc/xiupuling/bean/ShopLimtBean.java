package com.gohoc.xiupuling.bean;

/**
 * Created by 28713 on 2017/6/23.
 */

public class ShopLimtBean {


    /**
     * code : 999
     * message : 门店数已达上限，请购买后继续
     * shopcnt : 3
     * shopowncnt : 3
     */

    private int code;
    private String message;
    private int shopcnt;
    private int shopowncnt;
    private int terminalcnt;
    private  int terminalowncnt;

    public int getTerminalcnt() {
        return terminalcnt;
    }

    public void setTerminalcnt(int terminalcnt) {
        this.terminalcnt = terminalcnt;
    }

    public int getTerminalowncnt() {
        return terminalowncnt;
    }

    public void setTerminalowncnt(int terminalowncnt) {
        this.terminalowncnt = terminalowncnt;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getShopcnt() {
        return shopcnt;
    }

    public void setShopcnt(int shopcnt) {
        this.shopcnt = shopcnt;
    }

    public int getShopowncnt() {
        return shopowncnt;
    }

    public void setShopowncnt(int shopowncnt) {
        this.shopowncnt = shopowncnt;
    }
}
