package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/2/6.
 */

public class UserBean implements Serializable {


    /**
     * message : 登录成功
     * data : {"age":null,"attention_count":null,"email":null,"follow_count":null,"gid":10140652,"mobile":"15018546971","nick_name":"150****6971","portrait_url":null,"sex":null,"token":"9b017977-1320-3f6e-8467-5ae4225c5459","user_id":"cb37ee352fe5490381cddeb9da4da9f7","username":"15018546971"}
     * code : 1
     */

    private String message;
    private DataBean data;
    private int code;
    private String password;
    private int pwdleval = 1;

    public int getPwdleval() {
        return pwdleval;
    }



    public void setPwdleval(int pwdleval) {
        this.pwdleval = pwdleval;
    }

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

    public static class DataBean implements Serializable {
        /**
         * age : null
         * attention_count : null
         * email : null
         * follow_count : null
         * gid : 10140652
         * mobile : 15018546971
         * nick_name : 150****6971
         * portrait_url : null
         * sex : null
         * token : 9b017977-1320-3f6e-8467-5ae4225c5459
         * user_id : cb37ee352fe5490381cddeb9da4da9f7
         * username : 15018546971
         */

        private String age;
        private String attention_count;
        private String email;
        private String follow_count;
        private int gid;
        private String mobile;
        private String nick_name;
        private String portrait_url;
        private String sex;
        private String token;
        private String user_id;
        private String username;

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAttention_count() {
            return attention_count;
        }

        public void setAttention_count(String attention_count) {
            this.attention_count = attention_count;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFollow_count() {
            return follow_count;
        }

        public void setFollow_count(String follow_count) {
            this.follow_count = follow_count;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
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

        public String getPortrait_url() {
            return portrait_url;
        }

        public void setPortrait_url(String portrait_url) {
            this.portrait_url = portrait_url;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
