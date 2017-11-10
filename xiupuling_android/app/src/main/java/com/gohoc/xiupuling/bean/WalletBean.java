package com.gohoc.xiupuling.bean;

/**
 * Created by 28713 on 2017/4/10.
 */

public class WalletBean {

    /**
     * message : 查询成功
     * expectincome : 0
     * balance : 0
     * code : 1
     */

    private String message;
    private int expectincome;
    private double balance;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getExpectincome() {
        return expectincome;
    }

    public void setExpectincome(int expectincome) {
        this.expectincome = expectincome;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
