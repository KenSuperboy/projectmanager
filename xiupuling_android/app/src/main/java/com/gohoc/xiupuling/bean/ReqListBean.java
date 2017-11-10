package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/3/20.
 */

public class ReqListBean implements Serializable {


    /**
     * message : 查询成功
     * data : {"lastPage":false,"pageSize":1,"pageNumber":1,"list":[{"activity_brand":"换个方法","activity_content":"欢迎观影","activity_detail":"欢迎大家前来观看","activity_nav_content":"南山区海岸城家乐福超市东侧（海岸城保利文化广场负一楼B143号）","activity_nav_latitude":22.522191,"activity_nav_longitude":113.943637,"activity_nav_type":0,"activity_onoff":0,"activity_title":"魔兽海报-竖版","attention_count":0,"create_time":"2016-08-02 14:39:12","is_pay_money":0,"is_show":0,"is_show_phone":0,"rq_design_spec":"","rq_id":"xq013","rq_intention_money":0,"rq_opening":0,"rq_orientation":1,"rq_tag":null,"rq_telephone":"","rq_type":1,"unviewedworkcnt":0,"user_id":"15dc1ad0e136471d82057368a737d67c","work_count":5,"workcnt":5,"worklist":[{"copyright_price":32,"create_time":"2016-07-22 12:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖04.jpg","mobile":"13833213217","nick_name":"荣华月饼","orientation":1,"playtime":30,"work_id":"pt020","work_type":1},{"copyright_price":0,"create_time":"2016-07-22 12:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖01.jpg","mobile":"13912345672","nick_name":"张小凡","orientation":1,"playtime":30,"work_id":"pt017","work_type":1},{"copyright_price":100,"create_time":"2016-07-03 10:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖03.jpg","mobile":"13833213212","nick_name":"大胃王","orientation":1,"playtime":30,"work_id":"pt019","work_type":1},{"copyright_price":42,"create_time":"2016-07-02 23:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖05.jpg","mobile":"13912345672","nick_name":"张小凡","orientation":1,"playtime":30,"work_id":"pt021","work_type":1},{"copyright_price":32,"create_time":"2016-07-02 23:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖02.jpg","mobile":"13912345677","nick_name":"五大三粗","orientation":1,"playtime":30,"work_id":"pt018","work_type":1}],"xpl_status":null}],"firstPage":true,"totalRow":88,"totalPage":88}
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

    public static class DataBean implements Serializable {
        /**
         * lastPage : false
         * pageSize : 1
         * pageNumber : 1
         * list : [{"activity_brand":"换个方法","activity_content":"欢迎观影","activity_detail":"欢迎大家前来观看","activity_nav_content":"南山区海岸城家乐福超市东侧（海岸城保利文化广场负一楼B143号）","activity_nav_latitude":22.522191,"activity_nav_longitude":113.943637,"activity_nav_type":0,"activity_onoff":0,"activity_title":"魔兽海报-竖版","attention_count":0,"create_time":"2016-08-02 14:39:12","is_pay_money":0,"is_show":0,"is_show_phone":0,"rq_design_spec":"","rq_id":"xq013","rq_intention_money":0,"rq_opening":0,"rq_orientation":1,"rq_tag":null,"rq_telephone":"","rq_type":1,"unviewedworkcnt":0,"user_id":"15dc1ad0e136471d82057368a737d67c","work_count":5,"workcnt":5,"worklist":[{"copyright_price":32,"create_time":"2016-07-22 12:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖04.jpg","mobile":"13833213217","nick_name":"荣华月饼","orientation":1,"playtime":30,"work_id":"pt020","work_type":1},{"copyright_price":0,"create_time":"2016-07-22 12:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖01.jpg","mobile":"13912345672","nick_name":"张小凡","orientation":1,"playtime":30,"work_id":"pt017","work_type":1},{"copyright_price":100,"create_time":"2016-07-03 10:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖03.jpg","mobile":"13833213212","nick_name":"大胃王","orientation":1,"playtime":30,"work_id":"pt019","work_type":1},{"copyright_price":42,"create_time":"2016-07-02 23:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖05.jpg","mobile":"13912345672","nick_name":"张小凡","orientation":1,"playtime":30,"work_id":"pt021","work_type":1},{"copyright_price":32,"create_time":"2016-07-02 23:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖02.jpg","mobile":"13912345677","nick_name":"五大三粗","orientation":1,"playtime":30,"work_id":"pt018","work_type":1}],"xpl_status":null}]
         * firstPage : true
         * totalRow : 88
         * totalPage : 88
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

        public static class ListBean implements Serializable {
            /**
             * activity_brand : 换个方法
             * activity_content : 欢迎观影
             * activity_detail : 欢迎大家前来观看
             * activity_nav_content : 南山区海岸城家乐福超市东侧（海岸城保利文化广场负一楼B143号）
             * activity_nav_latitude : 22.522191
             * activity_nav_longitude : 113.943637
             * activity_nav_type : 0
             * activity_onoff : 0
             * activity_title : 魔兽海报-竖版
             * attention_count : 0
             * create_time : 2016-08-02 14:39:12
             * is_pay_money : 0
             * is_show : 0
             * is_show_phone : 0
             * rq_design_spec :
             * rq_id : xq013
             * rq_intention_money : 0.0
             * rq_opening : 0
             * rq_orientation : 1
             * rq_tag : null
             * rq_telephone :
             * rq_type : 1
             * unviewedworkcnt : 0
             * user_id : 15dc1ad0e136471d82057368a737d67c
             * work_count : 5
             * workcnt : 5
             * worklist : [{"copyright_price":32,"create_time":"2016-07-22 12:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖04.jpg","mobile":"13833213217","nick_name":"荣华月饼","orientation":1,"playtime":30,"work_id":"pt020","work_type":1},{"copyright_price":0,"create_time":"2016-07-22 12:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖01.jpg","mobile":"13912345672","nick_name":"张小凡","orientation":1,"playtime":30,"work_id":"pt017","work_type":1},{"copyright_price":100,"create_time":"2016-07-03 10:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖03.jpg","mobile":"13833213212","nick_name":"大胃王","orientation":1,"playtime":30,"work_id":"pt019","work_type":1},{"copyright_price":42,"create_time":"2016-07-02 23:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖05.jpg","mobile":"13912345672","nick_name":"张小凡","orientation":1,"playtime":30,"work_id":"pt021","work_type":1},{"copyright_price":32,"create_time":"2016-07-02 23:24:07","is_viewed":1,"iscopyrightown":"YES","material_store_url":"魔兽竖02.jpg","mobile":"13912345677","nick_name":"五大三粗","orientation":1,"playtime":30,"work_id":"pt018","work_type":1}]
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
            private int attention_count;
            private String create_time;
            private int is_pay_money;
            private int is_show;
            private int is_show_phone;
            private String rq_design_spec;
            private String rq_id;
            private double rq_intention_money;
            private int rq_opening;
            private int rq_orientation;
            private Object rq_tag;
            private String rq_telephone;
            private int rq_type;
            private int unviewedworkcnt;
            private String user_id;
            private int work_count;
            private int workcnt;
            private Object xpl_status;
            private List<WorklistBean> worklist;


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

            public int getAttention_count() {
                return attention_count;
            }

            public void setAttention_count(int attention_count) {
                this.attention_count = attention_count;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
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

            public String getRq_design_spec() {
                return rq_design_spec;
            }

            public void setRq_design_spec(String rq_design_spec) {
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

            public int getUnviewedworkcnt() {
                return unviewedworkcnt;
            }

            public void setUnviewedworkcnt(int unviewedworkcnt) {
                this.unviewedworkcnt = unviewedworkcnt;
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

            public int getWorkcnt() {
                return workcnt;
            }

            public void setWorkcnt(int workcnt) {
                this.workcnt = workcnt;
            }

            public Object getXpl_status() {
                return xpl_status;
            }

            public void setXpl_status(Object xpl_status) {
                this.xpl_status = xpl_status;
            }

            public List<WorklistBean> getWorklist() {
                return worklist;
            }

            public void setWorklist(List<WorklistBean> worklist) {
                this.worklist = worklist;
            }

            public static class WorklistBean implements Serializable {
                /**
                 * copyright_price : 32.0
                 * create_time : 2016-07-22 12:24:07
                 * is_viewed : 1
                 * iscopyrightown : YES
                 * material_store_url : 魔兽竖04.jpg
                 * mobile : 13833213217
                 * nick_name : 荣华月饼
                 * orientation : 1
                 * playtime : 30
                 * work_id : pt020
                 * work_type : 1
                 */

