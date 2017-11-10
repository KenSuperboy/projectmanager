package com.gohoc.xiupuling.bean;

/**
 * Created by 28713 on 2017/3/31.
 */

public class PushReqShowBean {


    /**
     * message : 查询成功
     * data : {"activity_title":"图册","create_time":null,"delete_time":null,"enddate":"20170716","finish_cycle_cnt":0,"finish_terminal_cnt":0,"finish_week_per_term":0,"info_id":"5ae9a6ee52bd42b78da86af8097edaf0","material_store_url":"thumb-requirement14981814129462892f06a544e437fbed3ac3a1963ab6f.png","rq_id":"686f9c1d72594f2c86b5d3e5ca901b15","startdate":"20170710","status":1,"total_cycle_cnt":0,"total_terminal_cnt":0,"total_week_per_term":0,"unfinish_cycle_cnt":0,"unfinish_terminal_cnt":0,"unfinish_week_per_term":0,"user_id":"9c9bd30381ff4198addf8a8938996705","work_id":"ea4d386c6ccb4afba2eb8964e09e0027"}
     * code : 1
     */

    private String message;
    private DataEntity data;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataEntity {
        /**
         * activity_title : 图册
         * create_time : null
         * delete_time : null
         * enddate : 20170716
         * finish_cycle_cnt : 0
         * finish_terminal_cnt : 0
         * finish_week_per_term : 0.0
         * info_id : 5ae9a6ee52bd42b78da86af8097edaf0
         * material_store_url : thumb-requirement14981814129462892f06a544e437fbed3ac3a1963ab6f.png
         * rq_id : 686f9c1d72594f2c86b5d3e5ca901b15
         * startdate : 20170710
         * status : 1
         * total_cycle_cnt : 0
         * total_terminal_cnt : 0
         * total_week_per_term : 0.0
         * unfinish_cycle_cnt : 0
         * unfinish_terminal_cnt : 0
         * unfinish_week_per_term : 0.0
         * user_id : 9c9bd30381ff4198addf8a8938996705
         * work_id : ea4d386c6ccb4afba2eb8964e09e0027
         */

        private String activity_title;
        private Object create_time;
        private Object delete_time;
        private String enddate;
        private int finish_cycle_cnt;
        private int finish_terminal_cnt;
        private double finish_week_per_term;
        private String info_id;
        private String material_store_url;
        private String rq_id;
        private String startdate;
        private int status;
        private double total_cycle_cnt;
        private double total_terminal_cnt;
        private double total_week_per_term;
        private int unfinish_cycle_cnt;
        private int unfinish_terminal_cnt;
        private double unfinish_week_per_term;
        private String user_id;
        private String work_id;
        private String activity_content;


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

        public Object getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(Object delete_time) {
            this.delete_time = delete_time;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
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

        public double getFinish_week_per_term() {
            return finish_week_per_term;
        }

        public void setFinish_week_per_term(double finish_week_per_term) {
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

        public String getRq_id() {
            return rq_id;
        }

        public void setRq_id(String rq_id) {
            this.rq_id = rq_id;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getTotal_cycle_cnt() {
            return total_cycle_cnt;
        }

        public void setTotal_cycle_cnt(double total_cycle_cnt) {
            this.total_cycle_cnt = total_cycle_cnt;
        }

        public double getTotal_terminal_cnt() {
            return total_terminal_cnt;
        }

        public void setTotal_terminal_cnt(int total_terminal_cnt) {
            this.total_terminal_cnt = total_terminal_cnt;
        }

        public double getTotal_week_per_term() {
            return total_week_per_term;
        }

        public void setTotal_week_per_term(double total_week_per_term) {
            this.total_week_per_term = total_week_per_term;
        }

        public int getUnfinish_cycle_cnt() {
            return unfinish_cycle_cnt;
        }

        public void setUnfinish_cycle_cnt(int unfinish_cycle_cnt) {
            this.unfinish_cycle_cnt = unfinish_cycle_cnt;
        }

        public int getUnfinish_terminal_cnt() {
            return unfinish_terminal_cnt;
        }

        public void setUnfinish_terminal_cnt(int unfinish_terminal_cnt) {
            this.unfinish_terminal_cnt = unfinish_terminal_cnt;
        }

        public double getUnfinish_week_per_term() {
            return unfinish_week_per_term;
        }

        public void setUnfinish_week_per_term(double unfinish_week_per_term) {
            this.unfinish_week_per_term = unfinish_week_per_term;
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
    }
}
