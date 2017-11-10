package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/5/2.
 */

public class PushHistory implements Serializable{


    /**
     * message : 查询成功
     * data : [{"activity_brand":"换个方法","activity_content":"欢迎观影","activity_detail":"欢迎大家前来观看","activity_nav_content":"南山区海岸城家乐福超市东侧（海岸城保利文化广场负一楼B143号）","activity_nav_latitude":22.522191,"activity_nav_longitude":113.943637,"activity_nav_type":0,"activity_onoff":0,"activity_title":"魔兽海报-竖版","attention_count":0,"copyright_price":32,"create_time":"2016-08-02 14:39:12","cycle_cnt":0,"delete_time":null,"info_id":"bb68402db8fe4b098c2596006c0b9e93","is_pay_money":0,"is_show":0,"is_show_phone":0,"material_store_url":"魔兽竖02.jpg","nick_name":"五大三粗","orientation":1,"playtime":30,"rq_design_spec":"","rq_id":"xq013","rq_intention_money":0,"rq_opening":0,"rq_orientation":1,"rq_tag":null,"rq_telephone":"","rq_type":1,"terminal_cnt":6,"user_id":"15dc1ad0e136471d82057368a737d67c","week_per_term":1,"work_count":5,"work_id":"pt018","work_type":1,"xpl_status":null}]
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
         * activity_brand : 换个方法
         * activity_content : 欢迎观影
         * activity_detail : 欢迎大家前来观看
         * activity_nav_content : 南山区海岸城家乐福超市东侧（海岸城保利文化广场负一楼B143号）
         * activity_nav_latitude : 22.522191
         * activity_nav_longitude : 113.943637
         * activity_nav_type : 0
         * activity_onoff : 0
         * activity_title : 魔兽海报-竖版
         * attention_count : 0
         * copyright_price : 32.0
         * create_time : 2016-08-02 14:39:12
         * cycle_cnt : 0
         * delete_time : null
         * info_id : bb68402db8fe4b098c2596006c0b9e93
         * is_pay_money : 0
         * is_show : 0
         * is_show_phone : 0
         * material_store_url : 魔兽竖02.jpg
         * nick_name : 五大三粗
         * orientation : 1
         * playtime : 30
         * rq_design_spec :
         * rq_id : xq013
         * rq_intention_money : 0.0
         * rq_opening : 0
         * rq_orientation : 1
         * rq_tag : null
         * rq_telephone :
         * rq_type : 1
         * terminal_cnt : 6
         * user_id : 15dc1ad0e136471d82057368a737d67c
         * week_per_term : 1.0
         * work_count : 5
         * work_id : pt018
         * work_type : 1
         * xpl_status : null
         */

        private String activity_brand;
        private String activity_content;
        private String activity_detail;
        private String activity_nav_content;
        private double activity_nav_latitude;
        private double activity_nav_longitude;
        private int activity_nav_type;
        private int activity_onoff;
        private String activity_title;
        private int attention_count;
        private double copyright_price;
        private String create_time;
        private int cycle_cnt;
        private Object delete_time;
        private String info_id;
        private int is_pay_money;
        private int is_show;
        private int is_show_phone;
        private String material_store_url;
        private String nick_name;
        private int orientation;
        private int playtime;
        private String rq_design_spec;
        private String rq_id;
        private double rq_intention_money;
        private int rq_opening;
        private int rq_orientation;
        private Object rq_tag;
        private String rq_telephone;
        private int rq_type;
        private int terminal_cnt;
        private String user_id;
        private double week_per_term;
        private int work_count;
        private String work_id;
        private int work_type;
        private Object xpl_status;
        private String work_name;

        public String getWork_name() {
            return work_name;
        }

        public void setWork_name(String work_name) {
            this.work_name = work_name;
        }

        public String getActivity_brand() {
            return activity_brand;
        }

        public void setActivity_brand(String activity_brand) {
            this.activity_brand = activity_brand;
        }

        public String getActivity_content() {
            return activity_content;
        }

        public void setActivity_content(String activity_content) {
            this.activity_content = activity_content;
        }

        public String getActivity_detail() {
            return activity_detail;
        }

        public void setActivity_detail(String activity_detail) {
            this.activity_detail = activity_detail;
        }

        public String getActivity_nav_content() {
            return activity_nav_content;
        }

        public void setActivity_nav_content(String activity_nav_content) {
            this.activity_nav_content = activity_nav_content;
        }

        public double getActivity_nav_latitude() {
            return activity_nav_latitude;
        }

        public void setActivity_nav_latitude(double activity_nav_latitude) {
            this.activity_nav_latitude = activity_nav_latitude;
        }

        public double getActivity_nav_longitude() {
            return activity_nav_longitude;
        }

        public void setActivity_nav_longitude(double activity_nav_longitude) {
            this.activity_nav_longitude = activity_nav_longitude;
        }

        public int getActivity_nav_type() {
            return activity_nav_type;
        }

        public void setActivity_nav_type(int activity_nav_type) {
            this.activity_nav_type = activity_nav_type;
        }

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

        public int getAttention_count() {
            return attention_count;
        }

        public void setAttention_count(int attention_count) {
            this.attention_count = attention_count;
        }

        public double getCopyright_price() {
            return copyright_price;
        }

        public void setCopyright_price(double copyright_price) {
            this.copyright_price = copyright_price;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getCycle_cnt() {
            return cycle_cnt;
        }

        public void setCycle_cnt(int cycle_cnt) {
            this.cycle_cnt = cycle_cnt;
        }

        public Object getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(Object delete_time) {
            this.delete_time = delete_time;
        }

        public String getInfo_id() {
            return info_id;
        }

        public void setInfo_id(String info_id) {
            this.info_id = info_id;
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

        public String getMaterial_store_url() {
            return material_store_url;
        }

        public void setMaterial_store_url(String material_store_url) {
            this.material_store_url = material_store_url;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getOrientation() {
            return orientation;
        }

        public void setOrientation(int orientation) {
            this.orientation = orientation;
        }

        public int getPlaytime() {
            return playtime;
        }

        public void setPlaytime(int playtime) {
            this.playtime = playtime;
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

        public double getRq_intention_money() {
            return rq_intention_money;
        }

        public void setRq_intention_money(double rq_intention_money) {
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

        public Object getRq_tag() {
            return rq_tag;
        }

        public void setRq_tag(Object rq_tag) {
            this.rq_tag = rq_tag;
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

        public int getTerminal_cnt() {
            return terminal_cnt;
        }

        public void setTerminal_cnt(int terminal_cnt) {
            this.terminal_cnt = terminal_cnt;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public double getWeek_per_term() {
            return week_per_term;
        }

        public void setWeek_per_term(double week_per_term) {
            this.week_per_term = week_per_term;
        }

        public int getWork_count() {
            return work_count;
        }

        public void setWork_count(int work_count) {
            this.work_count = work_count;
        }

        public String getWork_id() {
            return work_id;
        }

        public void setWork_id(String work_id) {
            this.work_id = work_id;
        }

        public int getWork_type() {
            return work_type;
        }

        public void setWork_type(int work_type) {
            this.work_type = work_type;
        }

        public Object getXpl_status() {
            return xpl_status;
        }

        public void setXpl_status(Object xpl_status) {
            this.xpl_status = xpl_status;
        }
    }
}
