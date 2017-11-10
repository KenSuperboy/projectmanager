package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/3/2.
 */

public class GroupBean implements Serializable {

    /**
     * message : 查询成功
     * data : [{"create_time":"2017-05-25 14:48:40","member_cnt":2,"union_brief_info":"睡咯破功","union_id":"951bb5b924c54fafb3db76dc552b9459","union_name":"机洗洗洗","union_num":"20140517","union_portrait":"union149569491961738984f216fbbc0729fb7c4b5504efb84.jpg","union_size":100,"union_type":0,"user_cnt":2,"user_id":"9c9bd30381ff4198addf8a8938996705"},{"create_time":"2017-05-25 14:38:33","member_cnt":1,"union_brief_info":"哈哈哈","union_id":"592c5d226ff64a06a3476712ba520188","union_name":"你好啊","union_num":"20140514","union_portrait":"null","union_size":100,"union_type":0,"user_cnt":1,"user_id":"9c9bd30381ff4198addf8a8938996705"},{"create_time":"2017-05-25 14:29:54","member_cnt":0,"union_brief_info":"标签","union_id":"52d3d16cbd6f4ea1a27258936e238b60","union_name":"群名","union_num":"20140511","union_portrait":null,"union_size":100,"union_type":0,"user_cnt":1,"user_id":"9c9bd30381ff4198addf8a8938996705"},{"create_time":"2017-05-23 09:53:30","member_cnt":0,"union_brief_info":"室友说","union_id":"90e47604afa143dda7b2843f356760a7","union_name":"私有群测试","union_num":"10140446","union_portrait":"null","union_size":100,"union_type":1,"user_cnt":1,"user_id":"9c9bd30381ff4198addf8a8938996705"},{"create_time":"2017-05-26 15:02:09","member_cnt":1,"union_brief_info":"李隆基gloom","union_id":"1c83c1c3f0214423aa2be88e40345dea","union_name":"私有群凑够十个一可口","union_num":"10140449","union_portrait":"union1495782129010b98ac1c7a76b7448f341b07efa82981d.jpg","union_size":100,"union_type":1,"user_cnt":1,"user_id":"9c9bd30381ff4198addf8a8938996705"},{"create_time":"2017-03-21 15:44:51","member_cnt":0,"union_brief_info":"你一共","union_id":"93645a09a235475f87e30be89690ecbb","union_name":"羽毛球","union_num":"20140480","union_portrait":"union1490082291510cb9d2cc8eb33c28f6d78914d2aa3f008.jpg","union_size":100,"union_type":0,"user_cnt":1,"user_id":"9c9bd30381ff4198addf8a8938996705"},{"create_time":"2017-05-22 15:45:15","member_cnt":1,"union_brief_info":"国际经济民","union_id":"0784ddf7115f459b857cd97d73d09f52","union_name":"这个群用于删","union_num":"20140490","union_portrait":"union1495439113818eb432175cac462d62ff7f9f3ccc1c4d3.jpg","union_size":100,"union_type":0,"user_cnt":2,"user_id":"15dc1ad0e136471d82057368a737d67c"}]
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
         * create_time : 2017-05-25 14:48:40
         * member_cnt : 2
         * union_brief_info : 睡咯破功
         * union_id : 951bb5b924c54fafb3db76dc552b9459
         * union_name : 机洗洗洗
         * union_num : 20140517
         * union_portrait : union149569491961738984f216fbbc0729fb7c4b5504efb84.jpg
         * union_size : 100
         * union_type : 0
         * user_cnt : 2
         * user_id : 9c9bd30381ff4198addf8a8938996705
         */

        private String create_time;
        private int member_cnt;
        private String union_brief_info;
        private String union_id;
        private String union_name;
        private String union_num;
        private String union_portrait;
        private int union_size;
        private int union_type;
        private int user_cnt;
        private String user_id;
        private String union_typeStr;
        private int flag;

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getUnion_typeStr() {
            switch (getUnion_type()) {
                case 0:
                    return "共享群组内发布";
                case 1:
                    return "私有群组内发布";
                case 2:
                    return "连锁群组内发布";
                case 3:
                    return "媒体群组内发布";

            }
            return "";
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

        public int getMember_cnt() {
            return member_cnt;
        }

        public void setMember_cnt(int member_cnt) {
            this.member_cnt = member_cnt;
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

        public String getUnion_portrait() {
            return union_portrait;
        }

        public void setUnion_portrait(String union_portrait) {
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

        public int getUser_cnt() {
            return user_cnt;
        }

        public void setUser_cnt(int user_cnt) {
            this.user_cnt = user_cnt;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}

