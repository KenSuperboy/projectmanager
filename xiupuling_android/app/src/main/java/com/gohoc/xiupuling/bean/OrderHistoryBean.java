package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/4/7.
 */

public class OrderHistoryBean implements Serializable{

    /**
     * message : 查询成功
     * data : [{"amount":100,"terminal_no":"01","terminal_id":"terminal012","range_id":"range001","activity_title":"绿茶餐厅（海岸城店） 2 月开店大酬宾-世纪活动大呈现","create_time":"2016-05-1000:00:00","work_id":"pt007","material_store_url":"绿茶作品 05.jpg","star_level":1,"shop_name":"满记甜品（海岸城店） ","totalamount":160}]
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
         * amount : 100
         * terminal_no : 01
         * terminal_id : terminal012
         * range_id : range001
         * activity_title : 绿茶餐厅（海岸城店） 2 月开店大酬宾-世纪活动大呈现
         * create_time : 2016-05-1000:00:00
         * work_id : pt007
         * material_store_url : 绿茶作品 05.jpg
         * star_level : 1
         * shop_name : 满记甜品（海岸城店）
         * totalamount : 160
         */

        private int amount;
        private String terminal_no;
        private String terminal_id;
        private String range_id;
        private String activity_title;
        private String create_time;
        private String work_id;
        private String material_store_url;
        private int star_level;
        private String shop_name;
        private int totalamount;
        private String work_name;

        public String getWork_name() {
            return work_name;
        }

        public void setWork_name(String work_name) {
            this.work_name = work_name;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getTerminal_no() {
            return terminal_no;
        }

        public void setTerminal_no(String terminal_no) {
            this.terminal_no = terminal_no;
        }

        public String getTerminal_id() {
            return terminal_id;
        }

        public void setTerminal_id(String terminal_id) {
            this.terminal_id = terminal_id;
        }

        public String getRange_id() {
            return range_id;
        }

        public void setRange_id(String range_id) {
            this.range_id = range_id;
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

        public String getWork_id() {
            return work_id;
        }

        public void setWork_id(String work_id) {
            this.work_id = work_id;
        }

        public String getMaterial_store_url() {
            return material_store_url;
        }

        public void setMaterial_store_url(String material_store_url) {
            this.material_store_url = material_store_url;
        }

        public int getStar_level() {
            return star_level;
        }

        public void setStar_level(int star_level) {
            this.star_level = star_level;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public int getTotalamount() {
            return totalamount;
        }

        public void setTotalamount(int totalamount) {
            this.totalamount = totalamount;
        }
    }
}
