package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/5/3.
 */

public class OrderMoneyListBean {

    /**
     * message : 查询成功
     * data : [{"end_date":"20170108","order_status":3,"range_id":"17f8f33e11bb4663b053cede8f8db575","start_date":"20170105","terminal_id":"25e0637d4e914193a85741a58c0622fb","trans_amount":0},{"end_date":"20170115","order_status":3,"range_id":"17f8f33e11bb4663b053cede8f8db575","start_date":"20170109","terminal_id":"25e0637d4e914193a85741a58c0622fb","trans_amount":0},{"end_date":"20170122","order_status":3,"range_id":"17f8f33e11bb4663b053cede8f8db575","start_date":"20170116","terminal_id":"25e0637d4e914193a85741a58c0622fb","trans_amount":0},{"end_date":"20170129","order_status":3,"range_id":"17f8f33e11bb4663b053cede8f8db575","start_date":"20170123","terminal_id":"25e0637d4e914193a85741a58c0622fb","trans_amount":0},{"end_date":"20170202","order_status":3,"range_id":"17f8f33e11bb4663b053cede8f8db575","start_date":"20170130","terminal_id":"25e0637d4e914193a85741a58c0622fb","trans_amount":0}]
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

    public static class DataBean {
        /**
         * end_date : 20170108
         * order_status : 3
         * range_id : 17f8f33e11bb4663b053cede8f8db575
         * start_date : 20170105
         * terminal_id : 25e0637d4e914193a85741a58c0622fb
         * trans_amount : 0.0
         */

        private String end_date;
        private int order_status;
        private String range_id;
        private String start_date;
        private String terminal_id;
        private double trans_amount;

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getRange_id() {
            return range_id;
        }

        public void setRange_id(String range_id) {
            this.range_id = range_id;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getTerminal_id() {
            return terminal_id;
        }

        public void setTerminal_id(String terminal_id) {
            this.terminal_id = terminal_id;
        }

        public double getTrans_amount() {
            return trans_amount;
        }

        public void setTrans_amount(double trans_amount) {
            this.trans_amount = trans_amount;
        }
    }
}
