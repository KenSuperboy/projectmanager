package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/2/22.
 */

public class PersonalVerifyapplyInfoBean implements Serializable {


    /**
     * message : 查询成功
     * data : {"create_time":null,"idno":"522131199009040001","idphoto_a":"张天宇身份证 01.jpg","idphoto_b":"张天宇身份证 02.jpg","name":"张天宇","status":2,"user_id":"7c7f36aed0ff4b8ca2ad736258a3c466"}
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
         * create_time : null
         * idno : 522131199009040001
         * idphoto_a : 张天宇身份证 01.jpg
         * idphoto_b : 张天宇身份证 02.jpg
         * name : 张天宇
         * status : 2
         * user_id : 7c7f36aed0ff4b8ca2ad736258a3c466
         */

        private Object create_time;
        private String idno;
        private String idphoto_a;
        private String idphoto_b;
        private String name;
        private int status;
        private String user_id;

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public String getIdno() {
            return idno;
        }

        public void setIdno(String idno) {
            this.idno = idno;
        }

        public String getIdphoto_a() {
            return idphoto_a;
        }

        public void setIdphoto_a(String idphoto_a) {
            this.idphoto_a = idphoto_a;
        }

        public String getIdphoto_b() {
            return idphoto_b;
        }

        public void setIdphoto_b(String idphoto_b) {
            this.idphoto_b = idphoto_b;
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
    }
}
