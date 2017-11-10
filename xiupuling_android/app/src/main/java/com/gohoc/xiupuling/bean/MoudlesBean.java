package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/4/19.
 */

public class MoudlesBean {
    public static final int TITLE_TEXT = 1;
    public static final int MODULE = 2;
    public static final int MODULE_SUB = 3;

    /**
     * message : 查询成功
     * data : [{"module_list":[{"module_brief_info":"","module_cover_url":"o_1b4gcha7v10slvn31keelfc1vk42e.jpg","module_id":"585a500d417f16.69362484","module_name":"努力","module_no":1,"module_orientation":0,"module_seats":3,"module_size":58971784,"module_size_total":524288000,"module_tag":0,"module_type":3,"module_type_id":"module_personal","update_time":"2016-12-21 17:49:01","user_id":"15dc1ad0e136471d82057368a737d67c"}],"module_type":2,"module_type_id":"module_sys001","name":"公益短片"}]
     */

    private String message;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * module_list : [{"module_brief_info":"","module_cover_url":"o_1b4gcha7v10slvn31keelfc1vk42e.jpg","module_id":"585a500d417f16.69362484","module_name":"努力","module_no":1,"module_orientation":0,"module_seats":3,"module_size":58971784,"module_size_total":524288000,"module_tag":0,"module_type":3,"module_type_id":"module_personal","update_time":"2016-12-21 17:49:01","user_id":"15dc1ad0e136471d82057368a737d67c"}]
         * module_type : 2
         * module_type_id : module_sys001
         * name : 公益短片
         */

        private int module_type;
        private String module_type_id;
        private String name;
        private List<ModuleListBean> module_list;
        private String module_type_str;

        public String getModule_type_str() {
            if (module_type == 1)
                return "标签墙最多只能选择10000个播放内容";
            else if (module_type == 3)
                return "私人视频库最多只能选择1个播放内容";
            else
                return "系统电台最多只能选择3个播放内容";
        }

        public void setModule_type_str(String module_type_str) {
            this.module_type_str = module_type_str;
        }

        public int getModule_type() {
            return module_type;
        }

        public void setModule_type(int module_type) {
            this.module_type = module_type;
        }

        public String getModule_type_id() {
            return module_type_id;
        }

        public void setModule_type_id(String module_type_id) {
            this.module_type_id = module_type_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ModuleListBean> getModule_list() {
            return module_list;
        }

        public void setModule_list(List<ModuleListBean> module_list) {
            this.module_list = module_list;
        }

        public static class ModuleListBean {
            /**
             * module_brief_info :
             * module_cover_url : o_1b4gcha7v10slvn31keelfc1vk42e.jpg
             * module_id : 585a500d417f16.69362484
             * module_name : 努力
             * module_no : 1
             * module_orientation : 0
             * module_seats : 3
             * module_size : 58971784
             * module_size_total : 524288000
             * module_tag : 0
             * module_type : 3
             * module_type_id : module_personal
             * update_time : 2016-12-21 17:49:01
             * user_id : 15dc1ad0e136471d82057368a737d67c
             */

            private String module_brief_info;
            private String module_cover_url;
            private String module_id;
            private String module_name;
            private int module_no;
            private int module_orientation;
            private int module_seats;
            private long module_size;
            private long module_size_total;
            private int module_tag;
            private int module_type;
            private String module_type_id;
            private String update_time;
            private String user_id;

            public String getModule_brief_info() {
                return module_brief_info;
            }

            public void setModule_brief_info(String module_brief_info) {
                this.module_brief_info = module_brief_info;
            }

            public String getModule_cover_url() {
                return module_cover_url;
            }

            public void setModule_cover_url(String module_cover_url) {
                this.module_cover_url = module_cover_url;
            }

            public String getModule_id() {
                return module_id;
            }

            public void setModule_id(String module_id) {
                this.module_id = module_id;
            }

            public String getModule_name() {
                return module_name;
            }

            public void setModule_name(String module_name) {
                this.module_name = module_name;
            }

            public int getModule_no() {
                return module_no;
            }

            public void setModule_no(int module_no) {
                this.module_no = module_no;
            }

            public int getModule_orientation() {
                return module_orientation;
            }

            public void setModule_orientation(int module_orientation) {
                this.module_orientation = module_orientation;
            }

            public int getModule_seats() {
                return module_seats;
            }

            public void setModule_seats(int module_seats) {
                this.module_seats = module_seats;
            }

            public long getModule_size() {
                return module_size;
            }

            public void setModule_size(long module_size) {
                this.module_size = module_size;
            }

            public long getModule_size_total() {
                return module_size_total;
            }

            public void setModule_size_total(long module_size_total) {
                this.module_size_total = module_size_total;
            }

            public int getModule_tag() {
                return module_tag;
            }

            public void setModule_tag(int module_tag) {
                this.module_tag = module_tag;
            }

            public int getModule_type() {
                return module_type;
            }

            public void setModule_type(int module_type) {
                this.module_type = module_type;
            }

            public String getModule_type_id() {
                return module_type_id;
            }

            public void setModule_type_id(String module_type_id) {
                this.module_type_id = module_type_id;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
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
