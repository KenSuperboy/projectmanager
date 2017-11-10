package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/5/2.
 */

public class OrderDetailBean implements Serializable{


    /**
     * message : 查询成功
     * data : {"activity_nav_content":null,"activity_nav_latitude":null,"activity_nav_longitude":null,"activity_nav_type":null,"activity_onoff":1,"activity_title":"70M的作品","datalist":[{"end_date":"20170507","info_id":"86bcbe47f4a447578b5ec712f0816884","range_detail_id":"f4b10d41f99843d38015935ddabe6680","range_id":"6eeadc1b457a4eccb0bd754fb0d96015","range_week":19,"range_year":2017,"start_date":"20170503","unit_price":20,"work_seats":2}],"enddate":"20170507","fund_pool_amount":20,"info_id":"86bcbe47f4a447578b5ec712f0816884","range_id":"6eeadc1b457a4eccb0bd754fb0d96015","rq_id":"c94f43e449614659a64f909e45f5a271","star_level":1,"startdate":"20170503","totalamt":20,"unit_price":20,"week_cnt":1,"weeks":1,"work_id":"c8d3e42f62474fffaec29cbe5bd63197","work_seats":2,"workdetail":{"copyright_price":0,"create_time":"2017-01-07 10:28:20","internal_switch_type":null,"is_recommend":0,"next_switch_type":null,"orientation":0,"owner__status":1,"playtime":59,"sale_status":0,"tips_switch_type":null,"use__status":1,"user_id":"15dc1ad0e136471d82057368a737d67c","work_cover_url":null,"work_id":"c8d3e42f62474fffaec29cbe5bd63197","work_name":"70M的作品-视频1","work_seats":2,"work_size":74799937,"work_tag":null,"work_type":3,"xpl_status":null},"workmaterials":[{"is_cover":1,"material_height":1080,"material_order":1,"material_start_x":0,"material_start_y":0,"material_store_id":"2fca9feda39841159c07383e364b322b","material_store_url":"offset-15dc1ad0e136471d82057368a737d67c/requirement/1483754537.mp4.jpg","material_width":1920,"work_id":"c8d3e42f62474fffaec29cbe5bd63197","work_material_id":"17bb7e8a0a36460ebc7f8eb5aa54b478"},{"is_cover":0,"material_height":1080,"material_order":2,"material_start_x":0,"material_start_y":0,"material_store_id":"f9d3deda98184f558659fa46c43d6cf6","material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/1483754537.mp4","material_width":1920,"work_id":"c8d3e42f62474fffaec29cbe5bd63197","work_material_id":"4c883bb60a8444f0bb98edabc797f913"}]}
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
         * activity_nav_content : null
         * activity_nav_latitude : null
         * activity_nav_longitude : null
         * activity_nav_type : null
         * activity_onoff : 1
         * activity_title : 70M的作品
         * datalist : [{"end_date":"20170507","info_id":"86bcbe47f4a447578b5ec712f0816884","range_detail_id":"f4b10d41f99843d38015935ddabe6680","range_id":"6eeadc1b457a4eccb0bd754fb0d96015","range_week":19,"range_year":2017,"start_date":"20170503","unit_price":20,"work_seats":2}]
         * enddate : 20170507
         * fund_pool_amount : 20.0
         * info_id : 86bcbe47f4a447578b5ec712f0816884
         * range_id : 6eeadc1b457a4eccb0bd754fb0d96015
         * rq_id : c94f43e449614659a64f909e45f5a271
         * star_level : 1
         * startdate : 20170503
         * totalamt : 20.0
         * unit_price : 20.0
         * week_cnt : 1
         * weeks : 1
         * work_id : c8d3e42f62474fffaec29cbe5bd63197
         * work_seats : 2
         * workdetail : {"copyright_price":0,"create_time":"2017-01-07 10:28:20","internal_switch_type":null,"is_recommend":0,"next_switch_type":null,"orientation":0,"owner__status":1,"playtime":59,"sale_status":0,"tips_switch_type":null,"use__status":1,"user_id":"15dc1ad0e136471d82057368a737d67c","work_cover_url":null,"work_id":"c8d3e42f62474fffaec29cbe5bd63197","work_name":"70M的作品-视频1","work_seats":2,"work_size":74799937,"work_tag":null,"work_type":3,"xpl_status":null}
         * workmaterials : [{"is_cover":1,"material_height":1080,"material_order":1,"material_start_x":0,"material_start_y":0,"material_store_id":"2fca9feda39841159c07383e364b322b","material_store_url":"offset-15dc1ad0e136471d82057368a737d67c/requirement/1483754537.mp4.jpg","material_width":1920,"work_id":"c8d3e42f62474fffaec29cbe5bd63197","work_material_id":"17bb7e8a0a36460ebc7f8eb5aa54b478"},{"is_cover":0,"material_height":1080,"material_order":2,"material_start_x":0,"material_start_y":0,"material_store_id":"f9d3deda98184f558659fa46c43d6cf6","material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/1483754537.mp4","material_width":1920,"work_id":"c8d3e42f62474fffaec29cbe5bd63197","work_material_id":"4c883bb60a8444f0bb98edabc797f913"}]
         */

