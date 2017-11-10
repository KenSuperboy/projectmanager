package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/2/27.
 */

public class AddShopResultBean implements Serializable {


    /**
     * message : 添加成功
     * data : {"create_time":"2017-02-27","shop_addr_latitude":22.55446184584557,"shop_addr_longitude":114.09536380540014,"shop_address":"深圳福田区红荔路38号群星广场C座1楼(华强北路口)","shop_business_id":10045,"shop_group_id":10604,"shop_id":"c8e16f4241ba47e3bbfb88e54409e183","shop_name":"你好","shop_region_city":4403,"shop_region_id":440306,"shop_region_province":44,"shop_star_level":"1","shop_telephone":"15018546971","user_id":"15dc1ad0e136471d82057368a737d67c"}
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
         * create_time : 2017-02-27
         * shop_addr_latitude : 22.55446184584557
         * shop_addr_longitude : 114.09536380540014
         * shop_address : 深圳福田区红荔路38号群星广场C座1楼(华强北路口)
         * shop_business_id : 10045
         * shop_group_id : 10604
         * shop_id : c8e16f4241ba47e3bbfb88e54409e183
         * shop_name : 你好
         * shop_region_city : 4403
         * shop_region_id : 440306
         * shop_region_province : 44
         * shop_star_level : 1
         * shop_telephone : 15018546971
         * user_id : 15dc1ad0e136471d82057368a737d67c
         */

        private String create_time;
        private double shop_addr_latitude;
        private double shop_addr_longitude;
        private String shop_address;
        private int shop_business_id;
        private int shop_group_id;
        private String shop_id;
        private String shop_name;
        private int shop_region_city;
        private int shop_region_id;
        private int shop_region_province;
        private String shop_star_level;
        private String shop_telephone;
        private String user_id;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public double getShop_addr_latitude() {
            return shop_addr_latitude;
        }

        public void setShop_addr_latitude(double shop_addr_latitude) {
            this.shop_addr_latitude = shop_addr_latitude;
        }

        public double getShop_addr_longitude() {
            return shop_addr_longitude;
        }

        public void setShop_addr_longitude(double shop_addr_longitude) {
            this.shop_addr_longitude = shop_addr_longitude;
        }

        public String getShop_address() {
            return shop_address;
        }

        public void setShop_address(String shop_address) {
            this.shop_address = shop_address;
        }

        public int getShop_business_id() {
            return shop_business_id;
        }

        public void setShop_business_id(int shop_business_id) {
            this.shop_business_id = shop_business_id;
        }

        public int getShop_group_id() {
            return shop_group_id;
        }

        public void setShop_group_id(int shop_group_id) {
            this.shop_group_id = shop_group_id;
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

        public int getShop_region_city() {
            return shop_region_city;
        }

        public void setShop_region_city(int shop_region_city) {
            this.shop_region_city = shop_region_city;
        }

        public int getShop_region_id() {
            return shop_region_id;
        }

        public void setShop_region_id(int shop_region_id) {
            this.shop_region_id = shop_region_id;
        }

        public int getShop_region_province() {
            return shop_region_province;
        }

        public void setShop_region_province(int shop_region_province) {
            this.shop_region_province = shop_region_province;
        }

        public String getShop_star_level() {
            return shop_star_level;
        }

        public void setShop_star_level(String shop_star_level) {
            this.shop_star_level = shop_star_level;
        }

        public String getShop_telephone() {
            return shop_telephone;
        }

        public void setShop_telephone(String shop_telephone) {
            this.shop_telephone = shop_telephone;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
