package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/3/15.
 */

public class GroupSearchBean implements Serializable {


    /**
     * message : 查询成功
     * data : [{"create_time":"2016-11-16 00:04:59","member_cnt":2,"mobile":"13600345343","nick_name":"小霸王","union_brief_info":"南山，茶叶会，飞压力","union_id":"5822afa4117e4a2287c12c402c905e2a","union_invitation_code":"600411","union_name":"茶叶群苗萌","union_num":"20140458","union_portrait":null,"union_size":100,"union_type":0,"user_id":"b20b7aca3002496694e6114b389b9596"}]
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

    public static class DataBean implements Serializable {
        /**
         * create_time : 2016-11-16 00:04:59
         * member_cnt : 2
         * mobile : 13600345343
         * nick_name : 小霸王
         * union_brief_info : 南山，茶叶会，飞压力
         * union_id : 5822afa4117e4a2287c12c402c905e2a
         * union_invitation_code : 600411
         * union_name : 茶叶群苗萌
         * union_num : 20140458
         * union_portrait : null
         * union_size : 100
         * union_type : 0
         * user_id : b20b7aca3002496694e6114b389b9596
         */

        private String create_time;
        private int member_cnt;
        private String mobile;
        private String nick_name;
        private String union_brief_info;
        private String union_id;
        private String union_invitation_code;
        private String union_name;
        private String union_num;
        private Object union_portrait;
        private int union_size;
        private int union_type;
        private String user_id;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getMember_cnt() {
            return member_cnt;
        }

        public void setMember_cnt(int member_cnt) {
            this.member_cnt = member_cnt;
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
    }
}
