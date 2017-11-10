package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/5/6.
 */

public class MyWorksBean {

    /**
     * message : 查询成功
     * data : [{"activity_brand":null,"activity_content":null,"activity_onoff":1,"activity_title":"卡拉卡拉","copyright_price":0,"create_time":"2017-05-06 17:49:56","material_store_url":"offset-requirement1494064122536ab744f53e1f1e6edf5d9c9e5d300165b.mp4.jpg","mobile":"15018546971","nick_name":"哈哈哈","orientation":0,"playtime":6,"rq_id":"0c8a6a1219e845f6b8dbf9908c349199","rq_opening":1,"rq_type":3,"work_id":"524c88c149e14ff69e79a565d20c755b","work_type":3},{"activity_brand":null,"activity_content":null,"activity_onoff":1,"activity_title":"急急急","copyright_price":0,"create_time":"2017-05-06 17:18:21","material_store_url":"thumb-requirement1494062263444cd54b13086beaa1be630cd1b6c4443bb.png","mobile":"15018546971","nick_name":"哈哈哈","orientation":0,"playtime":30,"rq_id":"5271231a05094367aa2ad62552694a5e","rq_opening":1,"rq_type":2,"work_id":"70a194e3b7264a59b6b0fa439db35fff","work_type":2},{"activity_brand":null,"activity_content":null,"activity_onoff":1,"activity_title":"呵呵呵","copyright_price":0,"create_time":"2017-05-06 16:58:10","material_store_url":"thumb-requirement1494061089716cd54b13086beaa1be630cd1b6c4443bb.png","mobile":"15018546971","nick_name":"哈哈哈","orientation":0,"playtime":30,"rq_id":"d96443cba5544309943a57f2c8e9429a","rq_opening":1,"rq_type":1,"work_id":"a3c172d19d5d4a22a339c3100d4ed574","work_type":1},{"activity_brand":null,"activity_content":null,"activity_onoff":1,"activity_title":"困困的了","copyright_price":0,"create_time":"2017-05-06 09:18:54","material_store_url":"thumb-requirement1494033529819cafd76accf3d89e7df01fe27b77025af.png","mobile":"15018546971","nick_name":"哈哈哈","orientation":0,"playtime":30,"rq_id":"e551647f6d09451392ba5fdfd1219af0","rq_opening":1,"rq_type":1,"work_id":"d856b030008a45f6bc2419253f1fd8ad","work_type":1},{"activity_brand":null,"activity_content":null,"activity_onoff":1,"activity_title":"卡拉卡拉","copyright_price":0,"create_time":"2017-05-06 09:17:57","material_store_url":"thumb-requirement1494033476378bd3b8b3b6fddc44f3c7f6dd178b542f7.png","mobile":"15018546971","nick_name":"哈哈哈","orientation":0,"playtime":30,"rq_id":"c5b8e6c1d08944c386b0f3a22faed478","rq_opening":1,"rq_type":1,"work_id":"b7ab1d40eaeb4629909329af2bf8f292","work_type":1},{"activity_brand":null,"activity_content":null,"activity_onoff":1,"activity_title":"监控室","copyright_price":0,"create_time":"2017-05-06 09:16:42","material_store_url":"thumb-requirement1494033401566cd54b13086beaa1be630cd1b6c4443bb.png","mobile":"15018546971","nick_name":"哈哈哈","orientation":0,"playtime":30,"rq_id":"b2083c79a99f4a7eaac3c8004c0d2f9a","rq_opening":1,"rq_type":1,"work_id":"c95a0b0e3b76477a98f5cbd934d696d8","work_type":1},{"activity_brand":null,"activity_content":null,"activity_onoff":1,"activity_title":"啊咯额","copyright_price":0,"create_time":"2017-05-06 09:15:17","material_store_url":"thumb-requirement1494033315934cd54b13086beaa1be630cd1b6c4443bb.png","mobile":"15018546971","nick_name":"哈哈哈","orientation":0,"playtime":30,"rq_id":"ab9a0d283d4144e5a73eafeb1b59e5f9","rq_opening":1,"rq_type":1,"work_id":"780e52b4a7374cb3bbee410d31ee1cde","work_type":1}]
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
         * activity_brand : null
         * activity_content : null
         * activity_onoff : 1
         * activity_title : 卡拉卡拉
         * copyright_price : 0.0
         * create_time : 2017-05-06 17:49:56
         * material_store_url : offset-requirement1494064122536ab744f53e1f1e6edf5d9c9e5d300165b.mp4.jpg
         * mobile : 15018546971
         * nick_name : 哈哈哈
         * orientation : 0
         * playtime : 6
         * rq_id : 0c8a6a1219e845f6b8dbf9908c349199
         * rq_opening : 1
         * rq_type : 3
         * work_id : 524c88c149e14ff69e79a565d20c755b
         * work_type : 3
         */

