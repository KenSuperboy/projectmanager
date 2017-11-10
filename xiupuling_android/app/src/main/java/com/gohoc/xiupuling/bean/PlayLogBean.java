package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/6/1.
 */

public class PlayLogBean {


    /**
     * message : 查询成功
     * data : [{"cycle_cnt":30,"daydtllist":[{"cyclecnt":30,"termdate":"20161030"}],"end_date":"20161030","start_date":"20161024","trans_amount":0},{"cycle_cnt":0,"daydtllist":[],"end_date":"20161023","start_date":"20161022","trans_amount":0}]
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
         * cycle_cnt : 30
         * daydtllist : [{"cyclecnt":30,"termdate":"20161030"}]
         * end_date : 20161030
         * start_date : 20161024
         * trans_amount : 0
         */

        private int cycle_cnt;
        private String end_date;
        private String start_date;
        private int trans_amount;
        private List<DaydtllistBean> daydtllist;

        public int getCycle_cnt() {
            return cycle_cnt;
        }

        public void setCycle_cnt(int cycle_cnt) {
            this.cycle_cnt = cycle_cnt;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public int getTrans_amount() {
            return trans_amount;
        }

        public void setTrans_amount(int trans_amount) {
            this.trans_amount = trans_amount;
        }

        public List<DaydtllistBean> getDaydtllist() {
            return daydtllist;
        }

        public void setDaydtllist(List<DaydtllistBean> daydtllist) {
            this.daydtllist = daydtllist;
        }

        public static class DaydtllistBean {
            /**
             * cyclecnt : 30
             * termdate : 20161030
             */

            private int cyclecnt;
            private String termdate;

            public int getCyclecnt() {
                return cyclecnt;
            }

            public void setCyclecnt(int cyclecnt) {
                this.cyclecnt = cyclecnt;
            }

            public String getTermdate() {
                return termdate;
            }

            public void setTermdate(String termdate) {
                this.termdate = termdate;
            }
        }
    }
}
