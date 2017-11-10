package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/4/14.
 */

public class TerminalFlagBean  implements Serializable{

    /**
     * message : 查询成功
     * data : {"cycle_cnt":500,"terminal_id":"terminal013","cycle_cnt_min":300}
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
         * cycle_cnt : 500
         * terminal_id : terminal013
         * cycle_cnt_min : 300
         */

        private int cycle_cnt;
        private String terminal_id;
        private int cycle_cnt_min;

        public int getCycle_cnt() {
            return cycle_cnt;
        }

        public void setCycle_cnt(int cycle_cnt) {
            this.cycle_cnt = cycle_cnt;
        }

        public String getTerminal_id() {
            return terminal_id;
        }

        public void setTerminal_id(String terminal_id) {
            this.terminal_id = terminal_id;
        }

        public int getCycle_cnt_min() {
            return cycle_cnt_min;
        }

        public void setCycle_cnt_min(int cycle_cnt_min) {
            this.cycle_cnt_min = cycle_cnt_min;
        }
    }
}
