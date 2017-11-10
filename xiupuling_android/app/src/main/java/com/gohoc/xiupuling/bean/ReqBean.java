package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/3/20.
 */

public class ReqBean implements Serializable{


    /**
     * message : 查询成功
     * data : {"lastPage":false,"pageSize":2,"pageNumber":1,"list":[{"activity_brand":null,"activity_content":null,"activity_detail":null,"activity_nav_content":null,"activity_nav_latitude":null,"activity_nav_longitude":null,"activity_nav_type":null,"activity_onoff":1,"activity_title":"测试画质1","attention_count":null,"copyright_price":0,"create_time":"2017-02-26 18:44:05","cycle_cnt":183,"is_pay_money":0,"is_show":0,"is_show_phone":1,"material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/095831488105820.jpg","nick_name":"朝天椒","orientation":0,"playtime":30,"rq_design_spec":null,"rq_id":"d7f5834af4e94ee9bbd1b75e3e744a2f","rq_intention_money":0,"rq_opening":1,"rq_orientation":0,"rq_tag":null,"rq_telephone":"13808856815","rq_type":2,"terminal_cnt":1,"user_id":"15dc1ad0e136471d82057368a737d67c","work_count":1,"work_id":"54b405e6e8aa41f38112e109df1e702c","work_type":2,"xpl_status":null},{"activity_brand":null,"activity_content":null,"activity_detail":null,"activity_nav_content":null,"activity_nav_latitude":null,"activity_nav_longitude":null,"activity_nav_type":null,"activity_onoff":1,"activity_title":"你就可以养家糊口","attention_count":null,"copyright_price":0,"create_time":"2017-02-21 17:18:49","cycle_cnt":0,"is_pay_money":0,"is_show":0,"is_show_phone":1,"material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/007531487668724.jpg","nick_name":"朝天椒","orientation":0,"playtime":25,"rq_design_spec":null,"rq_id":"ad7d891c05d04bc4941b9d2d3c799d8e","rq_intention_money":0,"rq_opening":1,"rq_orientation":0,"rq_tag":null,"rq_telephone":"13808856815","rq_type":4,"terminal_cnt":1,"user_id":"15dc1ad0e136471d82057368a737d67c","work_count":1,"work_id":"7e3380c6868243a6b9651c6ce7945224","work_type":4,"xpl_status":null}],"firstPage":true,"totalRow":122,"totalPage":61}
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
         * lastPage : false
         * pageSize : 2
         * pageNumber : 1
         * list : [{"activity_brand":null,"activity_content":null,"activity_detail":null,"activity_nav_content":null,"activity_nav_latitude":null,"activity_nav_longitude":null,"activity_nav_type":null,"activity_onoff":1,"activity_title":"测试画质1","attention_count":null,"copyright_price":0,"create_time":"2017-02-26 18:44:05","cycle_cnt":183,"is_pay_money":0,"is_show":0,"is_show_phone":1,"material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/095831488105820.jpg","nick_name":"朝天椒","orientation":0,"playtime":30,"rq_design_spec":null,"rq_id":"d7f5834af4e94ee9bbd1b75e3e744a2f","rq_intention_money":0,"rq_opening":1,"rq_orientation":0,"rq_tag":null,"rq_telephone":"13808856815","rq_type":2,"terminal_cnt":1,"user_id":"15dc1ad0e136471d82057368a737d67c","work_count":1,"work_id":"54b405e6e8aa41f38112e109df1e702c","work_type":2,"xpl_status":null},{"activity_brand":null,"activity_content":null,"activity_detail":null,"activity_nav_content":null,"activity_nav_latitude":null,"activity_nav_longitude":null,"activity_nav_type":null,"activity_onoff":1,"activity_title":"你就可以养家糊口","attention_count":null,"copyright_price":0,"create_time":"2017-02-21 17:18:49","cycle_cnt":0,"is_pay_money":0,"is_show":0,"is_show_phone":1,"material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/007531487668724.jpg","nick_name":"朝天椒","orientation":0,"playtime":25,"rq_design_spec":null,"rq_id":"ad7d891c05d04bc4941b9d2d3c799d8e","rq_intention_money":0,"rq_opening":1,"rq_orientation":0,"rq_tag":null,"rq_telephone":"13808856815","rq_type":4,"terminal_cnt":1,"user_id":"15dc1ad0e136471d82057368a737d67c","work_count":1,"work_id":"7e3380c6868243a6b9651c6ce7945224","work_type":4,"xpl_status":null}]
         * firstPage : true
         * totalRow : 122
         * totalPage : 61
         */

