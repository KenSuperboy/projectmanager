package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/5/20.
 */

public class CreatRqResultBean implements Serializable{


    /**
     * message : 添加成功
     * data : {"activity_onoff":1,"activity_title":"啦啦啦","create_time":"2017-05-20","is_pay_money":0,"is_show":0,"is_show_phone":1,"mobilephone":"15018546971","rq_design_spec":"喝了点","rq_id":"f30803e486be49a481ec5db97c175fb5","rq_intention_money":0,"rq_opening":0,"rq_orientation":0,"rq_telephone":"15018546971","rq_type":1,"user_id":"9c9bd30381ff4198addf8a8938996705"}
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
         * activity_onoff : 1
         * activity_title : 啦啦啦
         * create_time : 2017-05-20
         * is_pay_money : 0
         * is_show : 0
         * is_show_phone : 1
         * mobilephone : 15018546971
         * rq_design_spec : 喝了点
         * rq_id : f30803e486be49a481ec5db97c175fb5
         * rq_intention_money : 0
         * rq_opening : 0
         * rq_orientation : 0
         * rq_telephone : 15018546971
         * rq_type : 1
         * user_id : 9c9bd30381ff4198addf8a8938996705
         */

        private int activity_onoff;
        private String activity_title;
        private String create_time;
        private int is_pay_money;
        private int is_show;
        private int is_show_phone;
        private String mobilephone;
        private String rq_design_spec;
        private String rq_id;
        private int rq_intention_money;
        private int rq_opening;
        private int rq_orientation;
        private String rq_telephone;
        private int rq_type;
        private String user_id;

        public int getActivity_onoff() {
            return activity_onoff;
        }

        public void setActivity_onoff(int activity_onoff) {
            this.activity_onoff = activity_onoff;
        }

        public String getActivity_title() {
            return activity_title;
        }

        public void setActivity_title(String activity_title) {
            this.activity_title = activity_title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getIs_pay_money() {
            return is_pay_money;
        }

        public void setIs_pay_money(int is_pay_money) {
            this.is_pay_money = is_pay_money;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public int getIs_show_phone() {
            return is_show_phone;
        }

        public void setIs_show_phone(int is_show_phone) {
            this.is_show_phone = is_show_phone;
        }

        public String getMobilephone() {
            return mobilephone;
        }

        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }

        public String getRq_design_spec() {
            return rq_design_spec;
        }

        public void setRq_design_spec(String rq_design_spec) {
            this.rq_design_spec = rq_design_spec;
        }

        public String getRq_id() {
            return rq_id;
        }

        public void setRq_id(String rq_id) {
            this.rq_id = rq_id;
        }

        public int getRq_intention_money() {
            return rq_intention_money;
        }

        public void setRq_intention_money(int rq_intention_money) {
            this.rq_intention_money = rq_intention_money;
        }

        public int getRq_opening() {
            return rq_opening;
        }

        public void setRq_opening(int rq_opening) {
            this.rq_opening = rq_opening;
        }

        public int getRq_orientation() {
            return rq_orientation;
        }

        public void setRq_orientation(int rq_orientation) {
            this.rq_orientation = rq_orientation;
        }

        public String getRq_telephone() {
            return rq_telephone;
        }

        public void setRq_telephone(String rq_telephone) {
            this.rq_telephone = rq_telephone;
        }

        public int getRq_type() {
            return rq_type;
        }

        public void setRq_type(int rq_type) {
            this.rq_type = rq_type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
