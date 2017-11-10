package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/2/23.
 */

public class RegionLocationBean implements Serializable{

    /**
     * message : 查询成功
     * data : {"id":440305,"level":true,"name":"南山区","province":{"id":44,"level":true,"name":"广东省","parentid":0},"parentid":4403,"city":{"id":4403,"level":true,"name":"深圳市","parentid":44}}
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
         * id : 440305
         * level : true
         * name : 南山区
         * province : {"id":44,"level":true,"name":"广东省","parentid":0}
         * parentid : 4403
         * city : {"id":4403,"level":true,"name":"深圳市","parentid":44}
         */

        private int id;
        private boolean level;
        private String name;
        private ProvinceBean province;
        private int parentid;
        private CityBean city;

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

        public ProvinceBean getProvince() {
            return province;
        }

        public void setProvince(ProvinceBean province) {
            this.province = province;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public CityBean getCity() {
            return city;
        }

        public void setCity(CityBean city) {
            this.city = city;
        }

        public static class ProvinceBean {
            /**
             * id : 44
             * level : true
             * name : 广东省
             * parentid : 0
             */

            private int id;
            private boolean level;
            private String name;
            private int parentid;

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

        public static class CityBean {
            /**
             * id : 4403
             * level : true
             * name : 深圳市
             * parentid : 44
             */

            private int id;
            private boolean level;
            private String name;
            private int parentid;

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
}
