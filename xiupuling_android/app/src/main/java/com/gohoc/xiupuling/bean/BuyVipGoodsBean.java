package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/4/7.
 */

public class BuyVipGoodsBean implements Serializable{


    /**
     * message : 初始化完成
     * data : {"transtype":0,"amount":480,"refno":"20160508232300000003","account_id":"1","tradename":"门店+1","status":0,"trade_id":"e8b1f808a13b42aab01dbeb941d20041","create_time":"2016-05-08","transcode":0}
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
         * transtype : 0
         * amount : 480
         * refno : 20160508232300000003
         * account_id : 1
         * tradename : 门店+1
         * status : 0
         * trade_id : e8b1f808a13b42aab01dbeb941d20041
         * create_time : 2016-05-08
         * transcode : 0
         */

        private int transtype;
        private Double amount;
        private String refno;
        private String account_id;
        private String tradename;
        private int status;
        private String trade_id;
        private String create_time;
        private int transcode;

        public int getTranstype() {
            return transtype;
        }

        public void setTranstype(int transtype) {
            this.transtype = transtype;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getRefno() {
            return refno;
        }

        public void setRefno(String refno) {
            this.refno = refno;
        }

        public String getAccount_id() {
            return account_id;
        }

        public void setAccount_id(String account_id) {
            this.account_id = account_id;
        }

        public String getTradename() {
            return tradename;
        }

        public void setTradename(String tradename) {
            this.tradename = tradename;
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getTranscode() {
            return transcode;
        }

        public void setTranscode(int transcode) {
            this.transcode = transcode;
        }
    }
}
