package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/2/15.
 */

public class BusinessTypeBean implements Serializable{


    /**
     * message : 查询成功
     * data : [{"business_name":"美食","business_id":1000,"business_level":1,"parent_id":0},{"business_name":"酒店","business_id":1001,"business_level":1,"parent_id":0},{"business_name":"休闲娱乐","business_id":1002,"business_level":1,"parent_id":0},{"business_name":"汽车服务","business_id":1003,"business_level":1,"parent_id":0},{"business_name":"生活服务","business_id":1004,"business_level":1,"parent_id":0},{"business_name":"学习培训","business_id":1005,"business_level":1,"parent_id":0},{"business_name":"逛街购物","business_id":1006,"business_level":1,"parent_id":0},{"business_name":"医疗","business_id":1007,"business_level":1,"parent_id":0},{"business_name":"丽人","business_id":1008,"business_level":1,"parent_id":0},{"business_name":"旅游","business_id":1009,"business_level":1,"parent_id":0}]
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
         * business_name : 美食
         * business_id : 1000
         * business_level : 1
         * parent_id : 0
         */

        private String business_name;
        private int business_id;
        private int business_level;
        private int parent_id;
        private boolean isSelect=false;

        public String getBusiness_name() {
            return business_name;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public void setBusiness_name(String business_name) {
            this.business_name = business_name;
        }

        public int getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(int business_id) {
            this.business_id = business_id;
        }

        public int getBusiness_level() {
            return business_level;
        }

        public void setBusiness_level(int business_level) {
            this.business_level = business_level;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        @Override
        public boolean equals(Object obj) {
            BusinessTypeBean.DataBean db= (BusinessTypeBean.DataBean) obj;
            if (getBusiness_id()==db.getBusiness_id())
                return true;
            return false;
        }
    }
}
