package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/3/2.
 * ---
 */

public class GroupDetailsBean implements Serializable {


    /**
     * message : null
     * data : {"create_time":"2017-02-17 16:17:14","ower":{"mobile":"138****6815","nick_name":"朝天椒","portrait_url":"personal1487922174981f196aae86fadcb78073b1f41e5a6485b.jpg","user_id":"15dc1ad0e136471d82057368a737d67c"},"union_brief_info":"内部连锁","union_id":"cafc0d94cdde4b5eae143d5fd93dff4b","union_invitation_code":"151646","union_name":"百盛餐饮连锁","union_num":"20140469","union_portrait":null,"union_size":100,"union_type":2,"user_id":"15dc1ad0e136471d82057368a737d67c"}
     * code : 1
     */

    private Object message;
    private DataBean data;
    private int code;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
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
         * create_time : 2017-02-17 16:17:14
         * ower : {"mobile":"138****6815","nick_name":"朝天椒","portrait_url":"personal1487922174981f196aae86fadcb78073b1f41e5a6485b.jpg","user_id":"15dc1ad0e136471d82057368a737d67c"}
         * union_brief_info : 内部连锁
         * union_id : cafc0d94cdde4b5eae143d5fd93dff4b
         * union_invitation_code : 151646
         * union_name : 百盛餐饮连锁
         * union_num : 20140469
         * union_portrait : null
         * union_size : 100
         * union_type : 2
         * user_id : 15dc1ad0e136471d82057368a737d67c
         */

        private String create_time;
        private OwerBean ower;
        private String union_brief_info;
        private String union_id;
        private String union_invitation_code;
        private String union_name;
        private String union_num;
        private Object union_portrait;
        private int union_size;
        private int union_type;
        private String user_id;
        private String union_typeStr;

        public String getUnion_typeStr() {
            switch (getUnion_type()) {
                case 0:
                    return "共享群组";
                case 1:
                    return "私有群组";
                case 2:
                    return "连锁群组";
                case 3:
                    return "媒体群组";
                default:
                    return "联动群组";
            }
        }

        public void setUnion_typeStr(String union_typeStr) {
            this.union_typeStr = union_typeStr;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public OwerBean getOwer() {
            return ower;
        }

        public void setOwer(OwerBean ower) {
            this.ower = ower;
        }

        public String getUnion_brief_info() {
            return union_brief_info;
        }

        public void setUnion_brief_info(String union_brief_info) {
            this.union_brief_info = union_brief_info;
        }

        public String getUnion_id() {
            return union_id;
        }

        public void setUnion_id(String union_id) {
            this.union_id = union_id;
        }

        public String getUnion_invitation_code() {
            return union_invitation_code;
        }

        public void setUnion_invitation_code(String union_invitation_code) {
            this.union_invitation_code = union_invitation_code;
        }

        public String getUnion_name() {
            return union_name;
        }

        public void setUnion_name(String union_name) {
            this.union_name = union_name;
        }

        public String getUnion_num() {
            return union_num;
        }

        public void setUnion_num(String union_num) {
            this.union_num = union_num;
        }

        public Object getUnion_portrait() {
            return union_portrait;
        }

        public void setUnion_portrait(Object union_portrait) {
            this.union_portrait = union_portrait;
        }

        public int getUnion_size() {
            return union_size;
        }

        public void setUnion_size(int union_size) {
            this.union_size = union_size;
        }

        public int getUnion_type() {
            return union_type;
        }

        public void setUnion_type(int union_type) {
            this.union_type = union_type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public static class OwerBean implements Serializable {
            /**
             * mobile : 138****6815
             * nick_name : 朝天椒
             * portrait_url : personal1487922174981f196aae86fadcb78073b1f41e5a6485b.jpg
             * user_id : 15dc1ad0e136471d82057368a737d67c
             */

            private String mobile;
            private String nick_name;
            private String portrait_url;
            private String user_id;

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

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }
    }
}
