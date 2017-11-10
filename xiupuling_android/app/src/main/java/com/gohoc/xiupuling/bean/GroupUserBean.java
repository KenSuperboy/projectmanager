package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/3/10.
 */

public class GroupUserBean {


    /**
     * message : 查询成功
     * data : [{"nick_name":"朝天椒","portrait_url":"personal14886885894819805016caa2457a7199dfd5e4c30e0b6.jpg","terminal_cnt":2,"union_id":"cafc0d94cdde4b5eae143d5fd93dff4b","user_id":"15dc1ad0e136471d82057368a737d67c"},{"nick_name":"张无忌","portrait_url":"user001.jpg","terminal_cnt":3,"union_id":"cafc0d94cdde4b5eae143d5fd93dff4b","user_id":"7c7f36aed0ff4b8ca2ad736258a3c466"}]
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
         * nick_name : 朝天椒
         * portrait_url : personal14886885894819805016caa2457a7199dfd5e4c30e0b6.jpg
         * terminal_cnt : 2
         * union_id : cafc0d94cdde4b5eae143d5fd93dff4b
         * user_id : 15dc1ad0e136471d82057368a737d67c
         */

        private String nick_name;
        private String portrait_url;
        private int terminal_cnt;
        private String union_id;
        private String user_id;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getPortrait_url() {
            return portrait_url;
        }

        public void setPortrait_url(String portrait_url) {
            this.portrait_url = portrait_url;
        }

        public int getTerminal_cnt() {
            return terminal_cnt;
        }

        public void setTerminal_cnt(int terminal_cnt) {
            this.terminal_cnt = terminal_cnt;
        }

        public String getUnion_id() {
            return union_id;
        }

        public void setUnion_id(String union_id) {
            this.union_id = union_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
