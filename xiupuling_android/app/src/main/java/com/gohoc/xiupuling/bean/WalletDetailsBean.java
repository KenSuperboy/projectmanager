package com.gohoc.xiupuling.bean;

/**
 * Created by 28713 on 2017/5/16.
 */

public class WalletDetailsBean {


    /**
     * message : 查询成功
     * data : {"account_id":"732685303ace4bdb98d85678007d6b75","buyer_account_id":"c49b4afc702f4edfa8c4be81e001ff6a","create_time":"2017-07-29 17:23:03","pay_time":"2017-07-29 17:23:12","src_type":2,"trade_amount":1,"trade_id":"92da0d65af1741a9a0dcb75ce5d23429","trade_name":"卡拉卡拉1111-海报10(稿酬)","trade_status":"已存入我的钱包","trade_target_name":"我的名字","trade_type":"作品稿酬收入"}
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

    public static class DataBean {
        /**
         * account_id : 732685303ace4bdb98d85678007d6b75
         * buyer_account_id : c49b4afc702f4edfa8c4be81e001ff6a
         * create_time : 2017-07-29 17:23:03
         * pay_time : 2017-07-29 17:23:12
         * src_type : 2
         * trade_amount : 1
         * trade_id : 92da0d65af1741a9a0dcb75ce5d23429
         * trade_name : 卡拉卡拉1111-海报10(稿酬)
         * trade_status : 已存入我的钱包
         * trade_target_name : 我的名字
         * trade_type : 作品稿酬收入
         */

        private String account_id;
        private String buyer_account_id;
        private String create_time;
        private String pay_time;
        private int src_type;
        private int trade_amount;
        private String trade_id;
        private String trade_name;
        private String trade_status;
        private String trade_target_name;
        private String trade_type;
        private String range_type_name;
        private String fundroute;
        private String work_type_name;
        private String bank_name;

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getWork_type_name() {
            return work_type_name;
        }

        public void setWork_type_name(String work_type_name) {
            this.work_type_name = work_type_name;
        }

        public String getFundroute() {
            return fundroute;
        }

        public void setFundroute(String fundroute) {
            this.fundroute = fundroute;
        }

        public String getRange_type_name() {
            return range_type_name;
        }

        public void setRange_type_name(String range_type_name) {
            this.range_type_name = range_type_name;
        }

        public String getAccount_id() {
            return account_id;
        }

        public void setAccount_id(String account_id) {
            this.account_id = account_id;
        }

        public String getBuyer_account_id() {
            return buyer_account_id;
        }

        public void setBuyer_account_id(String buyer_account_id) {
            this.buyer_account_id = buyer_account_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public int getSrc_type() {
            return src_type;
        }

        public void setSrc_type(int src_type) {
            this.src_type = src_type;
        }

        public int getTrade_amount() {
            return trade_amount;
        }

        public void setTrade_amount(int trade_amount) {
            this.trade_amount = trade_amount;
        }

        public String getTrade_id() {
            return trade_id;
        }

        public void setTrade_id(String trade_id) {
            this.trade_id = trade_id;
        }

        public String getTrade_name() {
            return trade_name;
        }

        public void setTrade_name(String trade_name) {
            this.trade_name = trade_name;
        }

        public String getTrade_status() {
            return trade_status;
        }

        public void setTrade_status(String trade_status) {
            this.trade_status = trade_status;
        }

        public String getTrade_target_name() {
            return trade_target_name;
        }

        public void setTrade_target_name(String trade_target_name) {
            this.trade_target_name = trade_target_name;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }
    }
}
