package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/4/26.
 */

public class PushOrderConfimResultBean implements Serializable{

    /**
     * message : 初始化完成
     * data : {"account_id":"732685303ace4bdb98d85678007d6b75","amount":100,"create_time":"2017-04-26","refno":"20170426115600140417","status":0,"trade_id":"702fe10c6e694be6a07058e0d33c8cb5","tradename":"图片来源-图嵌视频1(投放)","transcode":0,"transtype":0}
     * code : 1
     */

    private String message;
    private DataBean data;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean implements Serializable{
        /**
         * account_id : 732685303ace4bdb98d85678007d6b75
         * amount : 100
         * create_time : 2017-04-26
         * refno : 20170426115600140417
         * status : 0
         * trade_id : 702fe10c6e694be6a07058e0d33c8cb5
         * tradename : 图片来源-图嵌视频1(投放)
         * transcode : 0
         * transtype : 0
         */

        private String account_id;
        private double amount;
        private String create_time;
        private String refno;
        private int status;
        private String trade_id;
        private String tradename;
        private int transcode;
        private int transtype;

        public String getAccount_id() {
            return account_id;
        }

        public void setAccount_id(String account_id) {
            this.account_id = account_id;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getRefno() {
            return refno;
        }

        public void setRefno(String refno) {
            this.refno = refno;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTrade_id() {
            return trade_id;
        }

        public void setTrade_id(String trade_id) {
            this.trade_id = trade_id;
        }

        public String getTradename() {
            return tradename;
        }

        public void setTradename(String tradename) {
            this.tradename = tradename;
        }

        public int getTranscode() {
            return transcode;
        }

        public void setTranscode(int transcode) {
            this.transcode = transcode;
        }

        public int getTranstype() {
            return transtype;
        }

        public void setTranstype(int transtype) {
            this.transtype = transtype;
        }
    }
}
