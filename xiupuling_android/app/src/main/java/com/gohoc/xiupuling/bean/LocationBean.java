package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/2/14.
 */

public class LocationBean {


    /**
     * message : 查询成功
     * data : [{"city_rank":null,"id":11,"level":true,"name":"北京市","parentid":0},{"city_rank":null,"id":31,"level":true,"name":"上海市","parentid":0},{"city_rank":null,"id":44,"level":true,"name":"广东省","parentid":0}]
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
         * city_rank : null
         * id : 11
         * level : true
         * name : 北京市
         * parentid : 0
         */

        private Object city_rank;
        private int id;
        private boolean level;
        private String name;
        private int parentid;
        private  boolean isCheck=false;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public Object getCity_rank() {
            return city_rank;
        }

        public void setCity_rank(Object city_rank) {
            this.city_rank = city_rank;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isLevel() {
            return level;
        }

        public void setLevel(boolean level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }
    }
}
