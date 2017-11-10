package com.gohoc.xiupuling.bean;

/**
 * Created by 28713 on 2017/6/8.
 */

public class PushHistoryDtBean {

    /**
     * message : 查询成功
     * data : {"activity_content":"","activity_title":"施华洛斯奇","create_time":null,"delete_time":"2016-09-1921:49:04","finish_cycle_cnt":0,"finish_terminal_cnt":0,"finish_week_per_term":0,"info_id":"c3529cb13f1f4462b0ad4a62d77a6615","material_store_url":"施华洛世奇_X_Miranda_Kerr_2013 秋冬广告大片[高清版]-封面.jpg","playtime":30,"rq_id":"xq008","status":2,"user_id":"e0de6884165a4a0bad0281bd12ec03f8","work_id":"pt023","work_seats":1}
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
         * activity_content :
         * activity_title : 施华洛斯奇
         * create_time : null
         * delete_time : 2016-09-1921:49:04
         * finish_cycle_cnt : 0
         * finish_terminal_cnt : 0
         * finish_week_per_term : 0
         * info_id : c3529cb13f1f4462b0ad4a62d77a6615
         * material_store_url : 施华洛世奇_X_Miranda_Kerr_2013 秋冬广告大片[高清版]-封面.jpg
         * playtime : 30
         * rq_id : xq008
         * status : 2
         * user_id : e0de6884165a4a0bad0281bd12ec03f8
         * work_id : pt023
         * work_seats : 1
         */

        private String activity_content;
        private String activity_title;
        private Object create_time;
        private String delete_time;
        private int finish_cycle_cnt;
        private int finish_terminal_cnt;
        private int finish_week_per_term;
        private String info_id;
        private String material_store_url;
        private int playtime;
        private String rq_id;
        private int status;
        private String user_id;
        private String work_id;
        private int work_seats;

        public String getActivity_content() {
            return activity_content;
        }

        public void setActivity_content(String activity_content) {
            this.activity_content = activity_content;
        }

        public String getActivity_title() {
            return activity_title;
        }

        public void setActivity_title(String activity_title) {
            this.activity_title = activity_title;
        }

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public String getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(String delete_time) {
            this.delete_time = delete_time;
        }

        public int getFinish_cycle_cnt() {
            return finish_cycle_cnt;
        }

        public void setFinish_cycle_cnt(int finish_cycle_cnt) {
            this.finish_cycle_cnt = finish_cycle_cnt;
        }

        public int getFinish_terminal_cnt() {
            return finish_terminal_cnt;
        }

        public void setFinish_terminal_cnt(int finish_terminal_cnt) {
            this.finish_terminal_cnt = finish_terminal_cnt;
        }

        public int getFinish_week_per_term() {
            return finish_week_per_term;
        }

        public void setFinish_week_per_term(int finish_week_per_term) {
            this.finish_week_per_term = finish_week_per_term;
        }

        public String getInfo_id() {
            return info_id;
        }

        public void setInfo_id(String info_id) {
            this.info_id = info_id;
        }

        public String getMaterial_store_url() {
            return material_store_url;
        }

        public void setMaterial_store_url(String material_store_url) {
            this.material_store_url = material_store_url;
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

        public String getWork_id() {
            return work_id;
        }

        public void setWork_id(String work_id) {
            this.work_id = work_id;
        }

        public int getWork_seats() {
            return work_seats;
        }

        public void setWork_seats(int work_seats) {
            this.work_seats = work_seats;
        }
    }
}
