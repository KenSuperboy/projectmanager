package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/4/7.
 */

public class CouponBean  {

    /**
     * message : 查询成功
     * data : [{"coupon_id":"0986ab3beb2c4d3e8563c814ea31ac51","coupon_type":3,"create_time":"2016-09-26\t13:51:02","enable_time":null,"expire_time":null,"icon_url":"作品临时券图标.png","ids":"6bb620d390204bb893a032087fd9ecc9","instructions":"点击即用，当作品剩余数为零时可使用此券。 ","name":"作品临时增加券","status":1,"user_id":"7c7f36aed0ff4b8ca2ad736258a3c466","value":1}]
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
         * coupon_id : 0986ab3beb2c4d3e8563c814ea31ac51
         * coupon_type : 3
         * create_time : 2016-09-26	13:51:02
         * enable_time : null
         * expire_time : null
         * icon_url : 作品临时券图标.png
         * ids : 6bb620d390204bb893a032087fd9ecc9
         * instructions : 点击即用，当作品剩余数为零时可使用此券。
         * name : 作品临时增加券
         * status : 1
         * user_id : 7c7f36aed0ff4b8ca2ad736258a3c466
         * value : 1
         */

        private String coupon_id;
        private int coupon_type;
        private String create_time;
        private Object enable_time;
        private Object expire_time;
        private String icon_url;
        private String ids;
        private String instructions;
        private String name;
        private int status;
        private String user_id;
        private int value;

        public String getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
        }

        public int getCoupon_type() {
            return coupon_type;
        }

        public void setCoupon_type(int coupon_type) {
            this.coupon_type = coupon_type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public Object getEnable_time() {
            return enable_time;
        }

        public void setEnable_time(Object enable_time) {
            this.enable_time = enable_time;
        }

        public Object getExpire_time() {
            return expire_time;
        }

        public void setExpire_time(Object expire_time) {
            this.expire_time = expire_time;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public String getIds() {
            return ids;
        }

        public void setIds(String ids) {
            this.ids = ids;
        }

        public String getInstructions() {
            return instructions;
        }

        public void setInstructions(String instructions) {
            this.instructions = instructions;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }


    }
}
