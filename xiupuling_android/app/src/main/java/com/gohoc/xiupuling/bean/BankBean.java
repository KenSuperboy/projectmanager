package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/4/12.
 */

public class BankBean implements Serializable{

    /**
     * message : 查询成功
     * data : [{"bank_icon":"xpl/ios/bank/ICBC.png","bank_id":1000,"bank_name":"工商银行"},{"bank_icon":"xpl/ios/bank/CCB.png","bank_id":1001,"bank_name":"建设银行"},{"bank_icon":"xpl/ios/bank/BOC.png","bank_id":1002,"bank_name":"中国银行"},{"bank_icon":"xpl/ios/bank/ABC.png","bank_id":1003,"bank_name":"农业银行"},{"bank_icon":"xpl/ios/bank/BCM.png","bank_id":1004,"bank_name":"交通银行"},{"bank_icon":"xpl/ios/bank/CMB.png","bank_id":1005,"bank_name":"招商银行"},{"bank_icon":"xpl/ios/bank/PSBC.png","bank_id":1006,"bank_name":"邮政储蓄银行"},{"bank_icon":"xpl/ios/bank/CCB2.png","bank_id":1007,"bank_name":"中信银行"},{"bank_icon":"xpl/ios/bank/CEB.png","bank_id":1008,"bank_name":"光大银行"},{"bank_icon":"xpl/ios/bank/CMBC.png","bank_id":1009,"bank_name":"民生银行"},{"bank_icon":"xpl/ios/bank/CIB.png","bank_id":1010,"bank_name":"兴业银行"},{"bank_icon":"xpl/ios/bank/HXB.png","bank_id":1011,"bank_name":"华夏银行"},{"bank_icon":"xpl/ios/bank/SPDB.png","bank_id":1012,"bank_name":"上海浦东发展银行"},{"bank_icon":"xpl/ios/bank/SDB.png","bank_id":1013,"bank_name":"深圳发展银行"},{"bank_icon":"xpl/ios/bank/GDB.png","bank_id":1014,"bank_name":"广发银行"},{"bank_icon":"xpl/ios/bank/shanghai.png","bank_id":1015,"bank_name":"上海银行"},{"bank_icon":"xpl/ios/bank/pingan.png","bank_id":1016,"bank_name":"平安银行"},{"bank_icon":"xpl/ios/bank/BOB.png","bank_id":1017,"bank_name":"北京银行"},{"bank_icon":"xpl/ios/bank/CBHB.png","bank_id":1018,"bank_name":"渤海银行"}]
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

    public static class DataBean implements Serializable {
        /**
         * bank_icon : xpl/ios/bank/ICBC.png
         * bank_id : 1000
         * bank_name : 工商银行
         */

        private String bank_icon;
        private int bank_id;
        private String bank_name;

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

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }
    }
}
