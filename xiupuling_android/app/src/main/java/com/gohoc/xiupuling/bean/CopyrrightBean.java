package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/4/7.
 */

public class CopyrrightBean implements Serializable {


    /**
     * message : 查询成功
     * data : [{"pay_time":"2017-06-07 17:48:00","trade_amount":-1,"trade_direct":"购买","trader_name":"朝天椒","work_name":"卡拉卡拉"},{"pay_time":"2017-06-07 17:39:19","trade_amount":-10,"trade_direct":"购买","trader_name":"朝天椒","work_name":"卡拉卡拉"}]
     * code : 1
     */

    private String message;
    private int code;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * pay_time : 2017-06-07 17:48:00
         * trade_amount : -1
         * trade_direct : 购买
         * trader_name : 朝天椒
         * work_name : 卡拉卡拉
         */

        private String pay_time;
        private int trade_amount;
        private String trade_direct;
        private String trader_name;
        private String work_name;

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public int getTrade_amount() {
            return trade_amount;
        }

        public void setTrade_amount(int trade_amount) {
            this.trade_amount = trade_amount;
        }

        public String getTrade_direct() {
            return trade_direct;
        }

        public void setTrade_direct(String trade_direct) {
            this.trade_direct = trade_direct;
        }

        public String getTrader_name() {
            return trader_name;
        }

        public void setTrader_name(String trader_name) {
            this.trader_name = trader_name;
        }

        public String getWork_name() {
            return work_name;
        }

        public void setWork_name(String work_name) {
            this.work_name = work_name;
        }
    }
}