                private double copyright_price;
                private String create_time;
                private int is_viewed;
                private String iscopyrightown;
                private String material_store_url;
                private String mobile;
                private String nick_name;
                private int orientation;
                private int playtime;
                private String work_id;
                private int work_type;
                private String work_type_str;
                private String orientationStr;

                public String getOrientationStr() {
                    return   getOrientation() == 0 ? "横向" : "竖向";
                }

                public void setOrientationStr(String orientationStr) {
                    this.orientationStr = orientationStr;
                }

                public String getWork_type_str() {
                    switch (getWork_type()) {
                        case 1:
                            return "海报";
                        case 2:
                            return "图册";
                        case 3:
                            return "视频";
                        case 4:
                            return "图嵌视频";
                    }
                    return "未知类型";
                }

                public void setWork_type_str(String work_type_str) {
                    this.work_type_str = work_type_str;
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

                public int getIs_viewed() {
                    return is_viewed;
                }

                public void setIs_viewed(int is_viewed) {
                    this.is_viewed = is_viewed;
                }

                public String getIscopyrightown() {
                    return iscopyrightown;
                }

                public void setIscopyrightown(String iscopyrightown) {
                    this.iscopyrightown = iscopyrightown;
                }

                public String getMaterial_store_url() {
                    return material_store_url;
                }

                public void setMaterial_store_url(String material_store_url) {
                    this.material_store_url = material_store_url;
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
            }
        }
    }
}