        private String activity_nav_content;
        private Object activity_nav_latitude;
        private Object activity_nav_longitude;
        private int activity_nav_type;
        private int activity_onoff;
        private String activity_title;
        private String enddate;
        private double fund_pool_amount;
        private String info_id;
        private String range_id;
        private String rq_id;
        private int star_level;
        private String startdate;
        private double totalamt;
        private double unit_price;
        private int week_cnt;
        private int weeks;
        private String work_id;
        private int work_seats;
        private WorkdetailBean workdetail;
        private List<DatalistBean> datalist;
        private List<WorkmaterialsBean> workmaterials;
        private String activity_content;
        private Object is_ignore_other_work;

        public Object getIs_ignore_other_work() {
            return is_ignore_other_work;
        }

        public void setIs_ignore_other_work(Object is_ignore_other_work) {
            this.is_ignore_other_work = is_ignore_other_work;
        }

        public String getActivity_content() {
            return activity_content;
        }

        public void setActivity_content(String activity_content) {
            this.activity_content = activity_content;
        }

        public String getActivity_nav_content() {
            return activity_nav_content;
        }

        public void setActivity_nav_content(String activity_nav_content) {
            this.activity_nav_content = activity_nav_content;
        }

        public Object getActivity_nav_latitude() {
            return activity_nav_latitude;
        }

        public void setActivity_nav_latitude(String activity_nav_latitude) {
            this.activity_nav_latitude = activity_nav_latitude;
        }

        public Object getActivity_nav_longitude() {
            return activity_nav_longitude;
        }

        public void setActivity_nav_longitude(Object activity_nav_longitude) {
            this.activity_nav_longitude = activity_nav_longitude;
        }

        public int getActivity_nav_type() {
            return activity_nav_type;
        }

