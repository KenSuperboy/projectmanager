package com.gohoc.xiupuling.bean;

/**
 * Created by 28713 on 2017/3/22.
 */

public class DesignerBean {


    /**
     * message : 查询成功
     * data : {"introduce":"","is_designer":0,"link":"www.xiupuling.com","mail":"","mobile":"13912345672","nick_name":"张小凡","portrait_url":"user006.png","qq":"","star_level":3,"user_id":"01a530aaf1b44eceb1837beb6aa80bf9"}
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

    public static class DataBean {
        /**
         * introduce :
         * is_designer : 0
         * link : www.xiupuling.com
         * mail :
         * mobile : 13912345672
         * nick_name : 张小凡
         * portrait_url : user006.png
         * qq :
         * star_level : 3
         * user_id : 01a530aaf1b44eceb1837beb6aa80bf9
         */

        private String introduce;
        private int is_designer;
        private String link;
        private String mail;
        private String mobile;
        private String nick_name;
        private String portrait_url;
        private String qq;
        private int star_level;
        private String user_id;

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public int getIs_designer() {
            return is_designer;
        }

        public void setIs_designer(int is_designer) {
            this.is_designer = is_designer;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
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

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public int getStar_level() {
            return star_level;
        }

        public void setStar_level(int star_level) {
            this.star_level = star_level;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
