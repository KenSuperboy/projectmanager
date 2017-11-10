package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/3/2.
 */

public class TerminalListBean implements Serializable{


    /**
     * message : 查询成功
     * data : [{"create_time":"2017-03-02 10:40:41","force_xpl":false,"repeat_weekday":"0000000","rsa_private":null,"rsa_public":null,"schedule_up_down":false,"shop_id":"006bf5f2c93d48128af8ca826bc32532","take_order":true,"term_dun_id":null,"term_dun_pwd":null,"term_internet_method":null,"term_orientation":1,"term_rsa_public":null,"term_show_seats":18,"term_soft_version":"1","term_wifi_pwd":null,"term_wifi_ssid":null,"term_wifi_verify_type":null,"terminal_id":"696f664bf6ac4101965d27b548bd4481","terminal_no":"01","terminal_type":null,"up_end_time":null,"up_start_time":null,"user_id":"15dc1ad0e136471d82057368a737d67c"}]
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
         * create_time : 2017-03-02 10:40:41
         * force_xpl : false
         * repeat_weekday : 0000000
         * rsa_private : null
         * rsa_public : null
         * schedule_up_down : false
         * shop_id : 006bf5f2c93d48128af8ca826bc32532
         * take_order : true
         * term_dun_id : null
         * term_dun_pwd : null
         * term_internet_method : null
         * term_orientation : 1
         * term_rsa_public : null
         * term_show_seats : 18
         * term_soft_version : 1
         * term_wifi_pwd : null
         * term_wifi_ssid : null
         * term_wifi_verify_type : null
         * terminal_id : 696f664bf6ac4101965d27b548bd4481
         * terminal_no : 01
         * terminal_type : null
         * up_end_time : null
         * up_start_time : null
         * user_id : 15dc1ad0e136471d82057368a737d67c
         */

        private String create_time;
        private boolean force_xpl;
        private String repeat_weekday;
        private Object rsa_private;
        private Object rsa_public;
        private boolean schedule_up_down;
        private String shop_id;
        private boolean take_order;
        private Object term_dun_id;
        private Object term_dun_pwd;
        private Object term_internet_method;
        private int term_orientation;
        private Object term_rsa_public;
        private int term_show_seats;
        private String term_soft_version;
        private Object term_wifi_pwd;
        private Object term_wifi_ssid;
        private Object term_wifi_verify_type;
        private String terminal_id;
        private String terminal_no;
        private Object terminal_type;
        private Object up_end_time;
        private Object up_start_time;
        private String user_id;
        private boolean isCheck=false;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public boolean isForce_xpl() {
            return force_xpl;
        }

        public void setForce_xpl(boolean force_xpl) {
            this.force_xpl = force_xpl;
        }

        public String getRepeat_weekday() {
            return repeat_weekday;
        }

        public void setRepeat_weekday(String repeat_weekday) {
            this.repeat_weekday = repeat_weekday;
        }

        public Object getRsa_private() {
            return rsa_private;
        }

        public void setRsa_private(Object rsa_private) {
            this.rsa_private = rsa_private;
        }

        public Object getRsa_public() {
            return rsa_public;
        }

        public void setRsa_public(Object rsa_public) {
            this.rsa_public = rsa_public;
        }

        public boolean isSchedule_up_down() {
            return schedule_up_down;
        }

        public void setSchedule_up_down(boolean schedule_up_down) {
            this.schedule_up_down = schedule_up_down;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public boolean isTake_order() {
            return take_order;
        }

        public void setTake_order(boolean take_order) {
            this.take_order = take_order;
        }

        public Object getTerm_dun_id() {
            return term_dun_id;
        }

        public void setTerm_dun_id(Object term_dun_id) {
            this.term_dun_id = term_dun_id;
        }

        public Object getTerm_dun_pwd() {
            return term_dun_pwd;
        }

        public void setTerm_dun_pwd(Object term_dun_pwd) {
            this.term_dun_pwd = term_dun_pwd;
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

        public Object getTerm_rsa_public() {
            return term_rsa_public;
        }

        public void setTerm_rsa_public(Object term_rsa_public) {
            this.term_rsa_public = term_rsa_public;
        }

        public int getTerm_show_seats() {
            return term_show_seats;
        }

        public void setTerm_show_seats(int term_show_seats) {
            this.term_show_seats = term_show_seats;
        }

        public String getTerm_soft_version() {
            return term_soft_version;
        }

        public void setTerm_soft_version(String term_soft_version) {
            this.term_soft_version = term_soft_version;
        }

        public Object getTerm_wifi_pwd() {
            return term_wifi_pwd;
        }

        public void setTerm_wifi_pwd(Object term_wifi_pwd) {
            this.term_wifi_pwd = term_wifi_pwd;
        }

        public Object getTerm_wifi_ssid() {
            return term_wifi_ssid;
        }

        public void setTerm_wifi_ssid(Object term_wifi_ssid) {
            this.term_wifi_ssid = term_wifi_ssid;
        }

        public Object getTerm_wifi_verify_type() {
            return term_wifi_verify_type;
        }

        public void setTerm_wifi_verify_type(Object term_wifi_verify_type) {
            this.term_wifi_verify_type = term_wifi_verify_type;
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

        public Object getTerminal_type() {
            return terminal_type;
        }

        public void setTerminal_type(Object terminal_type) {
            this.terminal_type = terminal_type;
        }

        public Object getUp_end_time() {
            return up_end_time;
        }

        public void setUp_end_time(Object up_end_time) {
            this.up_end_time = up_end_time;
        }

        public Object getUp_start_time() {
            return up_start_time;
        }

        public void setUp_start_time(Object up_start_time) {
            this.up_start_time = up_start_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