        public void setActivity_nav_type(int activity_nav_type) {
            this.activity_nav_type = activity_nav_type;
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

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public double getFund_pool_amount() {
            return fund_pool_amount;
        }

        public void setFund_pool_amount(double fund_pool_amount) {
            this.fund_pool_amount = fund_pool_amount;
        }

        public String getInfo_id() {
            return info_id;
        }

        public void setInfo_id(String info_id) {
            this.info_id = info_id;
        }

        public String getRange_id() {
            return range_id;
        }

        public void setRange_id(String range_id) {
            this.range_id = range_id;
        }

        public String getRq_id() {
            return rq_id;
        }

        public void setRq_id(String rq_id) {
            this.rq_id = rq_id;
        }

        public int getStar_level() {
            return star_level;
        }

        public void setStar_level(int star_level) {
            this.star_level = star_level;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public double getTotalamt() {
            return totalamt;
        }

        public void setTotalamt(double totalamt) {
            this.totalamt = totalamt;
        }

        public double getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(double unit_price) {
            this.unit_price = unit_price;
        }

        public int getWeek_cnt() {
            return week_cnt;
        }

        public void setWeek_cnt(int week_cnt) {
            this.week_cnt = week_cnt;
        }

        public int getWeeks() {
            return weeks;
        }

        public void setWeeks(int weeks) {
            this.weeks = weeks;
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

        public WorkdetailBean getWorkdetail() {
            return workdetail;
        }

        public void setWorkdetail(WorkdetailBean workdetail) {
            this.workdetail = workdetail;
        }

        public List<DatalistBean> getDatalist() {
            return datalist;
        }

        public void setDatalist(List<DatalistBean> datalist) {
            this.datalist = datalist;
        }

        public List<WorkmaterialsBean> getWorkmaterials() {
            return workmaterials;
        }

        public void setWorkmaterials(List<WorkmaterialsBean> workmaterials) {
            this.workmaterials = workmaterials;
        }

        public static class WorkdetailBean implements Serializable{
            /**
             * copyright_price : 0.0
             * create_time : 2017-01-07 10:28:20
             * internal_switch_type : null
             * is_recommend : 0
             * next_switch_type : null
             * orientation : 0
             * owner__status : 1
             * playtime : 59
             * sale_status : 0
             * tips_switch_type : null
             * use__status : 1
             * user_id : 15dc1ad0e136471d82057368a737d67c
             * work_cover_url : null
             * work_id : c8d3e42f62474fffaec29cbe5bd63197
             * work_name : 70M的作品-视频1
             * work_seats : 2
             * work_size : 74799937
             * work_tag : null
             * work_type : 3
             * xpl_status : null
             */

            private double copyright_price;
            private String create_time;
            private Object internal_switch_type;
            private int is_recommend;
            private Object next_switch_type;
            private int orientation;
            private int owner__status;
            private int playtime;
            private int sale_status;
            private Object tips_switch_type;
            private int use__status;
            private String user_id;
            private Object work_cover_url;
            private String work_id;
            private String work_name;
            private int work_seats;
            private int work_size;
            private Object work_tag;
            private int work_type;
            private Object xpl_status;

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

            public Object getInternal_switch_type() {
                return internal_switch_type;
            }

            public void setInternal_switch_type(Object internal_switch_type) {
                this.internal_switch_type = internal_switch_type;
            }

            public int getIs_recommend() {
                return is_recommend;
            }

            public void setIs_recommend(int is_recommend) {
                this.is_recommend = is_recommend;
            }

            public Object getNext_switch_type() {
                return next_switch_type;
            }

            public void setNext_switch_type(Object next_switch_type) {
                this.next_switch_type = next_switch_type;
            }

            public int getOrientation() {
                return orientation;
            }

            public void setOrientation(int orientation) {
                this.orientation = orientation;
            }

            public int getOwner__status() {
                return owner__status;
            }

            public void setOwner__status(int owner__status) {
                this.owner__status = owner__status;
            }

            public int getPlaytime() {
                return playtime;
            }

            public void setPlaytime(int playtime) {
                this.playtime = playtime;
            }

            public int getSale_status() {
                return sale_status;
            }

            public void setSale_status(int sale_status) {
                this.sale_status = sale_status;
            }

            public Object getTips_switch_type() {
                return tips_switch_type;
            }

            public void setTips_switch_type(Object tips_switch_type) {
                this.tips_switch_type = tips_switch_type;
            }

            public int getUse__status() {
                return use__status;
            }

            public void setUse__status(int use__status) {
                this.use__status = use__status;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public Object getWork_cover_url() {
                return work_cover_url;
            }

            public void setWork_cover_url(Object work_cover_url) {
                this.work_cover_url = work_cover_url;
            }

            public String getWork_id() {
                return work_id;
            }

            public void setWork_id(String work_id) {
                this.work_id = work_id;
            }

            public String getWork_name() {
                return work_name;
            }

            public void setWork_name(String work_name) {
                this.work_name = work_name;
            }

            public int getWork_seats() {
                return work_seats;
            }

            public void setWork_seats(int work_seats) {
                this.work_seats = work_seats;
            }

            public int getWork_size() {
                return work_size;
            }

            public void setWork_size(int work_size) {
                this.work_size = work_size;
            }

            public Object getWork_tag() {
                return work_tag;
            }

            public void setWork_tag(Object work_tag) {
                this.work_tag = work_tag;
            }

            public int getWork_type() {
                return work_type;
            }

            public void setWork_type(int work_type) {
                this.work_type = work_type;
            }

            public Object getXpl_status() {
                return xpl_status;
            }

            public void setXpl_status(Object xpl_status) {
                this.xpl_status = xpl_status;
            }
        }

        public static class DatalistBean implements Serializable{
            /**
             * end_date : 20170507
             * info_id : 86bcbe47f4a447578b5ec712f0816884
             * range_detail_id : f4b10d41f99843d38015935ddabe6680
             * range_id : 6eeadc1b457a4eccb0bd754fb0d96015
             * range_week : 19
             * range_year : 2017
             * start_date : 20170503
             * unit_price : 20.0
             * work_seats : 2
             */

            private String end_date;
            private String info_id;
            private String range_detail_id;
            private String range_id;
            private int range_week;
            private int range_year;
            private String start_date;
            private double unit_price;
            private int work_seats;
            private boolean isCheck=false;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public String getEnd_date() {
                return end_date;
            }

            public void setEnd_date(String end_date) {
                this.end_date = end_date;
            }

            public String getInfo_id() {
                return info_id;
            }

            public void setInfo_id(String info_id) {
                this.info_id = info_id;
            }

            public String getRange_detail_id() {
                return range_detail_id;
            }

            public void setRange_detail_id(String range_detail_id) {
                this.range_detail_id = range_detail_id;
            }

            public String getRange_id() {
                return range_id;
            }

            public void setRange_id(String range_id) {
                this.range_id = range_id;
            }

            public int getRange_week() {
                return range_week;
            }

            public void setRange_week(int range_week) {
                this.range_week = range_week;
            }

            public int getRange_year() {
                return range_year;
            }

            public void setRange_year(int range_year) {
                this.range_year = range_year;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public double getUnit_price() {
                return unit_price;
            }

            public void setUnit_price(double unit_price) {
                this.unit_price = unit_price;
            }

            public int getWork_seats() {
                return work_seats;
            }

            public void setWork_seats(int work_seats) {
                this.work_seats = work_seats;
            }
        }

        public static class WorkmaterialsBean implements Serializable{
            /**
             * is_cover : 1
             * material_height : 1080
             * material_order : 1
             * material_start_x : 0
             * material_start_y : 0
             * material_store_id : 2fca9feda39841159c07383e364b322b
             * material_store_url : offset-15dc1ad0e136471d82057368a737d67c/requirement/1483754537.mp4.jpg
             * material_width : 1920
             * work_id : c8d3e42f62474fffaec29cbe5bd63197
             * work_material_id : 17bb7e8a0a36460ebc7f8eb5aa54b478
             */

            private int is_cover;
            private int material_height;
            private int material_order;
            private int material_start_x;
            private int material_start_y;
            private String material_store_id;
            private String material_store_url;
            private int material_width;
            private String work_id;
            private String filetype;
            private String work_material_id;

            public String getFiletype() {
                return filetype;
            }

            public void setFiletype(String filetype) {
                this.filetype = filetype;
            }

            public int getIs_cover() {
                return is_cover;
            }

            public void setIs_cover(int is_cover) {
                this.is_cover = is_cover;
            }

            public int getMaterial_height() {
                return material_height;
            }

            public void setMaterial_height(int material_height) {
                this.material_height = material_height;
            }

            public int getMaterial_order() {
                return material_order;
            }

            public void setMaterial_order(int material_order) {
                this.material_order = material_order;
            }

            public int getMaterial_start_x() {
                return material_start_x;
            }

            public void setMaterial_start_x(int material_start_x) {
                this.material_start_x = material_start_x;
            }

            public int getMaterial_start_y() {
                return material_start_y;
            }

            public void setMaterial_start_y(int material_start_y) {
                this.material_start_y = material_start_y;
            }

            public String getMaterial_store_id() {
                return material_store_id;
            }

            public void setMaterial_store_id(String material_store_id) {
                this.material_store_id = material_store_id;
            }

            public String getMaterial_store_url() {
                return material_store_url;
            }

            public void setMaterial_store_url(String material_store_url) {
                this.material_store_url = material_store_url;
            }

            public int getMaterial_width() {
                return material_width;
            }

            public void setMaterial_width(int material_width) {
                this.material_width = material_width;
            }

            public String getWork_id() {
                return work_id;
            }

            public void setWork_id(String work_id) {
                this.work_id = work_id;
            }

            public String getWork_material_id() {
                return work_material_id;
            }

            public void setWork_material_id(String work_material_id) {
                this.work_material_id = work_material_id;
            }
        }
    }
}