        private boolean lastPage;
        private int pageSize;
        private int pageNumber;
        private boolean firstPage;
        private int totalRow;
        private int totalPage;
        private List<ListBean> list;


        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public int getTotalRow() {
            return totalRow;
        }

        public void setTotalRow(int totalRow) {
            this.totalRow = totalRow;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * activity_brand : null
             * activity_content : null
             * activity_detail : null
             * activity_nav_content : null
             * activity_nav_latitude : null
             * activity_nav_longitude : null
             * activity_nav_type : null
             * activity_onoff : 1
             * activity_title : 测试画质1
             * attention_count : null
             * copyright_price : 0.0
             * create_time : 2017-02-26 18:44:05
             * cycle_cnt : 183
             * is_pay_money : 0
             * is_show : 0
             * is_show_phone : 1
             * material_store_url : thumb-15dc1ad0e136471d82057368a737d67c/requirement/095831488105820.jpg
             * nick_name : 朝天椒
             * orientation : 0
             * playtime : 30
             * rq_design_spec : null
             * rq_id : d7f5834af4e94ee9bbd1b75e3e744a2f
             * rq_intention_money : 0.0
             * rq_opening : 1
             * rq_orientation : 0
             * rq_tag : null
             * rq_telephone : 13808856815
             * rq_type : 2
             * terminal_cnt : 1
             * user_id : 15dc1ad0e136471d82057368a737d67c
             * work_count : 1
             * work_id : 54b405e6e8aa41f38112e109df1e702c
             * work_type : 2
             * xpl_status : null
             */

            private String activity_brand;
            private String activity_content;
            private String activity_detail;
            private String activity_nav_content;
            private Double activity_nav_latitude;
            private Double activity_nav_longitude;
            private int activity_nav_type;
            private int activity_onoff;
            private String activity_title;
            private Object attention_count;
            private double copyright_price;
            private String create_time;
            private int cycle_cnt;
            private int is_pay_money;
            private int is_show;
            private int is_show_phone;
            private String material_store_url;
            private String nick_name;
            private int orientation;
            private int playtime;
            private Object rq_design_spec;
            private String rq_id;
            private double rq_intention_money;
            private int rq_opening;
            private int rq_orientation;
            private Object rq_tag;
            private String rq_telephone;
            private int rq_type;
            private int terminal_cnt;
            private String user_id;
            private int work_count;
            private String work_id;
            private int work_type;
            private Object xpl_status;
            private String rq_orientation_str;

            private String work_name;
            public String getWork_name() {
                return work_name;
            }

            public void setWork_name(String work_name) {
                this.work_name = work_name;
            }

            public String getRq_orientation_str() {
                switch (getRq_type())
                {
                    case 1:
                        return "海报";
                    case 2:
                        return "数码相册";
                    case 3:
                        return "宣传片";
                    case 4:
                        return "图片嵌入视频";
                }
                return "未知类型";
            }

            public void setRq_orientation_str(String rq_orientation_str) {
                this.rq_orientation_str = rq_orientation_str;
            }

            public String getActivity_brand() {
                return activity_brand;
            }

            public void setActivity_brand(String activity_brand) {
                this.activity_brand = activity_brand;
            }

            public String getActivity_content() {
                return activity_content;
            }

            public void setActivity_content(String activity_content) {
                this.activity_content = activity_content;
            }

            public String getActivity_detail() {
                return activity_detail;
            }

            public void setActivity_detail(String activity_detail) {
                this.activity_detail = activity_detail;
            }

            public String getActivity_nav_content() {
                return activity_nav_content;
            }

            public void setActivity_nav_content(String activity_nav_content) {
                this.activity_nav_content = activity_nav_content;
            }

            public Double getActivity_nav_latitude() {
                return activity_nav_latitude;
            }

            public void setActivity_nav_latitude(Double activity_nav_latitude) {
                this.activity_nav_latitude = activity_nav_latitude;
            }

            public Double getActivity_nav_longitude() {
                return activity_nav_longitude;
            }

            public void setActivity_nav_longitude(Double activity_nav_longitude) {
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

            public Object getAttention_count() {
                return attention_count;
            }

            public void setAttention_count(Object attention_count) {
                this.attention_count = attention_count;
            }

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

            public int getCycle_cnt() {
                return cycle_cnt;
            }

            public void setCycle_cnt(int cycle_cnt) {
                this.cycle_cnt = cycle_cnt;
            }

            public int getIs_pay_money() {
                return is_pay_money;
            }

            public void setIs_pay_money(int is_pay_money) {
                this.is_pay_money = is_pay_money;
            }

            public int getIs_show() {
                return is_show;
            }

            public void setIs_show(int is_show) {
                this.is_show = is_show;
            }

            public int getIs_show_phone() {
                return is_show_phone;
            }

            public void setIs_show_phone(int is_show_phone) {
                this.is_show_phone = is_show_phone;
            }

            public String getMaterial_store_url() {
                return material_store_url;
            }

            public void setMaterial_store_url(String material_store_url) {
                this.material_store_url = material_store_url;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public int getOrientation() {
                return orientation;
            }

            public void setOrientation(int orientation) {
                this.orientation = orientation;
            }

            public int getPlaytime() {
                return playtime;
            }

            public void setPlaytime(int playtime) {
                this.playtime = playtime;
            }

            public Object getRq_design_spec() {
                return rq_design_spec;
            }

            public void setRq_design_spec(Object rq_design_spec) {
                this.rq_design_spec = rq_design_spec;
            }

            public String getRq_id() {
                return rq_id;
            }

            public void setRq_id(String rq_id) {
                this.rq_id = rq_id;
            }

            public double getRq_intention_money() {
                return rq_intention_money;
            }

            public void setRq_intention_money(double rq_intention_money) {
                this.rq_intention_money = rq_intention_money;
            }

            public int getRq_opening() {
                return rq_opening;
            }

            public void setRq_opening(int rq_opening) {
                this.rq_opening = rq_opening;
            }

            public int getRq_orientation() {
                return rq_orientation;
            }

            public void setRq_orientation(int rq_orientation) {
                this.rq_orientation = rq_orientation;
            }

            public Object getRq_tag() {
                return rq_tag;
            }

            public void setRq_tag(Object rq_tag) {
                this.rq_tag = rq_tag;
            }

            public String getRq_telephone() {
                return rq_telephone;
            }

            public void setRq_telephone(String rq_telephone) {
                this.rq_telephone = rq_telephone;
            }

            public int getRq_type() {
                return rq_type;
            }

            public void setRq_type(int rq_type) {
                this.rq_type = rq_type;
            }

            public int getTerminal_cnt() {
                return terminal_cnt;
            }

            public void setTerminal_cnt(int terminal_cnt) {
                this.terminal_cnt = terminal_cnt;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public int getWork_count() {
                return work_count;
            }

            public void setWork_count(int work_count) {
                this.work_count = work_count;
            }

            public String getWork_id() {
                return work_id;
            }

            public void setWork_id(String work_id) {
                this.work_id = work_id;
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
    }
}
