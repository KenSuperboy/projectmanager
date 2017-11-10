package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/4/12.
 */

public class MyBankBean implements Serializable{

    /**
     * message : 查询成功
     * data : [{"accountname":"深圳市大奖科技有限公司","bank_icon":"xpl/ios/bank/CCB.png","bank_id":1001,"bankcardno":"557588755","bankname":"建设银行","ids":"4ae90132c9484338a52ca57f284911d7","subbranch_addr":"你是不","user_id":"15dc1ad0e136471d82057368a737d67c"}]
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
         * accountname : 深圳市大奖科技有限公司
         * bank_icon : xpl/ios/bank/CCB.png
         * bank_id : 1001
         * bankcardno : 557588755
         * bankname : 建设银行
         * ids : 4ae90132c9484338a52ca57f284911d7
         * subbranch_addr : 你是不
         * user_id : 15dc1ad0e136471d82057368a737d67c
         */

        private String accountname;
        private String bank_icon;
        private int bank_id;
        private String bankcardno;
        private String bankname;
        private String ids;
        private String subbranch_addr;
        private String user_id;
        private boolean isSelect=false;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getAccountname() {
            return accountname;
        }

        public void setAccountname(String accountname) {
            this.accountname = accountname;
        }

        public String getBank_icon() {
            return bank_icon;
        }

        public void setBank_icon(String bank_icon) {
            this.bank_icon = bank_icon;
        }

        public int getBank_id() {
            return bank_id;
        }

        public void setBank_id(int bank_id) {
            this.bank_id = bank_id;
        }

        public String getBankcardno() {
            return bankcardno;
        }

        public void setBankcardno(String bankcardno) {
            this.bankcardno = bankcardno;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getIds() {
            return ids;
        }

        public void setIds(String ids) {
            this.ids = ids;
        }

        public String getSubbranch_addr() {
            return subbranch_addr;
        }

        public void setSubbranch_addr(String subbranch_addr) {
            this.subbranch_addr = subbranch_addr;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
