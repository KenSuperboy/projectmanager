package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/2/13.
 */

public class ShopBean implements Serializable{


    /**
     * message : 查询成功
     * data : [{"shop_id":"shop009","shop_name":"许留山（深圳欢乐颂店） ","shop_star_level":5,"termlist":[{"furtherorder":1,"online":0,"showtime":2342,"term_internet_method":null,"term_orientation":1,"terminal_id":"terminal012","terminal_no":"01","total_cycle_cnt":248,"totalamt":10,"totalplaytime":0},{"furtherorder":1,"online":0,"showtime":0,"term_internet_method":null,"term_orientation":0,"terminal_id":"terminal034","terminal_no":"06","total_cycle_cnt":0,"totalamt":10,"totalplaytime":0}],"totalorderamt":20}]
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
         * shop_id : shop009
         * shop_name : 许留山（深圳欢乐颂店）
         * shop_star_level : 5
         * termlist : [{"furtherorder":1,"online":0,"showtime":2342,"term_internet_method":null,"term_orientation":1,"terminal_id":"terminal012","terminal_no":"01","total_cycle_cnt":248,"totalamt":10,"totalplaytime":0},{"furtherorder":1,"online":0,"showtime":0,"term_internet_method":null,"term_orientation":0,"terminal_id":"terminal034","terminal_no":"06","total_cycle_cnt":0,"totalamt":10,"totalplaytime":0}]
         * totalorderamt : 20
         */

        private String shop_id;
        private String shop_name;
        private int shop_star_level;
        private int totalorderamt;
        private List<TermlistBean> termlist;
        public boolean mExpanded = false;
        private boolean isSelect=false;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public boolean ismExpanded() {
            return mExpanded;
        }

        public void setmExpanded(boolean mExpanded) {
            this.mExpanded = mExpanded;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public int getShop_star_level() {
            return shop_star_level;
        }

        public void setShop_star_level(int shop_star_level) {
            this.shop_star_level = shop_star_level;
        }

        public int getTotalorderamt() {
            return totalorderamt;
        }

        public void setTotalorderamt(int totalorderamt) {
            this.totalorderamt = totalorderamt;
        }

        public List<TermlistBean> getTermlist() {
            return termlist;
        }

        public void setTermlist(List<TermlistBean> termlist) {
            this.termlist = termlist;
        }


        public static class TermlistBean implements  Serializable{
            /**
             * furtherorder : 1
             * online : 0
             * showtime : 2342
             * term_internet_method : null
             * term_orientation : 1
             * terminal_id : terminal012
             * terminal_no : 01
             * total_cycle_cnt : 248
             * totalamt : 10
             * totalplaytime : 0
             */

            private int furtherorder;
            private int online;
            private int showtime;
            private Object term_internet_method;
            private int term_orientation;
            private String terminal_id;
            private String terminal_no;
            private int total_cycle_cnt;
            private int totalamt;
            private int totalplaytime;
            private boolean isLast;

            public boolean isLast() {
                return isLast;
            }

            public void setLast(boolean last) {
                isLast = last;
            }

            public int getFurtherorder() {
                return furtherorder;
            }

            public void setFurtherorder(int furtherorder) {
                this.furtherorder = furtherorder;
            }

            public int getOnline() {
                return online;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public int getShowtime() {
                return showtime;
            }

            public void setShowtime(int showtime) {
                this.showtime = showtime;
            }

            public Object getTerm_internet_method() {
                return term_internet_method;
            }

            public void setTerm_internet_method(Object term_internet_method) {
                this.term_internet_method = term_internet_method;
            }

            public int getTerm_orientation() {
                return term_orientation;
            }

            public void setTerm_orientation(int term_orientation) {
                this.term_orientation = term_orientation;
            }

            public String getTerminal_id() {
                return terminal_id;
            }

            public void setTerminal_id(String terminal_id) {
                this.terminal_id = terminal_id;
            }

            public String getTerminal_no() {
                return terminal_no;
            }

            public void setTerminal_no(String terminal_no) {
                this.terminal_no = terminal_no;
            }

            public int getTotal_cycle_cnt() {
                return total_cycle_cnt;
            }

            public void setTotal_cycle_cnt(int total_cycle_cnt) {
                this.total_cycle_cnt = total_cycle_cnt;
            }

            public int getTotalamt() {
                return totalamt;
            }

            public void setTotalamt(int totalamt) {
                this.totalamt = totalamt;
            }

            public int getTotalplaytime() {
                return totalplaytime;
            }

            public void setTotalplaytime(int totalplaytime) {
                this.totalplaytime = totalplaytime;
            }
        }
    }
}
