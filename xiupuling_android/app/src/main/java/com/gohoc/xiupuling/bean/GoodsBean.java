package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/4/5.
 */

public class GoodsBean implements Serializable {

    /**
     * message : 查询成功
     * data : [{"create_time":null,"goods_detail":"永久权限，增加用户可同时管理的门店数+1","goods_icon":null,"goods_id":"sg009","goods_name":"门店+1","goods_num":1,"goods_price":98,"goods_type":1,"status":1,"tag":null}]
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
         * create_time : null
         * goods_detail : 永久权限，增加用户可同时管理的门店数+1
         * goods_icon : null
         * goods_id : sg009
         * goods_name : 门店+1
         * goods_num : 1
         * goods_price : 98
         * goods_type : 1
         * status : 1
         * tag : null
         */

        private Object create_time;
        private String goods_detail;
        private Object goods_icon;
        private String goods_id;
        private String goods_name;
        private int goods_num;
        private int goods_price;
        private int goods_type;
        private int status;
        private Object tag;

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public String getGoods_detail() {
            return goods_detail;
        }

        public void setGoods_detail(String goods_detail) {
            this.goods_detail = goods_detail;
        }

        public Object getGoods_icon() {
            return goods_icon;
        }

        public void setGoods_icon(Object goods_icon) {
            this.goods_icon = goods_icon;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public int getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(int goods_price) {
            this.goods_price = goods_price;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }
    }
}
