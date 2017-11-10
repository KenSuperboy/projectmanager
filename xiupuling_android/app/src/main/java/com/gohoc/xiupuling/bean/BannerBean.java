package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/2/27.
 */

public class BannerBean {


    /**
     * message : 查询成功
     * data : [{"create_time":"2016-12-24 19:55:33","photo_id":"061a510f10f64e8fb7af21dce62ed54a","photo_url":"许留山（深圳欢乐颂店）004.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"498764cebbc64042b0d1cae37aac50fd","photo_url":"许留山（深圳欢乐颂店）001.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"79a4bac1c13d49ac8714427a5d8a5673","photo_url":"许留山（深圳欢乐颂店）005.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"c86c72669704473dbfca5e099d06d059","photo_url":"许留山（深圳欢乐颂店）002.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"eed20b0adeb5443eaeb88492faadfb70","photo_url":"许留山（深圳欢乐颂店）003.jpg","shop_id":"shop009"}]
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
         * create_time : 2016-12-24 19:55:33
         * photo_id : 061a510f10f64e8fb7af21dce62ed54a
         * photo_url : 许留山（深圳欢乐颂店）004.jpg
         * shop_id : shop009
         */

        private String create_time;
        private String photo_id;
        private String photo_url;
        private String shop_id;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPhoto_id() {
            return photo_id;
        }

        public void setPhoto_id(String photo_id) {
            this.photo_id = photo_id;
        }

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }
    }
}