        private Object activity_brand;
        private Object activity_content;
        private int activity_onoff;
        private String activity_title;
        private double copyright_price;
        private String create_time;
        private String material_store_url;
        private String mobile;
        private String nick_name;
        private int orientation;
        private int playtime;
        private String rq_id;
        private int rq_opening;
        private int rq_type;
        private String work_id;
        private int work_type;
        private String rq_orientation_str;
        private String work_name;
        private String activity_nav_type;
        private String activity_nav_latitude;
        private String activity_nav_longitude;
        private String activity_detail;
        private String activity_nav_content;
        private int flag;//0:标示没有选中   1：选中

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getActivity_nav_content() {
            return activity_nav_content;
        }

        public void setActivity_nav_content(String activity_nav_content) {
            this.activity_nav_content = activity_nav_content;
        }

        public String getActivity_nav_type() {
            return activity_nav_type;
        }

        public void setActivity_nav_type(String activity_nav_type) {
            this.activity_nav_type = activity_nav_type;
        }

        public String getActivity_nav_latitude() {
            return activity_nav_latitude;
        }

        public void setActivity_nav_latitude(String activity_nav_latitude) {
            this.activity_nav_latitude = activity_nav_latitude;
        }

        public String getActivity_nav_longitude() {
            return activity_nav_longitude;
        }

        public void setActivity_nav_longitude(String activity_nav_longitude) {
            this.activity_nav_longitude = activity_nav_longitude;
        }

        public String getActivity_detail() {
            return activity_detail;
        }

        public void setActivity_detail(String activity_detail) {
            this.activity_detail = activity_detail;
        }

        public String getWork_name() {
            return work_name;
        }

        public void setWork_name(String work_name) {
            this.work_name = work_name;
        }

        public String getRq_orientation_str() {
            switch (getRq_type()) {
                case 1:
                    return "海报";
                case 2:
                    return "图册";
                case 3:
                    return "视频";
                case 4:
                    return "图嵌视频";
            }
            return "未知类型";
        }

        public void setRq_orientation_str(String rq_orientation_str) {
            this.rq_orientation_str = rq_orientation_str;
        }

        public Object getActivity_brand() {
            return activity_brand;
        }

        public void setActivity_brand(Object activity_brand) {
            this.activity_brand = activity_brand;
        }

        public Object getActivity_content() {
            return activity_content;
        }

        public void setActivity_content(Object activity_content) {
            this.activity_content = activity_content;
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

        public String getMaterial_store_url() {
            return material_store_url;
        }

        public void setMaterial_store_url(String material_store_url) {
            this.material_store_url = material_store_url;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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

        public String getRq_id() {
            return rq_id;
        }

        public void setRq_id(String rq_id) {
            this.rq_id = rq_id;
        }

        public int getRq_opening() {
            return rq_opening;
        }

        public void setRq_opening(int rq_opening) {
            this.rq_opening = rq_opening;
        }

        public int getRq_type() {
            return rq_type;
        }

        public void setRq_type(int rq_type) {
            this.rq_type = rq_type;
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
    }
}
